package farm.giggle.y2rss_api.controllers;

import farm.giggle.y2rss_api.dto.ExchangeFileFormatDTO;
import farm.giggle.y2rss_api.model.FileJournal;
import farm.giggle.y2rss_api.services.DownloaderService;
import farm.giggle.y2rss_api.services.FileJournalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DownloaderController {

    private final DownloaderService downloaderService;

    public DownloaderController(DownloaderService downloaderService) {
        this.downloaderService = downloaderService;
    }

    @GetMapping("/getFileToDownload")
    public ResponseEntity<ExchangeFileFormatDTO> getFileToDownload() {

        FileJournal fileJournalRecord = downloaderService.take();
        if (fileJournalRecord == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        ExchangeFileFormatDTO fileDTO = new ExchangeFileFormatDTO(fileJournalRecord);
        return ResponseEntity.ok(fileDTO);
    }

    @PutMapping("/putDownloadedFile")
    public ResponseEntity<Void> putDownloadedFile(@RequestBody ExchangeFileFormatDTO fileDTO) {
        if (downloaderService.completeDownload(fileDTO)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
