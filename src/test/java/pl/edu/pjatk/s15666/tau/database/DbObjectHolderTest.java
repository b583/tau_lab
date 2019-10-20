package pl.edu.pjatk.s15666.tau.database;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.edu.pjatk.s15666.tau.domain.Sensor;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DbObjectHolderTest {

    private LocalDateTime timestamp;
    private DbObjectHolder dbo;

    @Mock
    private DbLocalDateTimeProvider timeProvider;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        newMockTimestamp();
        this.dbo = createHolder();
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
    public void dboHasACreationDate() {
        assertEquals(dbo.getCreationDate(), timestamp);
    }

    @Test
    public void dboTracksModificationDate() {
        var currentTimestamp = newMockTimestamp();
        dbo.setDbObject(dbo.getDbObject());
        assertEquals(currentTimestamp, dbo.getModificationDate().get());
    }

    @Test
    public void dboTracksAccessDate() {
        var currentTimestamp = newMockTimestamp();
        dbo.getDbObject();
        assertEquals(currentTimestamp, dbo.getAccessDate().get());
    }

}