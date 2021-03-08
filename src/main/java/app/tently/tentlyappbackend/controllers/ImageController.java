package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.ImageModel;
import app.tently.tentlyappbackend.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("image")
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok().body(imageService.getList());
    }

    @GetMapping("image/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<ImageModel> fileEntityOptional = imageService.getImage(id);
        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        ImageModel fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }

    @PostMapping(value = "image")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) {
        try {
            List<String> stringList = imageService.save(files);
            return new ResponseEntity<>(stringList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not upload files", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "image/{id}")
    public ResponseEntity<Object> delte(@PathVariable String id) {
        Optional<ImageModel> imageModel = imageService.getImage(id);
        if (imageModel.isPresent()) {
            imageService.deleteImage(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
