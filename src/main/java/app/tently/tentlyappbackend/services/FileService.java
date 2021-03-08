package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.ImageModel;
import app.tently.tentlyappbackend.repos.FileRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepo fileRepo;

    public FileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }


    public List<String> save(MultipartFile[] files) throws IOException {
        List<String> stringList = new ArrayList<>();
        for (MultipartFile file : files) {
            stringList.add(MapFileModel(file));
        }
        return stringList;
    }

    private String MapFileModel(MultipartFile file) throws IOException {
        ImageModel imageModel = new ImageModel();
        InputStream in = new ByteArrayInputStream(file.getBytes());
        BufferedImage originalImage = ImageIO.read(in);

        imageModel.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        imageModel.setContentType(file.getContentType());
        imageModel.setData(file.getBytes());
        imageModel.setWidth(originalImage.getWidth());
        imageModel.setHeight(originalImage.getHeight());
        System.out.println(originalImage.getType());
        System.out.println(originalImage.getTransparency());
        imageModel.setSize(file.getSize());
        fileRepo.save(imageModel);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(imageModel.getId())
                .toUriString();
    }


    public Optional<ImageModel> getFile(String id) {
        return fileRepo.findById(id);
    }

    public List<ImageModel> getAllFiles() {
        return fileRepo.findAll();
    }
}
