package farm.giggle.y2rss_api.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import farm.giggle.y2rss_api.model.FileJournal;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface FileJournalRepository extends CrudRepository<FileJournal, UUID> {
    @Transactional
    @Query("SELECT * FROM file_journal WHERE processing_time IS NULL ORDER BY published_time DESC FOR UPDATE SKIP LOCKED LIMIT 1")
    FileJournal getTopRecord();

    @Transactional
    @Modifying
    @Query("UPDATE file_journal SET proseccing_time = :processing_time WHERE id = :id")
    void updateProcessingTimeById(UUID id, LocalDateTime processing_time);

    @Transactional
    @Modifying
    @Query("DELETE FROM file_journal where id = :journalId and processing_time = :processingTime")
    void deleteRecord(UUID journalID, LocalDateTime processingTime);
}
