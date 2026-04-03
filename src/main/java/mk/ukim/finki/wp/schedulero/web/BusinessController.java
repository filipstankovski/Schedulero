package mk.ukim.finki.wp.schedulero.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.schedulero.enums.UserRole;
import mk.ukim.finki.wp.schedulero.repository.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final AppUserRepository userRepository;

    public record BusinessDto(Long id, String businessName, String username) {}

    @GetMapping
    public ResponseEntity<List<BusinessDto>> getAll() {
        List<BusinessDto> businesses = userRepository.findByRole(UserRole.ROLE_BUSINESS)
                .stream()
                .map(u -> new BusinessDto(u.getId(), u.getBusinessName(), u.getUsername()))
                .toList();
        return ResponseEntity.ok(businesses);
    }
}