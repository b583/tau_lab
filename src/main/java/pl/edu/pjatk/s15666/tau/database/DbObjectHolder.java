package pl.edu.pjatk.s15666.tau.database;

import java.time.LocalDateTime;
import java.util.Optional;

public class DbObjectHolder {

    private Optional<LocalDateTime> creationDate = Optional.empty();
    private Optional<LocalDateTime> modificationDate = Optional.empty();
    private Optional<LocalDateTime> accessDate = Optional.empty();

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
        if(DbObjectProperties.isTrackCreationDate()) {
            this.creationDate = Optional.of(timeProvider.get());
        }
    }

    private void updateModificationDate() {
        if(DbObjectProperties.isTrackModificationDate()) {
            this.modificationDate = Optional.of(timeProvider.get());
        }
    }

    private void updateAccessDate() {
        if(DbObjectProperties.isTrackAccessDate()) {
            this.accessDate = Optional.of(timeProvider.get());
        }
    }

    void setDbObject(DbObject o) {
        dbObject = o;
        updateModificationDate();
    }

    public Optional<LocalDateTime> getCreationDate() {
        return creationDate;
    }

    public Optional<LocalDateTime> getModificationDate() {
        return modificationDate;
    }

    public Optional<LocalDateTime> getAccessDate() {
        return accessDate;
    }

}
