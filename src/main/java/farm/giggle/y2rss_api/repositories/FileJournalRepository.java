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
    @Query("SELECT * FROM file_journal WHERE processing_time IS NULL ORDER BY publication_time DESC FOR UPDATE SKIP LOCKED LIMIT 1")
    FileJournal getTopRecordAndLock();

    @Transactional
    @Query("SELECT * FROM file_journal WHERE id = :journalID and processing_time = :processingTime FOR UPDATE SKIP LOCKED")
    FileJournal getRecordAndLock(UUID journalID, LocalDateTime processingTime);

    @Transactional
    @Modifying
    @Query("UPDATE file_journal SET processing_time = :processing_time WHERE id = :id")
    void updateProcessingTimeById(UUID id, LocalDateTime processing_time);

    @Transactional
    @Modifying
    @Query("DELETE FROM file_journal where id = :journalId")
    void deleteRecord(UUID journalId);
}
