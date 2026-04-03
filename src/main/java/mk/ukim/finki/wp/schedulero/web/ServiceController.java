package mk.ukim.finki.wp.schedulero.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.schedulero.model.AppUser;
import mk.ukim.finki.wp.schedulero.model.DetailService;
import mk.ukim.finki.wp.schedulero.repository.AppUserRepository;
import mk.ukim.finki.wp.schedulero.repository.DetailServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final DetailServiceRepository serviceRepository;
    private final AppUserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<DetailService>> getByBusiness(
            @RequestParam(required = false) Long businessId) {
        if (businessId != null) {
            return ResponseEntity.ok(serviceRepository.findByBusiness_Id(businessId));
        }
        return ResponseEntity.ok(serviceRepository.findAll());
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody ServiceRequest req,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        AppUser business = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DetailService service = DetailService.builder()
                .name(req.name())
                .description(req.description())
                .durationMinutes(req.durationMinutes())
                .price(req.price())
                .business(business)
                .build();

        return ResponseEntity.ok(serviceRepository.save(service));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        DetailService service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!service.getBusiness().getUsername().equals(userDetails.getUsername())) {
            return ResponseEntity.status(403).body("Not your service");
        }

        serviceRepository.delete(service);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/mine")
    public ResponseEntity<List<DetailService>> getMine(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                serviceRepository.findByBusiness_Username(userDetails.getUsername())
        );
    }

    public record ServiceRequest(
            String name,
            String description,
            int durationMinutes,
            BigDecimal price
    ) {}
}