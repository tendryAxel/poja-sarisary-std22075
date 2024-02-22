package hei.school.sarisary.endpoint;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SaryController {
    @PutMapping(value = "/black-and-white/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String send(@PathVariable String id, @RequestPart MultipartFile image){
        return id;
    }
}
