package si.feri.opj.ponsoda.Classes;

import java.util.Arrays;

/**
 * Venue class
 */
public abstract class Venue {
    private String name;
    private String phoneNumber;
    private Match[] listOfMatches;
    private Discipline venueDiscipline; 

    /**
     * New Venue Default constructor
     */
    public Venue() {
        // Default constructor
    }

    /**
     * New Venue with the specified name and phone number
     *
     * @param name         The name of the venue
     * @param phoneNumber  The phone number of the venue
     * @param numOfMatches The number of matches to initialize the array
     */
    public Venue(String name, String phoneNumber, int numOfMatches) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.listOfMatches = new Match[numOfMatches];
    }

    /**
     * New Venue with the specified name, phone number, number of matches, and venue discipline
     *
     * @param name           The name of the venue
     * @param phoneNumber    The phone number of the venue
     * @param numOfMatches   The number of matches to initialize the array
     * @param venueDiscipline The venue's discipline
     */
    public Venue(String name, String phoneNumber, int numOfMatches, Discipline venueDiscipline) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.listOfMatches = new Match[numOfMatches];
        this.venueDiscipline = venueDiscipline;
    }

    /**
     * Add a match to the list of matches if space is available and the match starts before 8 pm
     *
     * @param match   The match to add
     * @param athlete The athlete participating in the match
     * @throws AddingMatchException     if the match starts after 8 pm
     * @throws SportDisciplineException if the match contains an athlete from a different sport discipline than the venue supports
     */
    public void addMatch(Match match) throws AddingMatchException, SportDisciplineException {
        // Check if the match starts after 8 pm
        if (match.getSchedule().getDateTime().getHour() >= 20) {
            throw new AddingMatchException("Cannot add match starting after 8 pm.");
        }

    
        // Add the match to the venue
        for (int i = 0; i < listOfMatches.length; i++) {
            if (listOfMatches[i] == null) {
                listOfMatches[i] = match;
                return;
            }
        }
        System.out.println("No space available to add match.");
    }

    /**
     * Remove all matches from the venue
     */
    public void removeMatches() {
        Arrays.fill(listOfMatches, null);
    }

    /**
     * Get the percentage of venue occupancy with matches
     *
     * @return The percentage of venue occupancy
     */
    public double getOccupancy() {
        int occupiedSlots = 0;
        for (Match match : listOfMatches) {
            if (match != null) {
                occupiedSlots++;
            }
        }
        return (double) occupiedSlots / listOfMatches.length * 100;
    }

    /**
     * Getter for name
     *
     * @return The name of the venue
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     *
     * @param name The new name of the venue
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for phone number
     *
     * @return The phone number of the venue
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for phone number
     *
     * @param phoneNumber The new phone number of the venue
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for the list of matches
     *
     * @return The list of matches
     */
    public Match[] getListOfMatches() {
        return listOfMatches;
    }

    /**
     * Setter for the list of matches
     *
     * @param listOfMatches The new list of matches
     */
    public void setListOfMatches(Match[] listOfMatches) {
        this.listOfMatches = listOfMatches;
    }

    /**
     * Getter for venue discipline
     *
     * @return The discipline of the venue
     */
    public Discipline getVenueDiscipline() {
        return venueDiscipline;
    }

    /**
     * Setter for venue discipline
     *
     * @param venueDiscipline The new discipline of the venue
     */
    public void setVenueDiscipline(Discipline venueDiscipline) {
        this.venueDiscipline = venueDiscipline;
    }

    /**
     * Abstract method to get the capacity of the venue
     *
     * @return The capacity of the venue
     */
    public abstract int getCapacity();
    public abstract int setCapacity(int capacity);
    /**
     * String representation
     *
     * @return String representation of the venue
     */
    @Override
    public String toString() {
        return "Venue{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", listOfMatches=" + Arrays.toString(listOfMatches) +
                ", venueDiscipline=" + venueDiscipline +
                '}';
    }
}
