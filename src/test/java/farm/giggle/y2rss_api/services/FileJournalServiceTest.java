package farm.giggle.y2rss_api.services;

import farm.giggle.y2rss_api.model.FileJournal;
import farm.giggle.y2rss_api.repositories.FileJournalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileJournalServiceTest {

    @Mock
    FileJournalRepository fileJournalRepository;
    @InjectMocks
    FileJournalService fileJournalService;

    private AutoCloseable closeable;

    @BeforeEach
    void serUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void after() throws Exception {
        closeable.close();
    }

    @Test
    void testTake() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.plusSeconds(1);

        FileJournal fileJournalNow = new FileJournal(
                UUID.randomUUID(), "http://ffaway.online/video", now, UUID.randomUUID(), 1L, null
        );
        FileJournal fileJournalNext = new FileJournal(
                UUID.randomUUID(), "http://ffaway.online/video2", next, UUID.randomUUID(), 2L, null
        );

        when(fileJournalRepository.getTopRecord()).thenReturn(fileJournalNext);

        FileJournal result = fileJournalService.take();
        assertNotNull(result);
        assertEquals(fileJournalNext.id(), result.id());

        verify(fileJournalRepository, times(1)).updateProcessingTimeById(any(UUID.class), any(LocalDateTime.class));
    }

    @Test
    void testDelete() {
        fileJournalService.delete(UUID.randomUUID(), LocalDateTime.now());
        verify(fileJournalRepository, times(1)).deleteRecord(any(UUID.class), any(LocalDateTime.class));
    }

}