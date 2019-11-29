package com.main.controller;

import com.main.model.entity.CustomerEntity;
import com.main.model.entity.FactEntity;
import com.main.model.entity.OrderEntity;
import com.main.database.jpa.JpaConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NewOrderController implements Initializable {
    @FXML
    public TextField textFieldAmount;
    @FXML
    public DatePicker datePicker;

    @FXML
    public Button buttonRefresh;
    @FXML
    public Button buttonNewOrder;

    @FXML
    public TableView<CustomerEntity> tableView;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableColumnId;
    @FXML
    public TableColumn<CustomerEntity, Integer> tableColumnAddress;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnName;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnPhone;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnEmail;
    @FXML
    public TableColumn<CustomerEntity, String> tableColumnDescription;

    private FactEntity factEntity;

    public NewOrderController(FactEntity factEntity) {
        this.factEntity = factEntity;
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNewOrder.setOnAction(this::OnPress_Button_NewOrder);
        buttonRefresh.setOnAction(this::OnPress_Button_RefreshCustomerTable);
        displayInformationToTableView();
    }

    @FXML
    private void OnPress_Button_NewOrder(ActionEvent event) {
        OrderEntity orderEntity = getOrderEntity();
        if (orderEntity != null) try {
            JpaConnector.getOrder().save(getOrderEntity());
            MainController.showAlert(Alert.AlertType.CONFIRMATION, "New Order", "Order was successfully added.");
            Stage stage = (Stage) buttonNewOrder.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            MainController.showAlert(Alert.AlertType.ERROR, "New Order", ex.getMessage());
        }
    }

    private OrderEntity getOrderEntity() {
        if (checkValidation()) {
            CustomerEntity customerEntity = getCustomerEntity();
            return new OrderEntity(factEntity.getIdProduct(),
                    customerEntity.getIdCustomer(),
                    factEntity.getPrice(),
                    Integer.parseInt(textFieldAmount.getText()),
                    Date.valueOf(datePicker.getValue()));
        }
        return null;
    }

    private boolean checkValidation() {
        if (getCustomerEntity() == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Please select a customer from the table below.");
            return false;
        }
        if (MainController.hasOnlyNumbers(textFieldAmount.getText())) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Amount must has only numbers.");
            return false;
        }
        if (Integer.parseInt(textFieldAmount.getText()) > factEntity.getAmount()) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Inventory has only: " + factEntity.getAmount());
            return false;
        }
        if (datePicker.getValue() == null) {
            MainController.showAlert(Alert.AlertType.ERROR, "Validation", "Choose data of the order.");
            return false;
        }

        return true;
    }

    private CustomerEntity getCustomerEntity() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void OnPress_Button_RefreshCustomerTable(ActionEvent event) {
        displayInformationToTableView();
    }

    private void displayInformationToTableView() {
        ObservableList<CustomerEntity> data = FXCollections.observableArrayList(JpaConnector.getCustomer().getAll());
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        tableColumnAddress.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(data);
    }


}
