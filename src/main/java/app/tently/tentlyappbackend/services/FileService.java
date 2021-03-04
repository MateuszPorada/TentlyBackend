package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.FileModel;
import app.tently.tentlyappbackend.repos.FileRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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

    public String save(MultipartFile file) throws IOException {
        return MapFileModel(file);
    }

    public List<String> save(MultipartFile[] files) throws IOException {
        List<String> stringList = new ArrayList<>();
        for (MultipartFile file : files) {
            stringList.add(MapFileModel(file));
        }
        return stringList;
    }

    private String MapFileModel(MultipartFile file) throws IOException {
        FileModel fileModel = new FileModel();
        fileModel.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        fileModel.setContentType(file.getContentType());
        fileModel.setData(file.getBytes());
        fileModel.setSize(file.getSize());
        fileRepo.save(fileModel);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(fileModel.getId())
                .toUriString();
    }


    public Optional<FileModel> getFile(String id) {
        return fileRepo.findById(id);
    }

    public List<FileModel> getAllFiles() {
        return fileRepo.findAll();
    }
}
