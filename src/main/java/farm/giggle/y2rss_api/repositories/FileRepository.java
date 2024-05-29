package farm.giggle.y2rss_api.repositories;

import farm.giggle.y2rss_api.model.FileJournal;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface FileRepository extends CrudRepository<FileJournal, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE files SET downloaded_url = :downloadedUrl, downloaded_at = :downloadedAt WHERE id = :fileId")
    void updateDownloadFields(Long fileId, String downloadedUrl, LocalDateTime downloadedAt);
}
