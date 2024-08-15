package store.backend.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Customer;
import store.backend.database.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Customer save(Customer customer) {
        return repository.save(customer);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public Customer create(Customer customer) {
        if (repository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(customer);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public Customer getByEmail(String email) {
        return repository.findByEmail(email)
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
        return this::getByEmail;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public Customer getCurrentCustomer() {
        // Получение имени пользователя из контекста Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(username);
    }
}
