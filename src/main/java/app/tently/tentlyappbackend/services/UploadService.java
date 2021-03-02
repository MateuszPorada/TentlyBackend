package app.tently.tentlyappbackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UploadService {

    public List<String> upload(MultipartFile[] files) throws IOException {
        List<String> urls = new ArrayList<>();
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "tentlyapp",
                "api_key", "632641837842151",
                "api_secret", "Aa_m2i8fAPKishAagBu5jULa_Zs"));
        for (MultipartFile file : files) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            urls.add((String) uploadResult.get("secure_url"));
        }
        return urls;
    }
}
