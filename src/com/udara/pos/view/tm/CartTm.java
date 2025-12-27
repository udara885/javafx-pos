package com.udara.pos.view.tm;

import javafx.scene.control.Button;

public class CartTm {
    private String code;
    private String description;
    private double sellingPrice;
    private double discount;
    private double showPrice;
    private int qty;
    private double totalCost;
    private Button deleteBtn;

    public CartTm() {
    }

    public CartTm(String code, String description, double sellingPrice, double discount, double showPrice, int qty, double totalCost, Button deleteBtn) {
        this.code = code;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.showPrice = showPrice;
        this.qty = qty;
        this.totalCost = totalCost;
        this.deleteBtn = deleteBtn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }
}
