package mk.ukim.finki.wp.schedulero.repository;

import mk.ukim.finki.wp.schedulero.model.DetailService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailServiceRepository extends JpaRepository<DetailService, Long> {
    List<DetailService> findByBusiness_Id(Long businessId);
    List<DetailService> findByBusiness_Username(String username);
}