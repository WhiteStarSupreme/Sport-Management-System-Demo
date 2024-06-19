package si.feri.opj.ponsoda.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import si.feri.opj.ponsoda.Classes.Athlete;
import si.feri.opj.ponsoda.Classes.Discipline;

public class AthleteGUI {
    private Athlete[] athletes; 
    private int athleteCount; 

    private JTextField nameField;
    private JTextField surnameField;
    private JSpinner dobSpinner;
    private JTextField athleteNumberField;
    private JComboBox<Discipline> disciplineComboBox;
    private JButton createAthleteButton;
    private JButton editAthleteButton;
    private JButton deleteAthleteButton;
    private DefaultTableModel athleteTableModel;
    private JTable athleteTable;
    private JScrollPane athleteScrollPane;

    // Constructor accepting an array of athletes
    public AthleteGUI(Athlete[] athletes) {
        this.athletes = athletes;
        this.athleteCount = 0;

        nameField = new JTextField(15);
        surnameField = new JTextField(15);
        dobSpinner = createDateSpinner();
        athleteNumberField = new JTextField(5);
        disciplineComboBox = new JComboBox<>(Discipline.values());
        createAthleteButton = new JButton("Create Athlete");
        editAthleteButton = new JButton("Edit Athlete");
        deleteAthleteButton = new JButton("Delete Athlete");
        athleteTableModel = new DefaultTableModel(new String[]{
            "Name", "Surname", "DOB", "Athlete Number", "Discipline"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };
         athleteTable = new JTable(athleteTableModel);
        athleteScrollPane = new JScrollPane(athleteTable);

        setupAthleteGUI();
    }

    private void setupAthleteGUI() {
        // Athlete input panel
        JPanel athleteInputPanel = new JPanel(new GridLayout(5, 2));
        athleteInputPanel.add(new JLabel("Name:"));
        athleteInputPanel.add(nameField);
        athleteInputPanel.add(new JLabel("Surname:"));
        athleteInputPanel.add(surnameField);
        athleteInputPanel.add(new JLabel("Date of Birth:"));
        athleteInputPanel.add(dobSpinner);
        athleteInputPanel.add(new JLabel("Athlete Number:"));
        athleteInputPanel.add(athleteNumberField);
        athleteInputPanel.add(new JLabel("Discipline:"));
        athleteInputPanel.add(disciplineComboBox);

        // Athlete button panel
        JPanel athleteButtonPanel = new JPanel(new FlowLayout());
        athleteButtonPanel.add(createAthleteButton);
        athleteButtonPanel.add(editAthleteButton);
        athleteButtonPanel.add(deleteAthleteButton);

        // Athlete list panel
        JPanel athleteListPanel = new JPanel(new BorderLayout());
        athleteListPanel.add(new JLabel("Athletes:"), BorderLayout.NORTH);
        athleteListPanel.add(athleteScrollPane, BorderLayout.CENTER);

        // Add action listeners for athlete buttons
        createAthleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAthlete();
            }
        });

        editAthleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAthlete();
            }
        });

        deleteAthleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAthlete();
            }
        });

        athleteTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    editAthleteFromTable();
                }
            }
        });
    }

    private void createAthlete() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        LocalDate dateOfBirth = ((Date) dobSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int athleteNumber = Integer.parseInt(athleteNumberField.getText());
        Discipline discipline = (Discipline) disciplineComboBox.getSelectedItem();

        Athlete athlete = new Athlete(name, surname, dateOfBirth.toString(), athleteNumber, discipline);
        addAthlete(athlete);
        updateAthleteList();
    }

    private void editAthlete() {
        int index = athleteTable.getSelectedRow();
        if (index >= 0) {
            Athlete athlete = athletes[index];

            String name = nameField.getText();
            String surname = surnameField.getText();
            LocalDate dateOfBirth = ((Date) dobSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int athleteNumber = Integer.parseInt(athleteNumberField.getText());
            Discipline discipline = (Discipline) disciplineComboBox.getSelectedItem();

            athlete.setName(name);
            athlete.setSurname(surname);
            athlete.setDateOfBirth(dateOfBirth.toString());
            athlete.setAthleteNumber(athleteNumber);
            athlete.setDiscipline(discipline);

            updateAthleteList();
        }
    }

    private void deleteAthlete() {
        int index = athleteTable.getSelectedRow();
        if (index >= 0) {
            removeAthlete(index);
            updateAthleteList();
        }
    }

    private void editAthleteFromTable() {
        int selectedRow = athleteTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) athleteTableModel.getValueAt(selectedRow, 0);
            String surname = (String) athleteTableModel.getValueAt(selectedRow, 1);
            LocalDate dateOfBirth = LocalDate.parse((String) athleteTableModel.getValueAt(selectedRow, 2));
            int athleteNumber = (int) athleteTableModel.getValueAt(selectedRow, 3);
            Discipline discipline = Discipline.valueOf((String) athleteTableModel.getValueAt(selectedRow, 4));

            nameField.setText(name);
            surnameField.setText(surname);
            dobSpinner.setValue(java.sql.Date.valueOf(dateOfBirth));
            athleteNumberField.setText(String.valueOf(athleteNumber));
            disciplineComboBox.setSelectedItem(discipline);
        }
    }

    public JPanel getAthleteListPanel() {
        JPanel athleteListPanel = new JPanel(new BorderLayout());
        athleteListPanel.add(new JLabel("Athletes:"), BorderLayout.NORTH);
        athleteListPanel.add(athleteScrollPane, BorderLayout.CENTER);
        return athleteListPanel;
    }

    public JPanel getAthleteInputPanel() {
        JPanel athleteInputPanel = new JPanel(new GridLayout(5, 2));
        athleteInputPanel.add(new JLabel("Name:"));
        athleteInputPanel.add(nameField);
        athleteInputPanel.add(new JLabel("Surname:"));
        athleteInputPanel.add(surnameField);
        athleteInputPanel.add(new JLabel("Date of Birth:"));
        athleteInputPanel.add(dobSpinner);
        athleteInputPanel.add(new JLabel("Athlete Number:"));
        athleteInputPanel.add(athleteNumberField);
        athleteInputPanel.add(new JLabel("Discipline:"));
        athleteInputPanel.add(disciplineComboBox);
        return athleteInputPanel;
    }

    public JPanel getAthleteButtonPanel() {
        JPanel athleteButtonPanel = new JPanel(new FlowLayout());
        athleteButtonPanel.add(createAthleteButton);
        athleteButtonPanel.add(editAthleteButton);
        athleteButtonPanel.add(deleteAthleteButton);
        return athleteButtonPanel;
    }

    private void updateAthleteList() {
        athleteTableModel.setRowCount(0);
        for (int i = 0; i < athleteCount; i++) {
            Athlete athlete = athletes[i];
            athleteTableModel.addRow(new Object[]{
                athlete.getName(),
                athlete.getSurname(),
                athlete.getDateOfBirth(),
                athlete.getAthleteNumber(),
                athlete.getDiscipline().name()
            });
        }
    }

    private void addAthlete(Athlete athlete) {
        if (athleteCount == athletes.length) {
            athletes = java.util.Arrays.copyOf(athletes, athletes.length * 2);
        }
        athletes[athleteCount++] = athlete;
    }

    private void removeAthlete(int index) {
        System.arraycopy(athletes, index + 1, athletes, index, athleteCount - index - 1);
        athletes[--athleteCount] = null;
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        return dateSpinner;
    }
}
