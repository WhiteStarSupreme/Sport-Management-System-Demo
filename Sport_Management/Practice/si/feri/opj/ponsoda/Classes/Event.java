package si.feri.opj.ponsoda.Classes;

public class Event {
    protected String name;
    protected Schedule schedule;
    protected boolean cancelled;

    public Event() {
        // Default constructor
    }

    public Event(String name, Schedule schedule) {
        this.name = name;
        this.schedule = schedule;
    }

    public Event(String name, Schedule schedule, boolean cancelled) {
        this(name, schedule);
        this.cancelled = cancelled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


}

