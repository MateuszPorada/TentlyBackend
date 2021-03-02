package app.tently.tentlyappbackend.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class FileController {

    @PostMapping("image/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "tentlyapp",
                "api_key", "632641837842151",
                "api_secret", "Aa_m2i8fAPKishAagBu5jULa_Zs"));
        for (MultipartFile file : files) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return new ResponseEntity<>(uploadResult, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping("image/upload")
//    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
//        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "tentlyapp",
//                "api_key", "632641837842151",
//                "api_secret", "Aa_m2i8fAPKishAagBu5jULa_Zs"));
//        for (MultipartFile file : files) {
//            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//            return new ResponseEntity<>(uploadResult, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void getImage(HttpServletResponse response, @PathVariable String name) throws IOException {
//        System.out.println("images/" + name);
//        var imgFile = new ClassPathResource("images/" + name);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
//    }

//    @GetMapping(value = "/image")
//    public void getFile(HttpServletResponse response) throws IOException {
//        InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image.jpg");
//        System.out.println(in);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        IOUtils.copy(in, response.getOutputStream());
//    }

//    @RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
//    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
//        InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        IOUtils.copy(in, response.getOutputStream());
//    }


}
