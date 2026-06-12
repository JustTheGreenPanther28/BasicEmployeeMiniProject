package com.employee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.request.EmployeeAdditionRequest;
import com.employee.response.EmployeeResponse;
import com.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepo;

	public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	@Override
	public Page<EmployeeResponse> getEmployees(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<EmployeeResponse> response = employeeRepo.findAll(pageable).map(employeeEntity -> {
			EmployeeResponse employeeResponse = new EmployeeResponse();
			employeeResponse.setEmployeeAge(employeeEntity.getEmployeeAge());
			employeeResponse.setEmployeeId(employeeEntity.getEmployeeId());
			employeeResponse.setEmployeeName(employeeEntity.getEmployeeName());
			employeeResponse.setJoinDate(employeeEntity.getJoinDate());
			employeeResponse.setPosition(employeeEntity.getPosition());
			employeeResponse.setSalary(employeeEntity.getSalary());
			return employeeResponse;
		});
		return response;
	}

	@Override
	public List<EmployeeResponse> getEmployeesReport(UUID id) {
		List<EmployeeResponse> employeeResponses = new ArrayList<>();
		Employee current = employeeRepo.findByEmployeeId(id);

		while (current.getReportTo() != null) {
			EmployeeResponse employeeResponse = new EmployeeResponse();
			employeeResponse.setEmployeeAge(current.getEmployeeAge());
			employeeResponse.setEmployeeId(current.getEmployeeId());
			employeeResponse.setEmployeeName(current.getEmployeeName());
			employeeResponse.setJoinDate(current.getJoinDate());
			employeeResponse.setPosition(current.getPosition());
			employeeResponse.setSalary(current.getSalary());
			employeeResponses.add(employeeResponse);
			current = current.getReportTo();
		}

		return employeeResponses;
	}

	@Override
	@Transactional
	public boolean deleteEmployee(UUID publicId) {
		Employee employee = employeeRepo.findByEmployeeId(publicId);
		if (employee == null) {
			return false;
		}

		employeeRepo.delete(employee);

		return true;
	}

	@Override
	@Transactional
	public boolean addEmployee(EmployeeAdditionRequest employeeAdditionRequest) {

		Employee employee = new Employee();

		employee.setEmployeeAge(employeeAdditionRequest.age());
		employee.setEmployeeName(employeeAdditionRequest.name());
		employee.setJoinDate(employeeAdditionRequest.joinDate());
		employee.setPosition(employeeAdditionRequest.position());
		employee.setSalary(employeeAdditionRequest.salary());
		Employee reportTo = employeeRepo.findByEmployeeId(employeeAdditionRequest.reportTo());
//		if(reportTo==null) {
//			return false;
//		}
		employee.setReportTo(reportTo);

		employeeRepo.save(employee);

		return true;
	}

//	public byte[] uuidToArray(UUID id) {
//		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
//		bb.putLong(id.getMostSignificantBits());
//		bb.putLong(id.getLeastSignificantBits());
//		byte[] bytes = bb.array();
//		return bytes;
//	}

}
