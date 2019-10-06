package pl.edu.pjatk.s15666.tau.database;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseAccessTest {

    List<Object> db;
    DatabaseAccess dbAccess;

    @Before
    public void setup() {
        db = new ArrayList<Object>();
        dbAccess = new DatabaseAccess(db);
    }

    @org.junit.Test
    public void create() {

    }

    @org.junit.Test
    public void readAll() {
    }

    @org.junit.Test
    public void read() {
    }

    @org.junit.Test
    public void update() {
    }

    @org.junit.Test
    public void delete() {
    }
}