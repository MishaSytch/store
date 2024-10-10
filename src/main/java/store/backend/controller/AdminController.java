package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.backend.database.repository.CategoryRepository;
import store.backend.database.repository.ImageRepository;
import store.backend.database.repository.ProductRepository;
import store.backend.database.repository.OrderRepository;
import store.backend.database.repository.PriceRepository;
import store.backend.database.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Iterable<Object> getAllFromRepository() {
        List<Object> all = new ArrayList<>();

        all.addAll(categoryRepository.findAll());
        all.addAll(imageRepository.findAll());
        all.addAll(orderRepository.findAll());
        all.addAll(priceRepository.findAll());
        all.addAll(productRepository.findAll());
        all.addAll(userRepository.findAll());

        return all;
    }
}
