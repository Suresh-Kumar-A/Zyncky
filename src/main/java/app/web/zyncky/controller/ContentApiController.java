package app.web.zyncky.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.web.zyncky.dto.FileInfoDto;
import app.web.zyncky.service.StorageService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentApiController {

    private final StorageService storageService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public FileInfoDto uploadFiles(@RequestParam("file") MultipartFile file) throws Exception {
        return storageService.createNewFile(file);
    }

    @GetMapping(value = "/{uid}/download")
    public void downloadFiles(@PathVariable String uid) {

    }

    @GetMapping(value = "/{uid}/view")
    public void viewFilesContent(@PathVariable String uid) {

    }

}
