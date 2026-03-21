package mk.ukim.finki.wp.schedulero.repository;

import mk.ukim.finki.wp.schedulero.model.DetailService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailServiceRepository
        extends JpaRepository<DetailService, Long> {

}