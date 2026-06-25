package com.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {


//	@Query("SELECT e FROM Employee e WHERE " + "LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :query, '%')) OR "
//			+ "LOWER(e.position) LIKE LOWER(CONCAT('%', :query, '%'))") <--- USING QUERY
	Page<Employee> findByEmployeeNameContainingIgnoreCaseOrPositionContainingIgnoreCase(
		    String employeeName, String position, Pageable pageable);
}
