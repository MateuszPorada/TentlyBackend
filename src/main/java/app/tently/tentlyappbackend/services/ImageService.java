package app.tently.tentlyappbackend.services;

import app.tently.tentlyappbackend.models.ImageModel;
import app.tently.tentlyappbackend.models.ImageResponseModel;
import app.tently.tentlyappbackend.repos.ImageRepo;
import org.modelmapper.ModelMapper;
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
public class ImageService {

    private final ImageRepo imageRepo;
    private final ModelMapper modelMapper;

    public ImageService(ImageRepo imageRepo, ModelMapper modelMapper) {
        this.imageRepo = imageRepo;
        this.modelMapper = modelMapper;
    }


    public List<String> save(MultipartFile[] images) throws IOException {
        List<String> stringList = new ArrayList<>();
        for (MultipartFile image : images) {
            stringList.add(MapImageModel(image));
        }
        return stringList;
    }

    private String MapImageModel(MultipartFile image) throws IOException {
        ImageModel imageModel = new ImageModel();
        InputStream in = new ByteArrayInputStream(image.getBytes());
        BufferedImage originalImage = ImageIO.read(in);

        imageModel.setName(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
        imageModel.setContentType(image.getContentType());
        imageModel.setData(image.getBytes());
        imageModel.setWidth(originalImage.getWidth());
        imageModel.setHeight(originalImage.getHeight());
        System.out.println(originalImage.getType());
        System.out.println(originalImage.getTransparency());
        imageModel.setSize(image.getSize());
        imageRepo.save(imageModel);
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(imageModel.getId())
                .toUriString();
    }


    public Optional<ImageModel> getImage(String id) {
        return imageRepo.findById(id);
    }

    public void deleteImage(String id) {
        imageRepo.deleteById(id);
    }

    public List<ImageModel> getAllFiles() {
        return imageRepo.findAll();
    }

    public List<ImageResponseModel> getList() {
        List<ImageModel> imageModels = imageRepo.findAll();
        List<ImageResponseModel> imageResponseModels = new ArrayList<>();
        for (ImageModel imageModel : imageModels) {
            ImageResponseModel imageResponseModel = modelMapper.map(imageModel, ImageResponseModel.class);
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/")
                    .path(imageModel.getId())
                    .toUriString();
            imageResponseModel.setImageUrl(url);
            imageResponseModels.add(imageResponseModel);
        }
        return imageResponseModels;
    }
}
