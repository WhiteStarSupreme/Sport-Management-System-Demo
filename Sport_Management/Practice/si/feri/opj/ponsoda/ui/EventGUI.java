package si.feri.opj.ponsoda.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import si.feri.opj.ponsoda.Classes.Athlete;
import si.feri.opj.ponsoda.Classes.Event;
import si.feri.opj.ponsoda.Classes.Match;
import si.feri.opj.ponsoda.Classes.Schedule;
import si.feri.opj.ponsoda.Classes.Training;

public class EventGUI {
    private Event[] events; 
    private Athlete[] athletes; 
    private int eventCount; 

    private JTextField eventNameField;
    private JSpinner eventScheduleSpinner;
    private JCheckBox eventCancelledCheckBox;
    private JComboBox<String> eventTypeComboBox;
    private JTextField athleteNumberToAddField;
    private JButton addAthleteToMatchButton;
    private JButton createEventButton;
    private JButton editEventButton;
    private JButton deleteEventButton;
    private DefaultTableModel eventTableModel;
    private JTable eventTable;
    private JScrollPane eventScrollPane;

    public EventGUI(Event[] events, Athlete[] athletes) {
        this.events = events;
        this.athletes = athletes;
        this.eventCount = 0;

        createComponents();
        setupLayout();
        setupActions();
    }

    private void createComponents() {
        // Create components
        eventNameField = new JTextField(15);
        eventScheduleSpinner = createDateTimeSpinner();
        eventCancelledCheckBox = new JCheckBox("Cancelled");
        eventTypeComboBox = new JComboBox<>(new String[]{"Match", "Training"});
        athleteNumberToAddField = new JTextField(5);
        addAthleteToMatchButton = new JButton("Add Athlete to Match");
        createEventButton = new JButton("Create Event");
        editEventButton = new JButton("Edit Event");
        deleteEventButton = new JButton("Delete Event");

        // Initialize the event table model
        eventTableModel = new DefaultTableModel(new String[]{
            "Event Name", "Schedule", "Cancelled", "Type", "Athletes"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };
        eventTable = new JTable(eventTableModel);
        eventScrollPane = new JScrollPane(eventTable);
    }

    private void setupLayout() {
        // Event input panel
        JPanel eventInputPanel = new JPanel(new GridLayout(5, 2));
        eventInputPanel.add(new JLabel("Event Name:"));
        eventInputPanel.add(eventNameField);
        eventInputPanel.add(new JLabel("Schedule:"));
        eventInputPanel.add(eventScheduleSpinner);
        eventInputPanel.add(new JLabel("Type:"));
        eventInputPanel.add(eventTypeComboBox);
        eventInputPanel.add(eventCancelledCheckBox);
        eventInputPanel.add(new JLabel("Athlete Number to Add:"));
        eventInputPanel.add(athleteNumberToAddField);
        eventInputPanel.add(addAthleteToMatchButton);

        // Event button panel
        JPanel eventButtonPanel = new JPanel(new FlowLayout());
        eventButtonPanel.add(createEventButton);
        eventButtonPanel.add(editEventButton);
        eventButtonPanel.add(deleteEventButton);

        // Event list panel
        JPanel eventListPanel = new JPanel(new BorderLayout());
        eventListPanel.add(new JLabel("Events:"), BorderLayout.NORTH);
        eventListPanel.add(eventScrollPane, BorderLayout.CENTER);

        // Main layout
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(eventInputPanel);
        mainPanel.add(eventListPanel);
        mainPanel.add(eventButtonPanel);
    }

    private void setupActions() {
        createEventButton.addActionListener(this::createEvent);
        editEventButton.addActionListener(this::editEvent);
        deleteEventButton.addActionListener(this::deleteEvent);
        addAthleteToMatchButton.addActionListener(this::addAthleteToMatch);
        eventTable.getSelectionModel().addListSelectionListener(this::editEventFromTable);
    }

    private void createEvent(ActionEvent e) {
        // Retrieve event data
        String eventName = eventNameField.getText();
        LocalDateTime schedule = ((Date) eventScheduleSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        boolean isCancelled = eventCancelledCheckBox.isSelected();
        String eventType = (String) eventTypeComboBox.getSelectedItem();

        // Create event object
        Event event;
        if (eventType.equals("Match")) {
            event = new Match(eventName, new Schedule(schedule), isCancelled);
        } else {
            event = new Training(eventName, new Schedule(schedule), null, isCancelled);
        }

        // Add event and update table
        addEvent(event);
        updateEventList();
    }

    private void editEvent(ActionEvent e) {
        // Retrieve selected event index
        int index = eventTable.getSelectedRow();
        if (index >= 0) {
            // Retrieve selected event
            Event event = events[index];

            // Retrieve updated event data
            String eventName = eventNameField.getText();
            LocalDateTime schedule = ((Date) eventScheduleSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            boolean isCancelled = eventCancelledCheckBox.isSelected();
            String eventType = (String) eventTypeComboBox.getSelectedItem();

            // Update event based on type
            if (eventType.equals("Match") && !(event instanceof Match)) {
                events[index] = new Match(eventName, new Schedule(schedule), isCancelled);
            } else if (eventType.equals("Training") && !(event instanceof Training)) {
                events[index] = new Training(eventName, new Schedule(schedule), null, isCancelled);
            } else {
                event.setName(eventName);
                event.setSchedule(new Schedule(schedule));
                event.setCancelled(isCancelled);
            }

            // Update table
            updateEventList();
        }
    }

    private void deleteEvent(ActionEvent e) {
        // Retrieve selected event index
        int index = eventTable.getSelectedRow();
        if (index >= 0) {
            // Remove selected event and update table
            removeEvent(index);
            updateEventList();
        }
    }

    private void addAthleteToMatch(ActionEvent e) {
        // Retrieve selected match index
        int matchIndex = eventTable.getSelectedRow();
        if (matchIndex >= 0) {
            // Retrieve selected event
            Event event = events[matchIndex];
            if (event instanceof Match) {
                // Add athlete to match
                Match match = (Match) event;
                int athleteNumber = Integer.parseInt(athleteNumberToAddField.getText());
                Athlete athleteToAdd = findAthleteByNumber(athleteNumber);
                if (athleteToAdd != null) {
                    match.addAthleteToMatch(athleteToAdd);
                    updateEventList();
                    JOptionPane.showMessageDialog(null, "Athlete added to the match.");
                } else {
                    JOptionPane.showMessageDialog(null, "Athlete not found.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The selected event is not a match.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No match selected.");
        }
    }

    private void editEventFromTable(ListSelectionEvent e) {
        // Retrieve selected event index
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Retrieve event details from table
            String eventName = (String) eventTableModel.getValueAt(selectedRow, 0);
            LocalDateTime scheduleDateTime = LocalDateTime.parse((String) eventTableModel.getValueAt(selectedRow, 1));
            boolean isCancelled = eventTableModel.getValueAt(selectedRow, 2).equals("Yes");
            String eventType = (String) eventTableModel.getValueAt(selectedRow, 3);
            // Update input fields with event details
            eventNameField.setText(eventName);
            eventScheduleSpinner.setValue(java.sql.Timestamp.valueOf(scheduleDateTime));
            eventCancelledCheckBox.setSelected(isCancelled);
            eventTypeComboBox.setSelectedItem(eventType);
        }
    }

    private void updateEventList() {
        // Clear existing table rows
        eventTableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // Add events to the table
        for (int i = 0; i < eventCount; i++) {
            Event event = events[i];
            String schedule = event.getSchedule().getDateTime().format(formatter);
            String athletesList = "";

            // Get list of athletes for matches
            if (event instanceof Match) {
                Match match = (Match) event;
                athletesList = Arrays.stream(match.getAthletesList())
                    .filter(a -> a != null)
                    .map(a -> a.getName() + " " + a.getSurname())
                    .reduce((a1, a2) -> a1 + ", " + a2)
                    .orElse("");
            }

            // Add event to table
            eventTableModel.addRow(new Object[]{
                event.getName(),
                schedule,
                event.isCancelled() ? "Yes" : "No",
                event instanceof Match ? "Match" : "Training",
                athletesList
            });
        }
    }

    private void addEvent(Event event) {
        // Resize array if necessary
        if (eventCount == events.length) {
            events = Arrays.copyOf(events, events.length * 2);
        }
        // Add event and increase count
        events[eventCount++] = event;
    }

    private void removeEvent(int index) {
        // Remove event from array and shift other elements
        System.arraycopy(events, index + 1, events, index, eventCount - index - 1);
        events[--eventCount] = null;
    }

    private Athlete findAthleteByNumber(int athleteNumber) {
        // Find athlete by athlete number
        for (Athlete athlete : athletes) {
            if (athlete != null && athlete.getAthleteNumber() == athleteNumber) {
                return athlete;
            }
        }
        return null;
    }

    private JSpinner createDateTimeSpinner() {
        // Create date and time spinner
        SpinnerDateModel dateTimeModel = new SpinnerDateModel();
        JSpinner dateTimeSpinner = new JSpinner(dateTimeModel);
        JSpinner.DateEditor dateTimeEditor = new JSpinner.DateEditor(dateTimeSpinner, "yyyy-MM-dd HH:mm");
        dateTimeSpinner.setEditor(dateTimeEditor);
        return dateTimeSpinner;
    }

    public JPanel getEventListPanel() {
        // Create panel for event list
        JPanel eventListPanel = new JPanel(new BorderLayout());
        eventListPanel.add(new JLabel("Events:"), BorderLayout.NORTH);
        eventListPanel.add(eventScrollPane, BorderLayout.CENTER);
        return eventListPanel;
    }

    public JPanel getEventInputPanel() {
        // Create panel for event input
        JPanel eventInputPanel = new JPanel(new GridLayout(5, 2));
        eventInputPanel.add(new JLabel("Event Name:"));
        eventInputPanel.add(eventNameField);
        eventInputPanel.add(new JLabel("Schedule:"));
        eventInputPanel.add(eventScheduleSpinner);
        eventInputPanel.add(new JLabel("Type:"));
        eventInputPanel.add(eventTypeComboBox);
        eventInputPanel.add(eventCancelledCheckBox);
        eventInputPanel.add(new JLabel("Athlete Number to Add:"));
        eventInputPanel.add(athleteNumberToAddField);
        eventInputPanel.add(addAthleteToMatchButton);
        return eventInputPanel;
    }

    public JPanel getEventButtonPanel() {
        // Create panel for event buttons
        JPanel eventButtonPanel = new JPanel(new FlowLayout());
        eventButtonPanel.add(createEventButton);
        eventButtonPanel.add(editEventButton);
        eventButtonPanel.add(deleteEventButton);
        return eventButtonPanel;
    }
}
