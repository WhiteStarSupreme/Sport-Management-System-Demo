package si.feri.opj.ponsoda.Classes;

/**
 * Hall class, subclass of Venue
 */
public class Hall extends Venue {
    private int numOfAdditionalHalls;

    /**
     * Constructor for Hall class
     *
     * @param name                 The name of the hall
     * @param phoneNumber          The phone number of the hall
     * @param nbMatch              The number of matches
     * @param discipline 
     * @param numOfAdditionalHalls The number of additional halls
     */
    public Hall(String name, String phoneNumber, int nbMatch, Discipline discipline, int numOfAdditionalHalls) {
        super(name, phoneNumber, nbMatch);
        if (numOfAdditionalHalls < 0) {
            throw new IllegalArgumentException("Number of additional halls must be non-negative.");
        }
        this.numOfAdditionalHalls = numOfAdditionalHalls;
    }

    /**
     * Get the capacity of the hall including additional halls
     *
     * @return The capacity of the hall
     */
    @Override
    public int getCapacity() {
        return numOfAdditionalHalls;
    }

    /**
     * Get the number of additional halls
     *
     * @return The number of additional halls
     */
    public int getNumOfAdditionalHalls() {
        return numOfAdditionalHalls;
    }

    /**
     * Set the number of additional halls
     *
     * @param numOfAdditionalHalls The number of additional halls
     */
    public void setNumOfAdditionalHalls(int numOfAdditionalHalls) {
        if (numOfAdditionalHalls < 0) {
            throw new IllegalArgumentException("Number of additional halls must be non-negative.");
        }
        this.numOfAdditionalHalls = numOfAdditionalHalls;
    }

    /**
     * String representation
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Hall{" +
                super.toString() +
                ", numOfAdditionalHalls=" + numOfAdditionalHalls +
                '}';
    }

    @Override
    public int setCapacity(int capacity) {
        return capacity + numOfAdditionalHalls;
    }
}
