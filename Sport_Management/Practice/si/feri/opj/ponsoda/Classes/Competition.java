package si.feri.opj.ponsoda.Classes;

/**
 * Competition interface
 */
public interface Competition {
    /**
     * Checks the validity of the competition venue
     * @param venue The venue to check validity against
     */
    public void checkValidity(Venue venue);

}
