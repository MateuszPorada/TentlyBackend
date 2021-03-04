package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.FileModel;
import app.tently.tentlyappbackend.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
public class ImageController {

    private final FileService fileService;

    public ImageController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "image")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) {
        try {
            fileService.save(file);
            return new ResponseEntity<>("File succesfully uploaded", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping(value = "image")
//    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) {
//        try {
//            for (MultipartFile file : files) {
//                fileService.save(file);
//            }
//            return new ResponseEntity<>("Files succesfully uploaded", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Could not upload file", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("image/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<FileModel> fileEntityOptional = fileService.getFile(id);
        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        FileModel fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }

//    @PostMapping("image/upload")
//    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) throws IOException {
//
//        for (MultipartFile file : files) {
//            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//            return new ResponseEntity<>(uploadResult, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

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

//    @RequestMapping(value = "/image-manual-response", method = Re-questMethod.GET)
//    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
//        InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        IOUtils.copy(in, response.getOutputStream());
//    }


}
