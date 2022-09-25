package com.example.electronicshop.service;

import com.example.electronicshop.users.SpecificUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.example.electronicshop.service.UploadAvatar.SPECIFIC_USER;
import static com.example.electronicshop.servlets.DrawImage.IMAGE_STORAGE_PATH;

public class ImageServiceImpl implements ImageService{
    public static final String UNKNOWN_AVATAR_URL = "uploadDir/4/unknown.jpg";
    @Override
    public void drawImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        SpecificUser specificUser = (SpecificUser) session.getAttribute(SPECIFIC_USER);
        String imageStorage = session.getServletContext().getInitParameter(IMAGE_STORAGE_PATH);
        String imageURL;
        if (specificUser == null || specificUser.getAvatarUrl() == null || specificUser.getAvatarUrl().isEmpty()) {
            imageURL =imageStorage + UNKNOWN_AVATAR_URL;
        } else {
            imageURL = imageStorage + specificUser.getAvatarUrl();
        }
        BufferedImage bufferedImage = ImageIO.read(new File(imageURL));
        OutputStream osImage = resp.getOutputStream();
        ImageIO.write(bufferedImage, "jpeg", osImage);
        osImage.close();
    }
}
