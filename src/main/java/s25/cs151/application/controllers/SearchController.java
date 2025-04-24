package s25.cs151.application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import s25.cs151.application.DAO.AppointmentDAO;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.services.PageNavigator;

import java.sql.SQLException;
import java.util.List;

public class SearchController  extends BaseScheduleController {
    public TextField studentNameField;
    @FXML private TableColumn<Appointment, Void> deleteColumn;


    @Override
    protected void loadAppointments() {
        appointments.setAll(AppointmentDAO.getAppointmentList());
    }

    // loads delete button in the table
    @Override
    protected void secondaryLoading() {
        addDeleteButton();
    }

    // adds the delete button
    private void addDeleteButton() {
        deleteColumn.setCellFactory(
                column -> new TableCell<>() {
                    private final Button deleteButton = new Button("delete");

                    {
                        deleteButton.setStyle("-fx-text-fill: red; -fx-font-size: 9px;");
                        deleteButton.setOnAction(event -> {
                            Appointment appointment = getTableView().getItems().get(getIndex());
                            try {
                                boolean success = AppointmentDAO.deleteAppointment(appointment);
                                if (success) {
                                    appointments.remove(appointment);// remove from table if successful
                                    handleSearch();
                                    System.out.println("Appointment deleted successfully.");
                                } else {
                                    System.out.println("Failed to delete appointment.");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace(); // or log it
                                System.out.println("Database error occurred while deleting appointment.");
                            }
                        });
                    }
                    // this is a override method in tableCell<> which needs to be called
                    // while updating a table cell
                    // if cell empty = clear the cell
                    // if cell has value = display the delete button

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); // nothing in this row
                        } else {
                            setGraphic(deleteButton); // show button
                        }
                    }
                }
        );
    }



    @SuppressWarnings("unchecked")
    @Override
    protected void applyDefaultSorting() {
        scheduleDateColumn.setSortType(TableColumn.SortType.DESCENDING);
        timeSlotColumn.setSortType(TableColumn.SortType.DESCENDING);
        scheduleTable.getSortOrder().setAll(scheduleDateColumn, timeSlotColumn);
        scheduleTable.sort();
    }

    @FXML
    private void handleSearch() {
        String studentName = studentNameField.getText().toLowerCase().trim();
        List<Appointment> filteredList = AppointmentDAO
                .getAppointmentList().stream()
                .filter(appt -> appt.getStudentFullName().toLowerCase().trim().contains(studentName))
                .toList();
        appointments.setAll(filteredList);
        applyDefaultSorting();
    }

    public void handleBack() {
        PageNavigator.navigateTo("Home");
    }
}
