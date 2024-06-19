package si.feri.opj.ponsoda.Classes;

import java.time.LocalDateTime;

/**
 * The Schedule class represents a schedule with a specific date and time.
 */
public class Schedule {
    private LocalDateTime dateTime; // The date and time of the schedule

    /**
     * Constructs a new Schedule object Default constructor
     */
    public Schedule() {
        // Default constructor
    }

    /**
     * Constructs a new Schedule object with the specified date and time.
     *
     * @param dateTime The date and time of the schedule.
     */
    public Schedule(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Returns the date and time of the schedule.
     *
     * @return The date and time of the schedule.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the date and time of the schedule.
     *
     * @param dateTime The date and time of the schedule.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

     /**
     * String representation 
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Schedule{" +
                "dateTime=" + dateTime +
                '}';
    }
}
