package pl.edu.pjatk.s15666.tau.database;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseAccess {

    private List<DbObject> db;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public DatabaseAccess(List<DbObject> db) {
        this.db = db;
    }

    public DbObject create(DbObject o) {
        return null;
    }

    public List<DbObject> readAll() {
        return null;
    }

    public DbObject read(int id) throws NotFoundException {
        return null;
    }

    public void update(DbObject o) throws NotFoundException {

    }

    public void delete(DbObject o) throws NotFoundException {

    }

}
