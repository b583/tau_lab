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

    @Mock
    private DbLocalDateTimeProvider timeProvider;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setupMock() {
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
    public void dboHasACreationDate() {
        assertEquals(createHolder().getCreationDate(), timestamp);
    }

    @Test
    public void dboTracksModificationDate() {
        var dbo = createHolder();
        var currentTimestamp = newMockTimestamp();
        dbo.setDbObject(dbo.getDbObject());
        assertEquals(currentTimestamp, dbo.getModificationDate().get());
    }

}