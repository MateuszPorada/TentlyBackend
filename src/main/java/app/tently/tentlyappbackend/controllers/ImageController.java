package app.tently.tentlyappbackend.controllers;

import app.tently.tentlyappbackend.models.FileModel;
import app.tently.tentlyappbackend.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
public class ImageController {

    private final FileService fileService;

    public ImageController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping(value = "image")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] files) {
        try {
            List<String> stringList = fileService.save(files);
            return new ResponseEntity<>(stringList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not upload files", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
}
