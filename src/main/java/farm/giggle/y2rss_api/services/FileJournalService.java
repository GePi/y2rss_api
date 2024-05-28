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
        FileJournal topRecord = fileJournalRepository.getTopRecord();
        if (topRecord == null) {
            return null;
        }
        fileJournalRepository.updateProcessingTimeById(topRecord.id(), LocalDateTime.now(Clock.systemUTC()));
        return topRecord;
    }

    @Transactional
    public void delete(UUID journalID, LocalDateTime processingTime) {
        fileJournalRepository.deleteRecord(journalID, processingTime);
    }
}
