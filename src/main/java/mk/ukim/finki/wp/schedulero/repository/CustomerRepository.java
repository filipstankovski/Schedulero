package mk.ukim.finki.wp.schedulero.repository;

import mk.ukim.finki.wp.schedulero.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhone(String phone);
    Optional<Customer> findByUsername(String username);

}
