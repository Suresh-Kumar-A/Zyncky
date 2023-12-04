package app.web.zyncky.model;

import java.util.Date;

import app.web.zyncky.constant.FileTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fileinfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfo {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uid;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private FileTypeEnum fileType;

    @Column(unique = true, nullable = false)
    private String storagePath;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_user_id")
    private User user;

}
