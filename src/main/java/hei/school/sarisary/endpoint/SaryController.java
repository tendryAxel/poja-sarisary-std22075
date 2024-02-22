package hei.school.sarisary.endpoint;

import hei.school.sarisary.service.sary.SaryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@Getter
public class SaryController {
    SaryService saryService;

    public static final ResponseEntity<String> OK = new ResponseEntity<>("OK", HttpStatus.OK);
    public static final ResponseEntity<String> KO =
            new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);

    @PutMapping(value = "/black-and-white/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> send(@PathVariable String id, @RequestPart MultipartFile image) throws IOException {
        saryService.grayScale(image);
        return OK;
    }
}
