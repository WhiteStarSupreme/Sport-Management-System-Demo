package si.feri.opj.ponsoda.Classes;

/**
 * Custom exception for sport discipline mismatch between match and venue.
 */
public class SportDisciplineException extends Exception {
    public SportDisciplineException(String message) {
        super(message);
    }
}
