package farm.giggle.y2rss_api.dto;

import farm.giggle.y2rss_api.model.FileJournal;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExchangeFileFormatDTO {
    @NotNull
    final private Long fileId;
    @NotNull
    final private String videoUrl;

    final private UUID journalID;
    final private LocalDateTime processingTime;

    final private String downloadedUrl;
    final private LocalDateTime downloadedAt;

    public ExchangeFileFormatDTO(FileJournal fileJournalRecord) {
        this.fileId = fileJournalRecord.fileID();
        this.videoUrl = fileJournalRecord.url();
        this.journalID = fileJournalRecord.id();
        this.processingTime = fileJournalRecord.processingTime();
        this.downloadedUrl = null;
        this.downloadedAt = null;
    }
}
