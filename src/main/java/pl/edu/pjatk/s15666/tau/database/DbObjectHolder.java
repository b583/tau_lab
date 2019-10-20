package pl.edu.pjatk.s15666.tau.database;

import java.time.LocalDateTime;
import java.util.Optional;

public class DbObjectHolder {

    private LocalDateTime creationDate;
    private Optional<LocalDateTime> modificationDate;
    private Optional<LocalDateTime> accessDate;

    private DbObject dbObject;

    private DbLocalDateTimeProvider timeProvider;

    DbObjectHolder(DbLocalDateTimeProvider provider, DbObject o) {
        this.dbObject = o;
        this.timeProvider = provider;
        updateCreationDate();
    }

    DbObject getDbObject() {
        updateAccessDate();
        return dbObject;
    }

    private void updateCreationDate() {
        this.creationDate = timeProvider.get();
    }

    private void updateModificationDate() {
        this.modificationDate = Optional.of(timeProvider.get());
    }

    private void updateAccessDate() {
        this.accessDate = Optional.of(timeProvider.get());
    }

    void setDbObject(DbObject o) {
        dbObject = o;
        updateModificationDate();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Optional<LocalDateTime> getModificationDate() {
        return modificationDate;
    }

    public Optional<LocalDateTime> getAccessDate() {
        return accessDate;
    }

}
