package farm.giggle.y2rss_api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileJournal(UUID id, String url, LocalDateTime publicationTime, UUID userUUID, Long fileID, LocalDateTime processingTime) {
    public FileJournal(FileJournal fileJournal, LocalDateTime processingTime) {
         this(fileJournal.id, fileJournal.url, fileJournal.publicationTime, fileJournal.userUUID, fileJournal.fileID, processingTime);
    }
}

