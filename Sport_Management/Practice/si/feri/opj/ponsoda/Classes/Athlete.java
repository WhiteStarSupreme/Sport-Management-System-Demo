package si.feri.opj.ponsoda.Classes;

/**
 * Athlete class
 */
public class Athlete implements Competition {
    private String name;
    private String surname;
    private String dateOfBirth;
    private int athleteNumber;
    private Discipline discipline;
    private String[] listOfPersonalMatches = new String[5]; // Adding listOfPersonalMatches

    /**
     * Constructs a new Athlete Default constructor
     */
    public Athlete() {
        // Default constructor
    }

    /**
     * Constructs a new Athlete with the specifications
     *
     * @param name          The name of the athlete
     * @param surname       The surname of the athlete
     * @param dateOfBirth   The date of birth of the athlete
     * @param athleteNumber The athlete number
     */
    public Athlete(String name, String surname, String dateOfBirth, int athleteNumber, Discipline discipline) {
        this(name, surname);
        this.dateOfBirth = dateOfBirth;
        this.athleteNumber = athleteNumber;
        this.discipline = discipline;
    }

    /**
     * Constructs new Athlete
     *
     * @param name    The name of the athlete
     * @param surname The surname of the athlete
     */
    public Athlete(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * Returns name of athlete
     *
     * @return Name of athlete
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of athlete
     *
     * @param name Name of athlete
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the surname of athlete
     *
     * @return Surname of athlete
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of athlete.
     *
     * @param surname Surname of athlete.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the date of birth
     *
     * @return Date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the athlete
     *
     * @param dateOfBirth The date of birth of the athlete
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the athlete number
     *
     * @return The athlete number
     */
    public int getAthleteNumber() {
        return athleteNumber;
    }

    /**
     * Sets the athlete number
     *
     * @param athleteNumber The athlete number
     */
    public void setAthleteNumber(int athleteNumber) {
        this.athleteNumber = athleteNumber;
    }

    /**
     * Returns the discipline
     *
     * @return The discipline
     */
    public Discipline getDiscipline() {
        return discipline;
    }

    /**
     * Set the discipline
     *
     * @param discipline The discipline
     */
    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    /**
     * Returns the list of personal matches
     *
     * @return The list of personal matches
     */
    public String[] getListOfPersonalMatches() {
        return listOfPersonalMatches;
    }

    /**
     * Sets the list of personal matches
     *
     * @param listOfPersonalMatches The list of personal matches
     */
    public void setListOfPersonalMatches(String[] listOfPersonalMatches) {
        this.listOfPersonalMatches = listOfPersonalMatches;
    }

    @Override
    public void checkValidity(Venue venue) {
        if (venue instanceof Venue) {
            Match[] matches = ((Venue) venue).getListOfMatches();
            for (Match match : matches) {
                if (match != null && match.athleteExists(getSurname())) {
                    for (int i = 0; i < listOfPersonalMatches.length; i++) {
                        if (listOfPersonalMatches[i] == null) {
                            listOfPersonalMatches[i] = match.getName();
                            break;
                        }
                    }
                }
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
        return "Athlete{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", athleteNumber=" + athleteNumber +
                '}';
    }
}
