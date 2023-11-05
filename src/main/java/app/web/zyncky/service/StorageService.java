package app.web.zyncky.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import app.web.zyncky.constant.FileTypeEnum;
import app.web.zyncky.util.CustomBeanUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {

    @Value("${app.storage.path}")
    private String appStoragePath;

    @Value("${app.name}")
    private String appName;

    private final CustomBeanUtils customBeanUtils;

    private String parseFilePath(String fileNameWithExt) throws Exception {
        if (fileNameWithExt == null)
            throw new NullPointerException("Invalid File Name");

        final String loggedInUsername = customBeanUtils.getUserNameFromJWTSession();
        return appStoragePath.concat(File.separator).concat(appName).concat(File.separator).concat(loggedInUsername)
                .concat(File.separator).concat(fileNameWithExt);
    }

    public void createNewFile(MultipartFile file) throws Exception {
        if (Objects.isNull(file))
            throw new IllegalArgumentException("Uploaded File (or) its content is invalid");

        final String loggedInUsername = customBeanUtils.getUserNameFromJWTSession();
        final String fileNameWithExt = file.getOriginalFilename();
        final String fileExtension = StringUtils.getFilenameExtension(fileNameWithExt);
        final FileTypeEnum fileType = FileTypeEnum.parse(fileExtension);

        Path filePath = Paths.get(parseFilePath(fileNameWithExt));
        Files.createFile(filePath);
        Files.write(filePath, file.getBytes());

        System.out.println(" --- File Uploaded Scuccessfully ---- ");
        System.out.println("File Name: " + fileNameWithExt);
        System.out.println("Uploaded by: " + loggedInUsername);
        System.out.println("Storage path: " + filePath.toAbsolutePath());
        System.out.println(" --- --------- ---- ");
    }
}
