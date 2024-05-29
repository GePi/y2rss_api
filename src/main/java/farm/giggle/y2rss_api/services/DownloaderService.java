package farm.giggle.y2rss_api.services;

import farm.giggle.y2rss_api.dto.ExchangeFileFormatDTO;
import farm.giggle.y2rss_api.model.FileJournal;
import farm.giggle.y2rss_api.repositories.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DownloaderService {

    private final FileJournalService fileJournalService;
    private final FileRepository fileRepository;

    public DownloaderService(FileJournalService fileJournalService, FileRepository fileRepository) {
        this.fileJournalService = fileJournalService;
        this.fileRepository = fileRepository;
    }

    @Transactional
    @Nullable
    public FileJournal take() {
        return fileJournalService.take();
    }

    @Transactional
    public boolean completeDownload(ExchangeFileFormatDTO fileDTO) {
        try {
            FileJournal fileJournal = fileJournalService.getJournalRecordAndLock(fileDTO.getJournalID(), fileDTO.getProcessingTime());
            if (fileJournal != null) {
                fileRepository.updateDownloadFields(fileJournal.fileID(), fileDTO.getDownloadedUrl(), fileDTO.getDownloadedAt());
                fileJournalService.delete(fileJournal.id());
            }
        } catch (Exception e) {
            log.error("completeDownload", e);
            return false;
        }
        return true;
    }
}
