package com.employee.response;

import java.time.LocalDateTime;

import com.employee.entity.Employee;

public class EmployeeResponse {
	private String employeeId;
	private String employeeName;
	private int employeeAge;
	private String position;
	private double salary;
	private LocalDateTime joinDate;
	private String reportTo;

	public EmployeeResponse() {

	}

	public EmployeeResponse(Employee employee) {
		this.employeeId = employee.getEmployeeId();
		this.employeeName = employee.getEmployeeName();
		this.employeeAge = employee.getEmployeeAge();
		this.position = employee.getPosition();
		this.salary = employee.getSalary();
		this.joinDate = employee.getJoinDate();
		if (employee.getReportTo() != null) {
			this.reportTo = employee.getReportTo().getEmployeeName();
		}
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeAge() {
		return employeeAge;
	}

	public void setEmployeeAge(int employeeAge) {
		this.employeeAge = employeeAge;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDateTime getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDateTime joinDate) {
		this.joinDate = joinDate;
	}

	public String getReportTo() {
		return reportTo;
	}

	public void setReportTo(String reportTo) {
		this.reportTo = reportTo;
	}
}
