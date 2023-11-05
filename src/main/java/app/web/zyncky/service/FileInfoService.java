package app.web.zyncky.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.web.zyncky.dto.FileInfoDto;
import app.web.zyncky.exception.FileMissingException;
import app.web.zyncky.model.FileInfo;
import app.web.zyncky.model.User;
import app.web.zyncky.repo.FileInfoRepository;
import app.web.zyncky.util.CommonUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileInfoService {

    private final FileInfoRepository fileInfoRepo;

    private final UserService userService;

    private FileInfoDto convertToDto(FileInfo fileInfo) {
        if (Objects.isNull(fileInfo))
            throw new IllegalArgumentException("FileInfo object is Invalid. Unable to process!");

        FileInfoDto fileInfoDto = new FileInfoDto();
        BeanUtils.copyProperties(fileInfo, fileInfoDto, "user");
        fileInfoDto.setUsername(fileInfo.getUser().getUserName());
        return fileInfoDto;
    }

    public FileInfoDto save(FileInfoDto fileInfoDto) throws Exception {
        if (Objects.isNull(fileInfoDto))
            throw new IllegalArgumentException("FileInfo object is Invalid. Unable to process!");

        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(fileInfoDto, fileInfo, "id", "username");
        fileInfo.setUser(userService.findByUserNameAndReturnUser(fileInfoDto.getUsername()));
        fileInfo.setUid(CommonUtils.generateToken());
        fileInfo.setCreatedAt(new Date());
        return convertToDto(fileInfoRepo.save(fileInfo));
    }

    public FileInfoDto findByUid(String uid) throws Exception {
        if (StringUtils.hasText(uid))
            throw new IllegalArgumentException("Uid is Invalid");

        FileInfo fileInfo = fileInfoRepo.findByUid(uid)
                .orElseThrow(() -> new FileMissingException("File not found!"));
        return convertToDto(fileInfo);
    }

    public List<FileInfoDto> findByUsername(String username) throws Exception {
        if (StringUtils.hasText(username))
            throw new IllegalArgumentException("username is Invalid");

        User user = userService.findByUserNameAndReturnUser(username);
        List<FileInfoDto> files = fileInfoRepo.findByUser(user).stream()
                .map(item -> convertToDto(item)).collect(Collectors.toList());
        return files;
    }

    public void deleteByUid(String uid) throws Exception {
        if (!StringUtils.hasText(uid))
            throw new IllegalArgumentException("Uid is Invalid");

        Optional<FileInfo> dbEntry = fileInfoRepo.findByUid(uid);
        if (dbEntry.isEmpty())
            throw new FileMissingException("File not found!");

        fileInfoRepo.delete(dbEntry.get());
    }
}
