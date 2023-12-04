package app.web.zyncky.dto;

import java.util.Date;

import app.web.zyncky.constant.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfoDto {

    private Integer id;

    private String uid;

    private String filename;

    private FileTypeEnum fileType;

    private String storagePath;

    private Date createdAt;

    private String username;

}
