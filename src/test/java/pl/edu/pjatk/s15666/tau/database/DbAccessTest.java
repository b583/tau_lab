package pl.edu.pjatk.s15666.tau.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.pjatk.s15666.tau.domain.Sensor;

import java.util.ArrayList;
import java.util.List;

public class DbAccessTest {

    private List<DbObjectHolder> db;
    private DbAccess dbAccess;

    @Before
    public void setup() {
        db = new ArrayList<>();
        dbAccess = new DbAccess(db, new DbLocalDateTimeProvider());
    }

    private Sensor create(String location, boolean isOutdoor) throws NotEnoughSpaceException {
        var testSensor = new Sensor(location, isOutdoor);
        return (Sensor) dbAccess.create(testSensor).getDbObject();
    }

    private DbObjectHolder add(Sensor s) {
        var dbo = new DbObjectHolder(new DbLocalDateTimeProvider(), s);
        db.add(dbo);
        return dbo;
    }

    @Test
    public void createdObjectShouldHaveID() throws NotEnoughSpaceException {
        Assert.assertEquals(Integer.valueOf(0), create("", false).getId());
    }

    @Test
    public void createdObjectsShouldHaveUniqueIDs() throws NotEnoughSpaceException {
        var sensor1 = create("", false);
        var sensor2 = create("", true);
        Assert.assertNotEquals(sensor1.getId(), sensor2.getId());
    }

    @Test
    public void createdObjectShouldBeInDatabase() throws NotEnoughSpaceException {
        var sensor = create("", false);
        Assert.assertTrue(db
                .stream()
                .anyMatch(o -> o.getDbObject() == sensor));
    }

    @Test
    public void createdObjectFieldsShouldBePersisted() throws NotEnoughSpaceException {
        var sensor = create("Kitchen", true);
        Assert.assertTrue(sensor.getLocation().equals("Kitchen") && sensor.isOutdoor());
    }

    @Test(expected = NotEnoughSpaceException.class)
    public void goingOverSpaceLimitWillCauseException() throws NotEnoughSpaceException {
        for(int i = 0; i < 11; i++) {
            dbAccess.create(new Sensor("", false));
        }
    }

    @Test
    public void readAll() {
        var sensor1 = new Sensor("Kitchen", false);
        sensor1.setId(0);
        var sensor2 = new Sensor("Patio", true);
        sensor2.setId(1);
        var sensor1_dbo = this.add(sensor1);
        var sensor2_dbo = this.add(sensor2);
        var sensors = dbAccess.readAll();
        Assert.assertTrue(sensors.contains(sensor1_dbo) && sensors.contains(sensor2_dbo));
    }

    @Test
    public void read() throws NotFoundException {
        var sensor = new Sensor("Kitchen", false);
        sensor.setId(0);
        this.add(sensor);
        sensor = (Sensor) dbAccess.read(0).getDbObject();
        Assert.assertTrue(sensor.getLocation().equals("Kitchen") && !sensor.isOutdoor());
    }

    @Test(expected = NotFoundException.class)
    public void readThrowsNotFoundException() throws NotFoundException {
        dbAccess.read(0);
    }

    @Test
    public void update() throws NotFoundException {
        var sensor = new Sensor("Garage", true);
        sensor.setId(0);
        this.add(sensor);
        sensor = new Sensor("Garage", false);
        sensor.setId(0);
        dbAccess.update(sensor);
        Assert.assertFalse(((Sensor) db.get(0).getDbObject()).isOutdoor());
    }

    @Test(expected = NotFoundException.class)
    public void updateThrowsNotFoundException() throws NotFoundException {
        var sensor = new Sensor("", false);
        sensor.setId(0);
        dbAccess.update(sensor);
    }

    @Test
    public void delete() throws NotFoundException {
        var sensor = new Sensor("Garage", true);
        sensor.setId(0);
        this.add(sensor);
        dbAccess.delete(sensor);
        Assert.assertTrue(db.isEmpty());
    }

    @Test(expected = NotFoundException.class)
    public void deleteThrowsNotFoundException() throws NotFoundException {
        var sensor = new Sensor("", false);
        sensor.setId(0);
        dbAccess.delete(sensor);
    }
}