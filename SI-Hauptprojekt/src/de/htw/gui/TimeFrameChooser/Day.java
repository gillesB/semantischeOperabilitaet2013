package de.htw.gui.TimeFrameChooser;

/**
 * Created with IntelliJ IDEA.
 * User: Tobias
 * Date: 15.08.13
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public enum Day {
    INVALID("INVALID", -1),
    MONDAY("Montag", 1), TUESDAY("Dienstag", 2), WEDNESDAY("Mittwoch", 3), THURSDAY("Donnerstag", 4),
    FRIDAY("Freitag", 5), SATURDAY("Samstag", 6), SUNDAY("Sonntag", 7);
    private String name;
    private int    id;

    Day(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Day getByID(int id) {
        switch (id) {
            case 1:
                return MONDAY;
            case 2:
                return TUESDAY;
            case 3:
                return WEDNESDAY;
            case 4:
                return THURSDAY;
            case 5:
                return FRIDAY;
            case 6:
                return SATURDAY;
            case 7:
                return SUNDAY;
        }
        return INVALID;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
