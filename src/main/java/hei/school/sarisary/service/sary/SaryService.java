package hei.school.sarisary.service.sary;

import hei.school.sarisary.file.BucketComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

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
        File file = new File(String.format("%s/%s", path, image.getOriginalFilename()));
        BufferedImage result = convertToGrayscale(convertToBufferedImage(convertMultipartFileToBufferedImage((image))));
        ImageIO.write(result, "png", file);
        sendFile(file, bucketComponent.getBucketName());
    }
}
