package si.feri.opj.ponsoda.Classes;

import java.time.LocalDate;
import java.time.Period;

/**
 * The Match class represents a match event.
 */
public class Match extends Event implements Competition {
    private Athlete[] athletesList = new Athlete[10];

    public Match() {
        super();
    }

    public Match(String name, Schedule schedule) {
        super(name, schedule);
    }

    public Match(String name, Schedule schedule, boolean cancelled) {
        super(name, schedule, cancelled);
    }

    /**
     * Checks if the given athlete can compete in the match based on their age
     * 
     * @param athlete Athlete to check
     * @return True if the athlete can compete; otherwise, false
     */
    public boolean canCompete(Athlete athlete) {
        String dob = athlete.getDateOfBirth();
        if (dob != null) {
            LocalDate dateOfBirth = LocalDate.parse(dob);
            LocalDate today = schedule.getDateTime().toLocalDate();
            Period ageDifference = Period.between(dateOfBirth, today);
            int age = ageDifference.getYears();
            return (age >= 14 && age <= 18);
        } else {
            System.out.println("Date of birth is null for athlete: " + athlete.getName());
            return false;
        }
    }

    // Getters and setters

    /**
     * Returns the name of the match
     * 
     * @return Name of the match
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the match
     * 
     * @param name Name of match
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the schedule of the match
     * 
     * @return Schedule of match
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Sets the schedule of the match
     * 
     * @param schedule Schedule of match
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Returns true or false
     * 
     * @return True if the match is cancelled; otherwise, false
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation status of the match.
     * 
     * @param cancelled The cancellation status of the match
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Adds an athlete to the match.
     * 
     * @param athlete The athlete to be added
     */
    public void addAthleteToMatch(Athlete athlete) {
        for (int i = 0; i < athletesList.length; i++) {
            if (athletesList[i] == null) {
                athletesList[i] = athlete;
                break;
            }
        }
    }

    /**
     * Removes an athlete from the match by object.
     * 
     * @param athlete The athlete object to be removed
     */
    public void removeAthleteFromMatch(Athlete athlete) {
        for (int i = 0; i < athletesList.length; i++) {
            if (athletesList[i] == athlete) {
                athletesList[i] = null;
                break;
            }
        }
    }

    /**
     * Removes an athlete from the match by athlete number.
     * 
     * @param athleteNumber Number of the athlete to be removed
     * @return True if athlete is successfully removed, false otherwise
     */
    public boolean removeAthleteFromMatch(int athleteNumber) {
        if (athleteNumber < 0) {
            throw new IllegalArgumentException("Invalid athlete number: " + athleteNumber);
        }
        for (int i = 0; i < athletesList.length; i++) {
            if (athletesList[i] != null && athletesList[i].getAthleteNumber() == athleteNumber) {
                athletesList[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the number of athletes in the match.
     * 
     * @return Number of athletes in the match
     */
    public int getNumberOfAthletesInMatch() {
        int count = 0;
        for (int i = 0; i < athletesList.length; i++) {
            if (athletesList[i] != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if an athlete exists in the match by last name.
     * 
     * @param lastname Last name of the athlete
     * @return True if athlete exists, false otherwise
     */
    public boolean athleteExists(String lastname) {
        for (int i = 0; i < athletesList.length; i++) {
            if (athletesList[i] != null && athletesList[i].getSurname().equals(lastname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Implementation of checkValidity method from Competition interface
     * Checks whether a match has been assigned to the selected venue
     * 
     * @param venue The venue to check against
     */
    @Override
    public void checkValidity(Venue venue) {
        if (venue instanceof Venue) {
            Match[] matches = ((Venue) venue).getListOfMatches();
            boolean matchFound = false;
            for (Match match : matches) {
                if (match != null && match.equals(this)) {
                    matchFound = true;
                    break;
                }
            }
            if (matchFound) {
                System.out.println("The match " + getName() + " can take place.");
            } else {
                System.out.println("The match " + getName() + " cannot take place.");
            }
        }
    }
    

    
    
    /**
     * String representation
     * 
     * @return String representation
     */
    @Override
    public String toString() {
        String result = "Match: " + name + "\n";

        for (Athlete athlete : athletesList) {
            if (athlete != null) {
                result += athlete + "\n";
            }
        }
        return result;
    }

    public Athlete[] getAthletesList() {
        return athletesList;
    }

}
