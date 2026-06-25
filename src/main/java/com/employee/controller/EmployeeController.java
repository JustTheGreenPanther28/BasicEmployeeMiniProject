package com.employee.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.request.EmployeeAdditionRequest;
import com.employee.response.EmployeeResponse;
import com.employee.service.EmployeeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
		RequestMethod.PATCH })

//201 for successful put/post
//204 for successful deletion
//404 for employee not found
//409 conflict
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@PostMapping
	public ResponseEntity<?> addEmployees(@Valid @RequestBody EmployeeAdditionRequest employeeAdditionRequest) {
		return ResponseEntity.status(HttpStatusCode.valueOf(201))
				.body(employeeService.addEmployee(employeeAdditionRequest));
	}

	@GetMapping
	public ResponseEntity<Page<EmployeeResponse>> getEmployees(@RequestParam @Min(0) int page,
			@RequestParam @Min(0) int size, @RequestParam(defaultValue = "employeeName") String sortBy,
			@RequestParam(defaultValue = "asc") String order) {
		return ResponseEntity.ok(employeeService.getEmployees(page, size, sortBy, order));
	}

	@GetMapping("/report/{id}")
	public ResponseEntity<?> getReportOfAEmployee(@PathVariable @NotNull UUID id) {
		return ResponseEntity.ok(employeeService.getEmployeesReport(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable @NotNull String id) {
		employeeService.deleteEmployee(UUID.fromString(id));
		return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();

	}

	// Multiple Id deletion
	@DeleteMapping("/ids")
	public ResponseEntity<Void> deleteEmployees(@Valid @NotNull @RequestBody List<String> ids) {
		if (employeeService.deleteEmployees(ids)) {
			return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<EmployeeResponse> updateEmployees(@PathVariable @NotNull UUID id,
			@Valid @RequestBody EmployeeAdditionRequest employeeChangeRequest) {
		return ResponseEntity.ok(employeeService.updateEmployee(id, employeeChangeRequest));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<EmployeeResponse>> searchEmployees(@RequestParam String query,
			@RequestParam @Min(0) int page, @RequestParam int size) {
		return ResponseEntity.ok(employeeService.searchEmployees(query, page, size));
	}

}