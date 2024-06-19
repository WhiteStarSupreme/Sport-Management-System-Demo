package si.feri.opj.ponsoda.ui;

import javax.swing.*;
import java.awt.*;

import si.feri.opj.ponsoda.Classes.*;
import si.feri.opj.ponsoda.Classes.Event;

public class SystemGUI extends JFrame {
    private Athlete[] athletes;
    private Event[] events; 
    private Venue[] venues; 
    private Match[] matches; 

    public SystemGUI() {
        // Initialize athletes, events, and venues arrays
        athletes = new Athlete[10]; 
        events = new Event[10]; 
        venues = new Venue[10]; 
        matches = new Match[10];

        // Initialize GUI components
        setTitle("Athlete, Event, and Venue Management");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create instances of AthleteGUI, EventGUI, and VenueGUI
        AthleteGUI athleteGUI = new AthleteGUI(athletes);
        EventGUI eventGUI = new EventGUI(events, athletes);
        VenueGUI venueGUI = new VenueGUI(venues, matches);

        // Create input and button panels
        JPanel inputPanel = createInputPanel(athleteGUI, eventGUI, venueGUI);
        JPanel buttonPanel = createButtonPanel(athleteGUI, eventGUI, venueGUI);

        // Create list panel with list panels
        JPanel listPanel = createListPanel(athleteGUI, eventGUI, venueGUI);

        // Add panels to the main frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(listPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel(AthleteGUI athleteGUI, EventGUI eventGUI, VenueGUI venueGUI) {
        // Create a panel with a grid layout for input panels
        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Panels"));

        // Add input panels
        inputPanel.add(athleteGUI.getAthleteInputPanel());
        inputPanel.add(eventGUI.getEventInputPanel());
        inputPanel.add(venueGUI.getVenueInputPanel());

        return inputPanel;
    }

    private JPanel createButtonPanel(AthleteGUI athleteGUI, EventGUI eventGUI, VenueGUI venueGUI) {
        // Create a panel with a grid layout for button panels
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Button Panels"));

        // Add button panels
        buttonPanel.add(athleteGUI.getAthleteButtonPanel());
        buttonPanel.add(eventGUI.getEventButtonPanel());
        buttonPanel.add(venueGUI.getVenueButtonPanel());

        return buttonPanel;
    }

    private JPanel createListPanel(AthleteGUI athleteGUI, EventGUI eventGUI, VenueGUI venueGUI) {
        // Create a panel with a grid layout for list panels
        JPanel listPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        listPanel.setBorder(BorderFactory.createTitledBorder("List Panels"));

        // Add list panels
        listPanel.add(athleteGUI.getAthleteListPanel());
        listPanel.add(eventGUI.getEventListPanel());
        listPanel.add(venueGUI.getVenueListPanel());

        return listPanel;
    }

    public static void main(String[] args) {
        SystemGUI gui = new SystemGUI();
        gui.setVisible(true);
    }
}
