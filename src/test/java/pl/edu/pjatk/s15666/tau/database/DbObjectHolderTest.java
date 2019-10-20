package pl.edu.pjatk.s15666.tau.database;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.edu.pjatk.s15666.tau.domain.Sensor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class DbObjectHolderTest {

    private LocalDateTime timestamp;

    @Mock
    private DbLocalDateTimeProvider timeProvider;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        newMockTimestamp();
    }

    private LocalDateTime newMockTimestamp() {
        var timestamp = LocalDateTime.now();
        when(timeProvider.get()).thenReturn(timestamp);
        this.timestamp = timestamp;
        return timestamp;
    }

    private DbObjectHolder createHolder() {
        var sensor = new Sensor("", false);
        return new DbObjectHolder(timeProvider, sensor);
    }

    @Test
    public void timeProviderIsUsedForTimestampRetrieval() {
        createHolder();
        verify(timeProvider).get();
    }

    @Test
    public void dboHasACreationDate() {
        DbObjectProperties.setTrackCreationDate(true);
        var dbo = createHolder();
        assertEquals(dbo.getCreationDate().get(), timestamp);
    }

    @Test
    public void dboTracksModificationDate() {
        DbObjectProperties.setTrackModificationDate(true);
        var dbo = createHolder();
        var currentTimestamp = newMockTimestamp();
        dbo.setDbObject(dbo.getDbObject());
        assertEquals(currentTimestamp, dbo.getModificationDate().get());
    }

    @Test
    public void dboTracksAccessDate() {
        DbObjectProperties.setTrackAccessDate(true);
        var dbo = createHolder();
        var currentTimestamp = newMockTimestamp();
        dbo.getDbObject();
        assertEquals(currentTimestamp, dbo.getAccessDate().get());
    }

    @Test
    public void dboDoesNotTrackCreationDateWhenPropertyIsFalse() {
        DbObjectProperties.setTrackCreationDate(false);
        var dbo = createHolder();
        assertEquals(Optional.empty(), dbo.getCreationDate());
    }

    @Test
    public void dboDoesNotTrackModificationDateWhenPropertyIsFalse() {
        DbObjectProperties.setTrackModificationDate(false);
        var dbo = createHolder();
        dbo.setDbObject(dbo.getDbObject());
        assertEquals(Optional.empty(), dbo.getModificationDate());
    }

    @Test
    public void dboDoesNotTrackAccessDateWhenPropertyIsFalse() {
        DbObjectProperties.setTrackAccessDate(false);
        var dbo = createHolder();
        dbo.getDbObject();
        assertEquals(Optional.empty(), dbo.getAccessDate());
    }

}