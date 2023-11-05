package app.web.zyncky.service;

import org.springframework.stereotype.Service;

import app.web.zyncky.repo.FileInfoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileInfoService {
    
    private final FileInfoRepository fileInfoRepo;
}
