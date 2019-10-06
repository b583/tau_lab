package pl.edu.pjatk.s15666.tau.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseAccess {

    private List<DbObject> db;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public DatabaseAccess(List<DbObject> db) {
        this.db = db;
    }

    public DbObject create(DbObject o) throws NotEnoughSpaceException {
        if(db.size() >= 10) {
            throw new NotEnoughSpaceException();
        }
        o.setId(atomicInteger.getAndIncrement());
        db.add(o);
        return o;
    }

    public List<DbObject> readAll() {
        return new ArrayList<>(db);
    }

    private Optional<DbObject> getObjectByID(int id) {
        return db.stream()
                .filter(o -> o.getId().equals(id))
                .findAny();
    }

    public DbObject read(int id) throws NotFoundException {
        var optional = getObjectByID(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundException();
    }

    public void update(DbObject o) throws NotFoundException {
        var optional = getObjectByID(o.getId());
        if(optional.isPresent()) {
            db.remove(optional.get());
            db.add(o);
        } else {
            throw new NotFoundException();
        }
    }

    public void delete(DbObject o) throws NotFoundException {
        var optional = getObjectByID(o.getId());
        if(optional.isPresent()) {
            db.remove(optional.get());
        } else {
            throw new NotFoundException();
        }
    }

}
