package store.backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.backend.StoreApp;
import store.backend.database.entity.Customer;
import store.backend.security.dto.JwtAuthenticationResponse;
import store.backend.security.dto.SignInRequest;
import store.backend.security.dto.SignUpRequest;
import store.backend.security.repository.RoleRepository;
import store.backend.security.repository.UserRepository;
import store.backend.security.role.Role;
import store.backend.security.role.UserRole;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerService customerService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Customer user = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
//                .role(getRole(Role.CUSTOMER))
                .build();

        customerService.create(user);

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    private UserRole getRole(Role role) {
        UserRole userRole = UserRole.builder()
                .role(role)
                .build();
        ApplicationContext context = new AnnotationConfigApplicationContext(StoreApp.class);
        RoleRepository repository = context.getBean(RoleRepository.class);
        repository.save(userRole);

        return userRole;
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        UserDetails user = customerService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}