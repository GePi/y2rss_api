package farm.giggle.y2rss_api.dto;

import farm.giggle.y2rss_api.model.FileJournal;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ExchangeFileFormatDTO {
    @NotNull
    private String videoUrl;
    @NotNull
    private UUID journalID;

    private LocalDateTime processingTime;

    private String downloadedUrl;
    private LocalDateTime downloadedAt;

    public ExchangeFileFormatDTO(FileJournal fileJournalRecord) {
        this.videoUrl = fileJournalRecord.url();
        this.journalID = fileJournalRecord.id();
        this.processingTime = fileJournalRecord.processingTime();
        this.downloadedUrl = null;
        this.downloadedAt = null;
    }
}
