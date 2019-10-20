package pl.edu.pjatk.s15666.tau.database;

public class DbObjectProperties {

    private DbObjectProperties() {}

    private static boolean trackCreationDate = true;
    private static boolean trackModificationDate = true;
    private static boolean trackAccessDate = true;

    public static boolean isTrackCreationDate() {
        return trackCreationDate;
    }

    public static boolean isTrackModificationDate() {
        return trackModificationDate;
    }

    public static boolean isTrackAccessDate() {
        return trackAccessDate;
    }

    public static void setTrackCreationDate(boolean trackCreationDate) {
        DbObjectProperties.trackCreationDate = trackCreationDate;
    }

    public static void setTrackModificationDate(boolean trackModificationDate) {
        DbObjectProperties.trackModificationDate = trackModificationDate;
    }

    public static void setTrackAccessDate(boolean trackAccessDate) {
        DbObjectProperties.trackAccessDate = trackAccessDate;
    }
}
