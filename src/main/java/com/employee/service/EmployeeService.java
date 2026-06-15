package com.employee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.employee.request.EmployeeAdditionRequest;
import com.employee.response.EmployeeResponse;
import com.employee.response.ReportEmployee;

public interface EmployeeService {
	boolean addEmployee(EmployeeAdditionRequest employeeAdditionRequest);
	
	Page<EmployeeResponse> getEmployees(int page,int size);
	List<ReportEmployee> getEmployeesReport(UUID id);
	
	boolean deleteEmployee(UUID publicId);
}
