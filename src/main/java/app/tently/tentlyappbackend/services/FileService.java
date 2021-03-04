package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.FileModel;
import app.tently.tentlyappbackend.repos.FileRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepo fileRepo;

    public FileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    public void save(MultipartFile file) throws IOException {
        FileModel fileModel = new FileModel();
        fileModel.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
        fileModel.setContentType(file.getContentType());
        fileModel.setData(file.getBytes());
        fileModel.setSize(file.getSize());
        fileRepo.save(fileModel);
    }

    public Optional<FileModel> getFile(String id) {
        return fileRepo.findById(id);
    }

    public List<FileModel> getAllFiles() {
        return fileRepo.findAll();
    }
}
