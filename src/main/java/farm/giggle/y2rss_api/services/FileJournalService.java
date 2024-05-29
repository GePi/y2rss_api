package farm.giggle.y2rss_api.services;

import farm.giggle.y2rss_api.model.FileJournal;
import farm.giggle.y2rss_api.repositories.FileJournalRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileJournalService {

    private final FileJournalRepository fileJournalRepository;

    public FileJournalService(FileJournalRepository fileJournalRepository) {
        this.fileJournalRepository = fileJournalRepository;
    }

    @Transactional
    @Nullable
    public FileJournal take() {
        FileJournal topRecord = fileJournalRepository.getTopRecordAndLock();
        if (topRecord == null) {
            return null;
        }
        LocalDateTime processingTime = LocalDateTime.now(Clock.systemUTC());
        fileJournalRepository.updateProcessingTimeById(topRecord.id(), processingTime);
        return new FileJournal(topRecord, processingTime);
    }

    @Transactional
    public FileJournal getJournalRecordAndLock(UUID journalID, LocalDateTime processingTime) {
        return fileJournalRepository.getRecordAndLock(journalID, processingTime);
    }

    @Transactional
    public void delete(UUID journalID) {
        fileJournalRepository.deleteRecord(journalID);
    }
}
