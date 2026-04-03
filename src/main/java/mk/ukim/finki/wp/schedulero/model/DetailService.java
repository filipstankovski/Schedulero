package mk.ukim.finki.wp.schedulero.model;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.wp.schedulero.model.AppUser;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DetailService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int durationMinutes;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private AppUser business;
}