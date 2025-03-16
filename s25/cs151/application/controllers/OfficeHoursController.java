<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.text.Font?>

<VBox xmlns="http://javafx.com/javafx"
      xmlns:fx="http://javafx.com/fxml"
      fx:controller="s25.cs151.controller.DefineSemesterOfficeHoursController"
      spacing="15"
      prefWidth="500"
      prefHeight="400"
      style="-fx-background-color: white;">

    <padding>
        <Insets top="20" right="20" bottom="20" left="20"/>
    </padding>

    <Label text="Define Semester's Office Hours"
           style="-fx-font-weight: bold;">
        <font>
            <Font size="18"/>
        </font>
    </Label>
    
    <Separator />
    
    <GridPane hgap="10" vgap="10">
        <padding>
            <Insets top="10" right="10" bottom="10" left="10"/>
        </padding>
        
        <!-- Semester Selection -->
        <Label text="Semester:" 
               GridPane.rowIndex="0" 
               GridPane.columnIndex="0"
               style="-fx-font-weight: bold;"/>
               
        <ComboBox fx:id="semesterComboBox" 
                  promptText="Select Semester"
                  prefWidth="200"
                  GridPane.rowIndex="0" 
                  GridPane.columnIndex="1"/>
        
        <!-- Year Input -->
        <Label text="Year:" 
               GridPane.rowIndex="1" 
               GridPane.columnIndex="0"
               style="-fx-font-weight: bold;"/>
               
        <TextField fx:id="yearTextField" 
                   promptText="Enter 4-digit year"
                   prefWidth="200"
                   GridPane.rowIndex="1" 
                   GridPane.columnIndex="1"/>
        
        <!-- Days Selection -->
        <Label text="Days:" 
               GridPane.rowIndex="2" 
               GridPane.columnIndex="0"
               style="-fx-font-weight: bold;"/>
               
        <VBox spacing="5" 
              GridPane.rowIndex="2" 
              GridPane.columnIndex="1">
            <CheckBox fx:id="mondayCheckBox" text="Monday (M)"/>
            <CheckBox fx:id="tuesdayCheckBox" text="Tuesday (T)"/>
            <CheckBox fx:id="wednesdayCheckBox" text="Wednesday (W)"/>
            <CheckBox fx:id="thursdayCheckBox" text="Thursday (Th)"/>
            <CheckBox fx:id="fridayCheckBox" text="Friday (F)"/>
        </VBox>
    </GridPane>
    
    <!-- Error Messages -->
    <Label fx:id="errorMessageLabel" 
           textFill="RED"
           wrapText="true"/>
    
    <!-- Buttons -->
    <HBox spacing="10" alignment="CENTER">
        <Button fx:id="submitButton" 
                text="Submit" 
                onAction="#handleSubmit"
                prefWidth="100"/>
                
        <Button fx:id="cancelButton" 
                text="Cancel" 
                onAction="#handleCancel"
                prefWidth="100"/>
    </HBox>
</VBox>
