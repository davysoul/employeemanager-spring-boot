package ca.davy.employeemanager.repos;

import ca.davy.employeemanager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepos extends JpaRepository<Employee,Long> {
    Optional<Employee> findEmployeeById(Long id);
    @Query("select  e from Employee e where e.email like:kw or e.name like :kw or e.jobTitle like :kw or e.phone like :kw ")
    List<Employee> searchEmployee(@Param("kw") String keyword);
}
