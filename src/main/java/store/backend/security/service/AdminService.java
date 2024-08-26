package store.backend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Admin;
import store.backend.database.entity.Customer;
import store.backend.database.repository.AdminRepository;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public Admin create(Admin admin) {
        if (adminRepository.findByName(admin.getName()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(admin);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public Admin getByName(String name) {
        return adminRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByName;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public Admin getCurrentAdmin() {
        // Получение имени пользователя из контекста Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByName(username);
    }
}
