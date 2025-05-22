package org.ea.cinevibe.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException {
        log.info("Someone try to upload file.");

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Map<?, ?> upload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        return upload.get("secure_url").toString();
    }

    public void deleteImage(String url) throws IOException {
        Map<?, ?> result = cloudinary.uploader().destroy(extractPublicIdFromUrl(url),
                ObjectUtils.emptyMap());
        if ("not found".equals(result.get("result").toString())) {
            throw new NoSuchFileException("Not found image in this url. Url : " + url);
        } else if ("error".equals(result.get("result").toString())) {
            throw new RuntimeException("Something happened . Url : " + url);
        }
    }

    private String extractPublicIdFromUrl(String imageUrl) {
        try {
            String substring = imageUrl.substring(imageUrl.indexOf("/upload/") + 8);

            String[] parts = substring.split("/");
            String publicId = Arrays.stream(parts)
                    .skip(1)
                    .collect(Collectors.joining("/"));

            return publicId.replaceAll("\\.[a-zA-Z0-9]+$", "");
        } catch (Exception e) {
            log.error("Error extracting publicId from URL:" + imageUrl, e);
            return null;
        }
    }
}
