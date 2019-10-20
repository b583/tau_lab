package pl.edu.pjatk.s15666.tau.database;

import java.time.LocalDateTime;

public class DbLocalDateTimeProvider {

    public LocalDateTime get() {
        return LocalDateTime.now();
    }

}
