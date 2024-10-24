package store.backend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder extends BCryptPasswordEncoder{
}

//@Bean
//public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//}