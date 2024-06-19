package si.feri.opj.ponsoda.Classes;

/**
 * Training class
 */
public class Training extends Event {
    private String trainerName;
    private Discipline discipline;

    public Training() {
        super();
    }

    public Training(String name, Schedule schedule, String trainerName, boolean cancelled) {
        super(name, schedule, cancelled);
        this.trainerName = trainerName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }


      /**
     * String representation 
     * @return String representation
     */
    @Override
    public String toString() {
        return "Training{" +
                "name='" + name + '\'' +
                ", schedule=" + schedule +
                ", trainerName='" + trainerName + '\'' +
                ", cancelled=" + cancelled +
                '}';
    }
}
