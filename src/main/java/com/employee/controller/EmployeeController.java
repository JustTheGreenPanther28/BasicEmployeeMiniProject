package com.employee.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.request.EmployeeAdditionRequest;
import com.employee.response.EmployeeResponse;
import com.employee.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public ResponseEntity<Boolean> addEmployees(@RequestBody EmployeeAdditionRequest employeeAdditionRequest) {
		return ResponseEntity.ok(employeeService.addEmployee(employeeAdditionRequest));
	}

	@GetMapping
	public ResponseEntity<Page<EmployeeResponse>> getEmployees(@RequestParam int page, @RequestParam int size) {
		return ResponseEntity.ok(employeeService.getEmployees(page, size));
	}

	@GetMapping("/report/{id}")
	public ResponseEntity<List<EmployeeResponse>> getReportOfAEmployee(@PathVariable String id) {
		return ResponseEntity.ok(employeeService.getEmployeesReport(UUID.fromString(id)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
		if (employeeService.deleteEmployee(UUID.fromString(id))) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.badRequest().build();
	}
}