package mk.ukim.finki.wp.schedulero.service;



import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.schedulero.enums.UserRole;
import mk.ukim.finki.wp.schedulero.model.AppUser;
import mk.ukim.finki.wp.schedulero.model.Customer;
import mk.ukim.finki.wp.schedulero.repository.AppUserRepository;
import mk.ukim.finki.wp.schedulero.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final CustomerRepository CustomerRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username))
            throw new RuntimeException("Username already taken");
        if (userRepository.existsByEmail(email))
            throw new RuntimeException("Email already in use");

        userRepository.save(AppUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(UserRole.ROLE_USER)
                .build());


        CustomerRepository.save(Customer.builder()
                .username(username)
                .name(username)
                .email(email)
                .build());
    }

    public void registerBusiness(String username, String email, String password,
                                 String businessName, String phone) {
        if (userRepository.existsByUsername(username))
            throw new RuntimeException("Username already taken");

        userRepository.save(AppUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(UserRole.ROLE_BUSINESS)
                .businessName(businessName)
                .phoneNumber(phone)
                .build());


    }
}