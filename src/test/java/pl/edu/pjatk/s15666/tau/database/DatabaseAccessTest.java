package pl.edu.pjatk.s15666.tau.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.pjatk.s15666.tau.domain.Sensor;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessTest {

    List<Object> db;
    DatabaseAccess dbAccess;

    @Before
    public void setup() {
        db = new ArrayList<Object>();
        dbAccess = new DatabaseAccess(db);
    }

    private Sensor create(String location, boolean isOutdoor) {
        var testSensor = new Sensor(location, isOutdoor);
        return (Sensor) dbAccess.create(testSensor);
    }

    @Test
    public void createdObjectShouldHaveID() {
        Assert.assertEquals(0, create("", false).getId());
    }

    @Test
    public void createdObjectsShouldHaveUniqueIDs() {
        var sensor1 = create("", false);
        var sensor2 = create("", true);
        Assert.assertNotEquals(sensor1.getId(), sensor2.getId());
    }

    @Test
    public void createdObjectShouldBeInDatabase() {
        var sensor = create("", false);
        Assert.assertTrue(db
                .stream()
                .anyMatch(o -> o == sensor));
    }

    @Test
    public void createdObjectFieldsShouldBePersisted() {
        var sensor = create("Kitchen", true);
        Assert.assertTrue(sensor.getLocation().equals("Kitchen") && sensor.isOutdoor());
    }

    @org.junit.Test
    public void readAll() {
        var sensor1 = new Sensor("Kitchen", false);
        sensor1.setId(0);
        var sensor2 = new Sensor("Patio", true);
        sensor2.setId(1);
        db.add(sensor1);
        db.add(sensor2);
        var sensors = dbAccess.readAll();
        Assert.assertTrue(sensors.contains(sensor1) && sensors.contains(sensor2));
    }

    @org.junit.Test
    public void read() {
        var sensor = new Sensor("Kitchen", false);
        sensor.setId(0);
        db.add(sensor);
        sensor = (Sensor) dbAccess.read(0);
        Assert.assertTrue(sensor.getLocation().equals("Kitchen") && !sensor.isOutdoor());
    }

    @org.junit.Test
    public void update() {
        var sensor = new Sensor("Garage", true);
        sensor.setId(0);
        db.add(sensor);
        sensor = new Sensor("Garage", false);
        sensor.setId(0);
        dbAccess.update(sensor);
        Assert.assertFalse(((Sensor) db.get(0)).isOutdoor());
    }

    @org.junit.Test
    public void delete() {
        var sensor = new Sensor("Garage", true);
        sensor.setId(0);
        db.add(sensor);
        dbAccess.delete(sensor);
        Assert.assertTrue(db.isEmpty());
    }
}