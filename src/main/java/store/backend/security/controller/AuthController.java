package store.backend.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.backend.security.dto.JwtAuthenticationResponse;
import store.backend.security.dto.SignInRequest;
import store.backend.security.dto.SignUpRequest;
import store.backend.security.service.AuthenticationService;
import store.backend.security.role.Role;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request, Role.CUSTOMER);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}