package de.htw.logging;

/**
 * Beinhaltet die einzelnen Logger
 *
 * @author Tobias Kalmes
 */
public enum LoggerNames {
    MAIN_LOGGER("mainLogger"),
    DB_LOGGER("databaseLogger"),
    DEBUG_TIME_RANGE_CHOOSER("debugTimeRangeChooser");
    private String name;

    LoggerNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
