package doctorhoai.learn.indentity_service.business.upload;

import com.cloudinary.Cloudinary;
import doctorhoai.learn.indentity_service.dto.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("upload")
public class UploadController {

    private final Cloudinary cloudinary;

    @PostMapping("/image")
    public ResponseEntity<ResponseObject>uploadImage(
            @RequestParam("image")MultipartFile file
            ) throws IOException {
        String path =  cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString())).get("url").toString();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("Upload successfully!")
                        .data(path)
                        .build()
        );
    }

}
