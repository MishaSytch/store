package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Image;
import store.backend.database.repository.ImageRepository;

import java.util.Optional;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/{id}")
    public Optional<Image> getImage(@PathVariable Long id) {
        return imageRepository.findById(id);
    }

    @PostMapping
    public Image postImage(@RequestBody Image image) {
        imageRepository.save(image);

        return image;
    }

    @PutMapping("/{id}")
    public Image putImage(@RequestBody Image editedImage, @PathVariable Long id) {
        Optional<Image> op_image = imageRepository.findById(id);

        if (op_image.isPresent()) {
            Image image = op_image.get();

            image.setName(editedImage.getName());
            image.setProduct(editedImage.getProduct());
            image.setReference(editedImage.getReference());

            return image;
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        imageRepository.deleteById(id);
    }
}
