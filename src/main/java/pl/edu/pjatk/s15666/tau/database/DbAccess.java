package pl.edu.pjatk.s15666.tau.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class DbAccess {

    private List<DbObjectHolder> db;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private DbLocalDateTimeProvider timeProvider;

    DbAccess(List<DbObjectHolder> db, DbLocalDateTimeProvider timeProvider) {
        this.db = db;
        this.timeProvider = timeProvider;
    }

    private DbObjectHolder createObjectHolder(DbObject o) {
        return new DbObjectHolder(timeProvider, o);
    }

    DbObjectHolder create(DbObject o) throws NotEnoughSpaceException {
        if(db.size() >= 10) {
            throw new NotEnoughSpaceException();
        }
        o.setId(atomicInteger.getAndIncrement());
        var dbo = createObjectHolder(o);
        db.add(dbo);
        return dbo;
    }

    List<DbObjectHolder> readAll() {
        return new ArrayList<>(db);
    }

    private Optional<DbObjectHolder> getObjectByID(int id) {
        return db.stream()
                .filter(o -> o.getDbObject().getId().equals(id))
                .findAny();
    }

    DbObjectHolder read(int id) throws NotFoundException {
        var optional = getObjectByID(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundException();
    }

    void update(DbObject o) throws NotFoundException {
        var optional = getObjectByID(o.getId());
        if(optional.isPresent()) {
            optional.get().setDbObject(o);
        } else {
            throw new NotFoundException();
        }
    }

    void delete(DbObject o) throws NotFoundException {
        var optional = getObjectByID(o.getId());
        if(optional.isPresent()) {
            db.remove(optional.get());
        } else {
            throw new NotFoundException();
        }
    }

}
