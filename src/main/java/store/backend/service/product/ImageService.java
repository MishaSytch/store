package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Image;
import store.backend.database.repository.ImageRepository;

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

    public Image updateImage(Image image) {
        assert imageRepository.findById(image.getId()).isPresent();

        return imageRepository.save(image);
    }

    public void deleteImage(Long image_id) {
        if (image_id != null) imageRepository.findById(image_id).ifPresent(
            image ->{
                    image.getProduct().removeImage(image);
                    imageRepository.deleteById(image_id);
            }
        );
    }

    public Image getImage(Long image_id) {
        return imageRepository.findById(image_id).orElse(null);
    }
}
