package farm.giggle.y2rss_api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileJournal(UUID id, String url, LocalDateTime publicationTime, UUID userUUID, Long fileID, LocalDateTime processingTime) {
};

