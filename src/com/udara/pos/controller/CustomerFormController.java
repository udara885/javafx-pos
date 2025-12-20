package com.udara.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.udara.pos.bo.BoFactory;
import com.udara.pos.bo.custom.CustomerBo;
import com.udara.pos.dto.CustomerDto;
import com.udara.pos.enums.BoType;
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
import java.util.Optional;

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

    CustomerBo bo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadAllCustomers(searchText);
        tbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setData(newValue);
            }
        });
        txtSearch.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchText = newValue;
            try {
                loadAllCustomers(searchText);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }));
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
        for (CustomerDto dto : searchText.length() > 0 ? bo.searchCustomers(searchText) : bo.findAllCustomers()) {
            Button btn = new Button("Delete");
            CustomerTm tm = new CustomerTm(counter, dto.getEmail(), dto.getName(), dto.getContact(), dto.getSalary(), btn);
            btn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        if (bo.deleteCustomer(dto.getEmail())) {
                            new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                            loadAllCustomers(searchText);
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                    }
                }
            });
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
            if (btnSaveCustomer.getText().equals("Save Customer")) {
                if (bo.saveCustomer(new CustomerDto(txtEmail.getText(), txtName.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText())))) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                    clearFields();
                    loadAllCustomers(searchText);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }
            } else {
                if (bo.updateCustomer(new CustomerDto(txtEmail.getText(), txtName.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText())))) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
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
