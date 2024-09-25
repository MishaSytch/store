package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Image;
import store.backend.database.entity.Product;
import store.backend.database.repository.ImageRepository;

@Service
class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Transactional
    public Image createImage(String name, String ref) {
        return imageRepository.save(
                Image.builder()
                    .name(name)
                    .reference(ref)
                    .build()
        );
    }

    @Transactional
    public Image updateImage(Long image_id, Image editedImage) {
        return imageRepository.findById(image_id).map(
                image -> {
                    image.setName(editedImage.getName());
                    image.setReference(editedImage.getReference());

                    imageRepository.save(image);
                    return image;
                }
        ).orElse(null);
    }

    @Transactional
    public void deleteImage(Long image_id) {
        imageRepository.findById(image_id).ifPresent(
                image -> image.getProduct().removeImage(image)
        );
    }
}
