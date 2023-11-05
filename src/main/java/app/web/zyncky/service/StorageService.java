package app.web.zyncky.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import app.web.zyncky.constant.FileTypeEnum;
import app.web.zyncky.dto.FileInfoDto;
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

    private final FileInfoService fileInfoService;

    private String parseFilePath(String fileNameWithExt) throws Exception {
        if (fileNameWithExt == null)
            throw new NullPointerException("Invalid File Name");

        final String loggedInUsername = customBeanUtils.getUserNameFromJWTSession();
        return appStoragePath.concat(File.separator).concat(appName).concat(File.separator).concat(loggedInUsername)
                .concat(File.separator).concat(fileNameWithExt);
    }

    public FileInfoDto createNewFile(MultipartFile file) throws Exception {
        if (Objects.isNull(file))
            throw new IllegalArgumentException("Uploaded File (or) its content is invalid");

        final String loggedInUsername = customBeanUtils.getUserNameFromJWTSession();
        final String fileNameWithExt = file.getOriginalFilename();
        final String fileExtension = StringUtils.getFilenameExtension(fileNameWithExt);
        final FileTypeEnum fileType = FileTypeEnum.parse(fileExtension);

        Path filePath = Paths.get(parseFilePath(fileNameWithExt));
        Files.createDirectories(filePath.getParent());
        Files.createFile(filePath);
        Files.write(filePath, file.getBytes());

        FileInfoDto fileInfoDto = FileInfoDto.builder().filename(fileNameWithExt).fileType(fileType)
                .createdAt(new Date()).storagePath(filePath.toAbsolutePath().toString())
                .username(loggedInUsername).build();
        return fileInfoService.save(fileInfoDto);
    }
}
