package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Image;
import store.backend.database.entity.Product;
import store.backend.database.repository.ImageRepository;

@Service
class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public Image addImage(Product product, Image image) {
        product.addImage(image);

        return image;
    }
}
