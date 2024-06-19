package si.feri.opj.ponsoda.Classes;

/**
 * Stadium class, subclass of Venue
 */
public class Stadium extends Venue {
    private int capacity;

    /**
     * Constructor for Stadium class
     *
     * @param name         The name of the stadium
     * @param phoneNumber  The phone number of the stadium
     * @param numOfMatches The number of matches
     * @param discipline 
     * @param capacity     The capacity of the stadium
     */
    public Stadium(String name, String phoneNumber, int numOfMatches, Discipline discipline, int capacity) {
        super(name, phoneNumber, numOfMatches);
        this.capacity = capacity;
    }

    /**
     * Get the capacity of the stadium
     *
     * @return The capacity of the stadium
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set the capacity of the stadium
     *
     * @param capacity The capacity of the stadium
     */
    public int setCapacity(int capacity) {
        return capacity;
    }

    /**
     * String representation
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Stadium{" +
                super.toString() +
                ", capacity=" + capacity +
                '}';
    }
}
