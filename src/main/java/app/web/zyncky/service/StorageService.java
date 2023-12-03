package app.web.zyncky.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Resource> testFileStreamAndDownload(Integer option) throws Exception {
        // final String loggedInUsername = "dev";
        final String pdfFilePath = "/home/suresh/Project/AppData/iShare/dev/Resume.pdf";
        final String txtFilePath = "/home/suresh/Project/AppData/iShare/dev/Snap_Common_Error_Fix.txt";
        final String pngFilePath = "/home/suresh/Project/AppData/iShare/dev/Zyncky icon (Design 2).png";

        // Path pdfPath = Paths.get(pdfFilePath);
        // Resource pdfResource = new UrlResource(pdfPath.toUri());
        // return pdfResource;
        Path filePath = null;
        MediaType contentType = MediaType.TEXT_PLAIN;

        option = Objects.isNull(option) ? 0 : option;
        switch (option) {
            case 2: {
                filePath = Paths.get(pdfFilePath);
                contentType = MediaType.APPLICATION_PDF;
            }
                break;

            case 3: {
                filePath = Paths.get(pngFilePath);
                contentType = MediaType.IMAGE_PNG;
            }
                break;
            case 1:
            default: {
                filePath = Paths.get(txtFilePath);
                contentType = MediaType.TEXT_PLAIN;
            }
                break;
        }

        Resource fileResource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok().contentType(contentType).body(fileResource);
    }

    public ResponseEntity<Resource> downloadFile(String uid) throws Exception {
        FileInfoDto fileInfoDto = fileInfoService.findByUid(uid);
        MediaType contentType = parseContentType(fileInfoDto.getFileType());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"".concat(fileInfoDto.getFilename())+"\"");
        Path filePath = Paths.get(fileInfoDto.getStoragePath());
        ByteArrayResource byteArrResource = new ByteArrayResource(Files.readAllBytes(filePath));
        return ResponseEntity.ok().contentLength(filePath.toFile().length()).headers(httpHeaders)
                .contentType(contentType).body(byteArrResource);
    }

    public ResponseEntity<Resource> streamFileContents(String uid) throws Exception {
        FileInfoDto fileInfoDto = fileInfoService.findByUid(uid);
        MediaType contentType = parseContentType(fileInfoDto.getFileType());
        Path filePath = Paths.get(fileInfoDto.getStoragePath());
        Resource fileResource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok().contentType(contentType).body(fileResource);
    }

    private MediaType parseContentType(FileTypeEnum fileTypeEnum) {
        switch (fileTypeEnum) {
            case SVG:
            case PNG:
                return MediaType.IMAGE_PNG;
            case PDF:
                return MediaType.APPLICATION_PDF;
            case MP4:
                return MediaType.APPLICATION_OCTET_STREAM;
            case TXT:
            default:
                return MediaType.TEXT_PLAIN;
        }
    }

}
