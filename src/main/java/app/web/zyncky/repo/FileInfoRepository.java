package app.web.zyncky.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import app.web.zyncky.model.FileInfo;
import app.web.zyncky.model.User;

@Repository
public interface FileInfoRepository extends ListCrudRepository<FileInfo, Integer> {

    public Optional<FileInfo> findByUid(String uid);

    public Optional<FileInfo> findByFileName(String fileName);

    public List<FileInfo> findByUser(User user);
}