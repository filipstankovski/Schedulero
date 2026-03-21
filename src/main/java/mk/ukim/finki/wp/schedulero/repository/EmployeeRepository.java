package mk.ukim.finki.wp.schedulero.repository;

import mk.ukim.finki.wp.schedulero.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

}