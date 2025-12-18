package com.udara.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.udara.pos.dao.DatabaseAccessCode;
import com.udara.pos.dto.CustomerDto;
import com.udara.pos.view.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerFormController {
    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;
    public JFXButton btnSaveCustomer;
    public TextField txtSearch;
    public TableView<CustomerTm> tbl;
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colSalary;
    public TableColumn colOperate;

    private String searchText = "";

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadAllCustomers(searchText);
        tbl.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            if (newValue!=null){
                setData(newValue);
            }
        });
    }

    private void setData(CustomerTm newValue) {
        txtEmail.setEditable(false);
        btnSaveCustomer.setText("Update Customer");
        txtEmail.setText(newValue.getEmail());
        txtName.setText(newValue.getName());
        txtContact.setText(newValue.getContact());
//        txtSalary.setText(""+newValue.getSalary());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }

    private void loadAllCustomers(String searchText) throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> observableList = FXCollections.observableArrayList();
        int counter = 1;
        for (CustomerDto dto : DatabaseAccessCode.searchCustomers(searchText)) {
            Button btn = new Button("Delete");
            CustomerTm tm = new CustomerTm(counter, dto.getEmail(), dto.getName(), dto.getContact(), dto.getSalary(), btn);
            observableList.add(tm);
            counter++;
        }
        tbl.setItems(observableList);
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void btnManageLoyaltyCardsOnAction(ActionEvent actionEvent) {
    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        clearFields();
        txtEmail.setEditable(true);
        btnSaveCustomer.setText("Save Customer");
    }

    public void btnSaveCustomerOnAction(ActionEvent actionEvent) {
        try {
            if (btnSaveCustomer.getText().equals("Save Customer")){
                if (DatabaseAccessCode.createCustomer(txtEmail.getText(), txtName.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText()))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved!").show();
                    clearFields();
                    loadAllCustomers(searchText);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            } else {
                if (DatabaseAccessCode.updateCustomer(txtEmail.getText(), txtName.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText()))) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
                    clearFields();
                    loadAllCustomers(searchText);
                    //-----------
                    txtEmail.setEditable(true);
                    btnSaveCustomer.setText("Save Customer");
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtEmail.clear();
        txtName.clear();
        txtContact.clear();
        txtSalary.clear();
    }

    private void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
        stage.centerOnScreen();
    }
}
