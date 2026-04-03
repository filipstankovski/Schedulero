package mk.ukim.finki.wp.schedulero.model;


import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.wp.schedulero.enums.UserRole;

@Entity
@Table(name = "app_users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


    private String businessName;
    private String phoneNumber;
}