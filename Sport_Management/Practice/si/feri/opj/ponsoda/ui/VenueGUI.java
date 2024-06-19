package si.feri.opj.ponsoda.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import si.feri.opj.ponsoda.Classes.*;

public class VenueGUI {
    private Venue[] venues;
    private Match[] matches;
    private int venueCount;

    // GUI components
    private JTextField venueNameField;
    private JTextField phoneNumberField;
    private JTextField capacityField;
    private JComboBox<Discipline> disciplineComboBox;
    private JTextField additionalCapacityField;
    private JTextField matchNameField;
    private JComboBox<String> venueTypeComboBox;
    private JButton addMatchToVenueButton;
    private JButton createVenueButton;
    private JButton editVenueButton;
    private JButton deleteVenueButton;
    private DefaultTableModel venueTableModel;
    private JTable venueTable;
    private JScrollPane venueScrollPane;

    public VenueGUI(Venue[] venues, Match[] matches) {
        this.venues = venues;
        this.matches = matches;
        this.venueCount = 0;

        createComponents();
        setupActions();
        updateVenueList();
    }

    private void createComponents() {
        // Initialize GUI components
        venueNameField = new JTextField(15);
        phoneNumberField = new JTextField(15);
        capacityField = new JTextField(5);
        disciplineComboBox = new JComboBox<>(Discipline.values());
        additionalCapacityField = new JTextField(5);
        matchNameField = new JTextField(15);
        venueTypeComboBox = new JComboBox<>(new String[]{"Hall", "Stadium"});

        addMatchToVenueButton = new JButton("Add Match to Venue");
        createVenueButton = new JButton("Create Venue");
        editVenueButton = new JButton("Edit Venue");
        deleteVenueButton = new JButton("Delete Venue");

        // Initialize the table model and table
        venueTableModel = new DefaultTableModel(new String[]{
            "Venue Name", "Phone Number", "Capacity", "Discipline", "Type", "Matches"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        venueTable = new JTable(venueTableModel);
        venueScrollPane = new JScrollPane(venueTable);
    }

    private void setupActions() {
        // Set up action listeners for buttons and list selection
        createVenueButton.addActionListener(this::createVenue);
        editVenueButton.addActionListener(this::editVenue);
        deleteVenueButton.addActionListener(this::deleteVenue);
        addMatchToVenueButton.addActionListener(this::addMatchToVenue);

        venueTable.getSelectionModel().addListSelectionListener(this::editVenueFromTable);
    }

    private void createVenue(ActionEvent e) {
        // Retrieve input data for venue creation
        String venueName = venueNameField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        int capacity = Integer.parseInt(capacityField.getText().trim());
        Discipline discipline = (Discipline) disciplineComboBox.getSelectedItem();
        String venueType = (String) venueTypeComboBox.getSelectedItem();

        Venue venue;
        if (venueType.equals("Hall")) {
            int additionalHalls = Integer.parseInt(additionalCapacityField.getText().trim());
            venue = new Hall(venueName, phoneNumber, capacity, discipline, additionalHalls);
        } else {
            venue = new Stadium(venueName, phoneNumber, capacity, discipline, capacity);
        }

        addVenue(venue);
        updateVenueList();
    }

    private void editVenue(ActionEvent e) {
        // Retrieve selected venue index
        int index = venueTable.getSelectedRow();
        if (index >= 0) {
            Venue venue = venues[index];

            // Update venue details from the input fields
            venue.setName(venueNameField.getText().trim());
            venue.setPhoneNumber(phoneNumberField.getText().trim());
            Discipline discipline = (Discipline) disciplineComboBox.getSelectedItem();
            venue.setVenueDiscipline(discipline);

            if (venue instanceof Hall) {
                int additionalHalls = Integer.parseInt(additionalCapacityField.getText().trim());
                ((Hall) venue).setNumOfAdditionalHalls(additionalHalls);
                ((Hall) venue).setCapacity(Integer.parseInt(capacityField.getText().trim()));
            } else if (venue instanceof Stadium) {
                ((Stadium) venue).setCapacity(Integer.parseInt(capacityField.getText().trim()));
            }

            updateVenueList();
        }
    }

    private void deleteVenue(ActionEvent e) {
        // Retrieve selected venue index
        int index = venueTable.getSelectedRow();
        if (index >= 0) {
            removeVenue(index);
            updateVenueList();
        }
    }

    private void addMatchToVenue(ActionEvent e) {
        // Retrieve match name from input field
        String matchName = matchNameField.getText().trim();
        
        // Find the match in the matches array
        Match matchToAdd = findMatchByName(matchName);

        // Retrieve selected venue index
        int selectedVenueIndex = venueTable.getSelectedRow();

        // Validate selection and inputs
        if (selectedVenueIndex < 0) {
            JOptionPane.showMessageDialog(null, "No venue selected.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (matchToAdd == null) {
            JOptionPane.showMessageDialog(null, "Match not found.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Venue selectedVenue = venues[selectedVenueIndex];

        try {
            // Add the match to the selected venue
            selectedVenue.addMatch(matchToAdd);
            
            // Update the GUI
            updateVenueList();
            
            // Show success message
            JOptionPane.showMessageDialog(null, "Match added to venue successfully.");
            
            // Print the list of matches for the venue in the console
            printVenueMatches(selectedVenue);
        } catch (AddingMatchException | SportDisciplineException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printVenueMatches(Venue selectedVenue) {
        // Print the list of matches associated with the venue in the console
        Match[] venueMatches = selectedVenue.getListOfMatches();
        System.out.println("Matches at " + selectedVenue.getName() + ":");
        if (venueMatches != null && venueMatches.length > 0) {
            for (Match match : venueMatches) {
                if (match != null) {
                    System.out.println("- " + match.getName());
                }
            }
        } else {
            System.out.println("No matches found at the venue.");
        }
    }

    private void editVenueFromTable(ListSelectionEvent e) {
        // Retrieve selected venue index from the table
        int selectedRow = venueTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Retrieve data from the table and populate the input fields
            venueNameField.setText((String) venueTableModel.getValueAt(selectedRow, 0));
            phoneNumberField.setText((String) venueTableModel.getValueAt(selectedRow, 1));
            capacityField.setText(String.valueOf(venueTableModel.getValueAt(selectedRow, 2)));
            Discipline discipline = Discipline.valueOf((String) venueTableModel.getValueAt(selectedRow, 3));
            disciplineComboBox.setSelectedItem(discipline);
            venueTypeComboBox.setSelectedItem((String) venueTableModel.getValueAt(selectedRow, 4));

            Venue selectedVenue = venues[selectedRow];
            if (selectedVenue instanceof Hall) {
                int additionalHalls = ((Hall) selectedVenue).getNumOfAdditionalHalls();
                additionalCapacityField.setText(String.valueOf(additionalHalls));
            } else {
                additionalCapacityField.setText(""); // Clear additional halls for Stadiums
            }
        }
    }

    private void updateVenueList() {
        // Clear existing rows in the table model
        venueTableModel.setRowCount(0);
        
        // Iterate over each venue and update the table
        for (int i = 0; i < venueCount; i++) {
            Venue venue = venues[i];
            Discipline discipline = venue.getVenueDiscipline();
            String disciplineName = discipline != null ? discipline.name() : "";
            String venueType = venue instanceof Hall ? "Hall" : "Stadium";

            // Build the list of matches for the venue
            String matchesList = Arrays.stream(venue.getListOfMatches())
                .filter(match -> match != null)
                .map(Match::getName)
                .reduce((match1, match2) -> match1 + ", " + match2)
                .orElse("");

            // Add the venue's details to the table
            venueTableModel.addRow(new Object[]{
                venue.getName(),
                venue.getPhoneNumber(),
                venue.getCapacity(),
                disciplineName,
                venueType,
                matchesList
            });
        }
    }

    private void addVenue(Venue venue) {
        // Expand the venues array if necessary
        if (venueCount == venues.length) {
            venues = Arrays.copyOf(venues, venues.length * 2);
        }
        
        // Add the new venue and increase the count
        venues[venueCount++] = venue;
    }

    private void removeVenue(int index) {
        // Remove the selected venue from the array and shift remaining elements
        System.arraycopy(venues, index + 1, venues, index, venueCount - index - 1);
        venues[--venueCount] = null;
    }

    private Match findMatchByName(String matchName) {
        // Search for the match in the matches array using case-insensitive comparison
        for (Match match : matches) {
            if (match != null && match.getName().equalsIgnoreCase(matchName)) {
                return match;
            }
        }
        return null;
    }

    public JTextField getMatchNameField() {
        return matchNameField;
    }

    public JButton getAddMatchToVenueButton() {
        return addMatchToVenueButton;
    }

    public JPanel getVenueInputPanel() {
        JPanel venueInputPanel = new JPanel(new GridLayout(9, 2));
        venueInputPanel.add(new JLabel("Venue Name:"));
        venueInputPanel.add(venueNameField);
        venueInputPanel.add(new JLabel("Phone Number:"));
        venueInputPanel.add(phoneNumberField);
        venueInputPanel.add(new JLabel("Capacity:"));
        venueInputPanel.add(capacityField);
        venueInputPanel.add(new JLabel("Additional Halls (Hall only):"));
        venueInputPanel.add(additionalCapacityField);
        venueInputPanel.add(new JLabel("Discipline:"));
        venueInputPanel.add(disciplineComboBox);
        venueInputPanel.add(new JLabel("Venue Type:"));
        venueInputPanel.add(venueTypeComboBox);
        venueInputPanel.add(new JLabel("Match to Add:"));
        venueInputPanel.add(matchNameField);
        venueInputPanel.add(addMatchToVenueButton);
        return venueInputPanel;
    }

    public JPanel getVenueButtonPanel() {
        JPanel venueButtonPanel = new JPanel(new FlowLayout());
        venueButtonPanel.add(createVenueButton);
        venueButtonPanel.add(editVenueButton);
        venueButtonPanel.add(deleteVenueButton);
        return venueButtonPanel;
    }

    public JPanel getVenueListPanel() {
        JPanel venueListPanel = new JPanel(new BorderLayout());
        venueListPanel.add(new JLabel("Venues:"), BorderLayout.NORTH);
        venueListPanel.add(venueScrollPane, BorderLayout.CENTER);
        return venueListPanel;
    }
}
