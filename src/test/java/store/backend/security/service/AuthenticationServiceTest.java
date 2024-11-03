package store.backend.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.backend.security.dto.JwtAuthenticationResponse;
import store.backend.security.dto.SignInRequest;
import store.backend.security.dto.SignUpRequest;
import store.backend.security.role.Role;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;


    @Test
    void signUp() {
        SignUpRequest request = new SignUpRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        Role role = Role.CUSTOMER;

        JwtAuthenticationResponse response = authenticationService.signUp(request, role);

        assertNotNull(response.getToken());
    }

    @Test
    void signIn() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");
        signUpRequest.setEmail("john.doe@example.com");
        signUpRequest.setPassword("password");

        Role role = Role.CUSTOMER;

        authenticationService.signUp(signUpRequest, role);


        SignInRequest request = new SignInRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        JwtAuthenticationResponse response = authenticationService.signIn(request);

        assertNotNull(response.getToken());
    }
}