package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Image;
import store.backend.database.repository.ImageRepository;

import java.sql.SQLDataException;
import java.sql.SQLException;

@Service
class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image createImage(String name, String ref) {
        return saveImage(
                Image.builder()
                    .name(name)
                    .reference(ref)
                    .build()
        );
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public Image updateImage(Long image_id, Image editedImage) {
        return imageRepository.findById(image_id).map(
                image -> {
                    image.setName(editedImage.getName());
                    image.setReference(editedImage.getReference());
                    imageRepository.deleteById(image_id);

                    return imageRepository.save(image);
                }
        ).orElse(null);
    }

    public void deleteImage(Long image_id) {
        imageRepository.findById(image_id).ifPresent(
                image -> image.getProduct().removeImage(image)
        );
        imageRepository.deleteById(image_id);
    }

    public Image getImage(Long image_id) {
        return imageRepository.findById(image_id).orElse(null);
    }
}
