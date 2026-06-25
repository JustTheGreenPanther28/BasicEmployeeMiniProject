package com.employee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.entity.Employee;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.repository.EmployeeRepository;
import com.employee.request.EmployeeAdditionRequest;
import com.employee.response.EmployeeResponse;
import com.employee.response.ReportEmployee;
import com.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepo;

	public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	@Override
	public Page<EmployeeResponse> getEmployees(int page, int size, String sortBy, String order) {

		Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<EmployeeResponse> response = employeeRepo.findAll(pageable).map(employeeEntity -> {
			EmployeeResponse employeeResponse = new EmployeeResponse();
			employeeResponse.setEmployeeAge(employeeEntity.getEmployeeAge());
			employeeResponse.setEmployeeId(employeeEntity.getEmployeeId().toString());
			employeeResponse.setEmployeeName(employeeEntity.getEmployeeName());
			employeeResponse.setJoinDate(employeeEntity.getJoinDate());
			employeeResponse.setPosition(employeeEntity.getPosition());
			employeeResponse.setSalary(employeeEntity.getSalary());

			return employeeResponse;
		});

		if (response == null) {
			throw new RuntimeException("Empty!");
		}
		return response;
	}

	@Override
	public List<ReportEmployee> getEmployeesReport(UUID id) throws EmployeeNotFoundException {
		// id is id of employee - who will report to others

		List<ReportEmployee> reportsTo = new ArrayList<>();
		Employee current = employeeRepo.findById(id.toString())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with doesn't exist!"));
		if(current.getReportTo()==null) {
			throw new EmployeeNotFoundException("No employee to report");
		}
		ReportEmployee reportEmp = new ReportEmployee();
		reportEmp.setReporter(current.getEmployeeName());
		reportEmp.setReportTo(current.getReportTo().getEmployeeName());

		reportsTo.add(reportEmp);

		return reportsTo;
	}

	@Override
	@Transactional
	public void deleteEmployee(UUID id) throws EmployeeNotFoundException {

		Employee employee = employeeRepo.findById(id.toString())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with id doesn't exist"));

		employeeRepo.delete(employee);

	}

	@Override
	@Transactional
	public EmployeeResponse addEmployee(EmployeeAdditionRequest employeeAdditionRequest)
			throws EmployeeNotFoundException {

		Employee employee = new Employee();
		employee.setEmployeeAge(employeeAdditionRequest.age());
		employee.setEmployeeName(employeeAdditionRequest.name());
		employee.setJoinDate(employeeAdditionRequest.joinDate());
		employee.setPosition(employeeAdditionRequest.position());
		employee.setSalary(employeeAdditionRequest.salary());
		Employee reportTo = null;
		if (employeeAdditionRequest.reportTo() != null) {
			reportTo = employeeRepo.findById(employeeAdditionRequest.reportTo().toString())
					.orElseThrow(() -> new EmployeeNotFoundException("The employee to report doesn't exist!"));
		}
		employee.setReportTo(reportTo);
		Employee savedEmployee = employeeRepo.save(employee);

		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setEmployeeAge(savedEmployee.getEmployeeAge());
		employeeResponse.setEmployeeName(savedEmployee.getEmployeeName());
		employeeResponse.setJoinDate(savedEmployee.getJoinDate());
		employeeResponse.setPosition(savedEmployee.getPosition());
		employeeResponse.setSalary(savedEmployee.getSalary());

		return employeeResponse;

	}

	@Override
	public boolean deleteEmployees(List<String> publicIds) {

		employeeRepo.deleteAllByIdInBatch(publicIds);

		return true;
	}

	@Override
	@Transactional
	public EmployeeResponse updateEmployee(UUID id, EmployeeAdditionRequest employeeChangeRequest)
			throws EmployeeNotFoundException {
		Employee employee = employeeRepo.findById(id.toString())
				.orElseThrow(() -> new EmployeeNotFoundException("The employee doesn't exist!"));

		if (employeeChangeRequest.reportTo() != null) {
			Employee employeeToReport = employeeRepo.findById(employeeChangeRequest.reportTo().toString())
					.orElseThrow(() -> new EmployeeNotFoundException("The employee to report doesn't exist!"));

			employee.setReportTo(employeeToReport);

			employeeRepo.save(employee);
		}

		employee.setEmployeeName(employeeChangeRequest.name());

		if (employeeChangeRequest.joinDate() != null) {
			employee.setJoinDate(employeeChangeRequest.joinDate());
		}

		if (employeeChangeRequest.position() != null) {
			employee.setPosition(employeeChangeRequest.position());
		}

		if (employeeChangeRequest.age() != 0) {
			employee.setEmployeeAge(employeeChangeRequest.age());
		}

		if (employeeChangeRequest.salary() != 0) {
			employee.setSalary(employeeChangeRequest.salary());
		}

		Employee savedEmp = employeeRepo.save(employee);
		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setEmployeeAge(savedEmp.getEmployeeAge());
		employeeResponse.setEmployeeName(savedEmp.getEmployeeName());
		employeeResponse.setJoinDate(savedEmp.getJoinDate());
		employeeResponse.setPosition(savedEmp.getPosition());
		employeeResponse.setSalary(savedEmp.getSalary());

		return employeeResponse;
	}

	public Page<EmployeeResponse> searchEmployees(String query, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return employeeRepo.findByEmployeeNameContainingIgnoreCaseOrPositionContainingIgnoreCase(query, query, pageable)
				.map(e -> {
					EmployeeResponse r = new EmployeeResponse();
					r.setEmployeeId(e.getEmployeeId());
					r.setEmployeeName(e.getEmployeeName());
					r.setEmployeeAge(e.getEmployeeAge());
					r.setPosition(e.getPosition());
					r.setSalary(e.getSalary());
					r.setJoinDate(e.getJoinDate());
					return r;
				});
	}

}
