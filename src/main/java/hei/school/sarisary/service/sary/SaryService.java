package hei.school.sarisary.service.sary;

import hei.school.sarisary.file.BucketComponent;
import hei.school.sarisary.repository.model.GrayBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@AllArgsConstructor
@Getter
@Service
public class SaryService {
    BucketComponent bucketComponent;

    final String path = "./image";

    private static BufferedImage convertToGrayscale(BufferedImage image) {
        BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = grayscaleImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return grayscaleImage;
    }
    private static BufferedImage convertToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }
    private BufferedImage convertMultipartFileToBufferedImage(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(inputStream);
    }
    private void sendFile(File file, String key){
        bucketComponent.upload(file, String.format("%s/%s", path, key));
    }

    public void grayScale(MultipartFile image) throws IOException {
        File old = Files.createTempFile(createBuckerKey(image.getOriginalFilename()), ".png").toFile();
        sendFile(old, createBuckerKey(image.getOriginalFilename()));
        BufferedImage result = convertToGrayscale(convertToBufferedImage(convertMultipartFileToBufferedImage((image))));
        File transform = Files.createTempFile(createTransformBuckerKey(image.getOriginalFilename()), ".png").toFile();
        ImageIO.write(result, "png", transform);
        sendFile(transform, createTransformBuckerKey(image.getOriginalFilename()));
    }

    public GrayBody getImage(String id){
        GrayBody result = new GrayBody();
        result.setOriginal_url(bucketComponent.download(createBuckerKey(id)).getAbsolutePath());
        result.setTransformed_url(bucketComponent.download(createBuckerKey(id)).getAbsolutePath());
        return result;
    }

    private String createBuckerKey(String id){
        return id;
    }
    private String createTransformBuckerKey(String id){
        return createBuckerKey(String.format("transform-%s", id));
    }
}
