package pl.edu.pjatk.s15666.tau.database;

import java.util.List;

public class DatabaseAccess {

    private List<Object> db;

    public DatabaseAccess(List<Object> db) {
        this.db = db;
    }

    public Object create(Object o) {
        return null;
    }

    public List<Object> readAll() {
        return null;
    }

    public Object read(int id) throws NotFoundException {
        return null;
    }

    public void update(Object o) throws NotFoundException {

    }

    public void delete(Object o) throws NotFoundException {

    }

}
