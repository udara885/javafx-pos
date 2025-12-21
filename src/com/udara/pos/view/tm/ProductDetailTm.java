package com.udara.pos.view.tm;

import javafx.scene.control.Button;

public class ProductDetailTm {
    private String code;
    private int qtyOnHand;
    private double sellingPrice;
    private double buyingPrice;
    private boolean discountAvailability;
    private double showPrice;
    private Button deleteBtn;

    public ProductDetailTm() {
    }

    public ProductDetailTm(String code, int qtyOnHand, double sellingPrice, double buyingPrice, boolean discountAvailability, double showPrice, Button deleteBtn) {
        this.code = code;
        this.qtyOnHand = qtyOnHand;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.discountAvailability = discountAvailability;
        this.showPrice = showPrice;
        this.deleteBtn = deleteBtn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public boolean isDiscountAvailability() {
        return discountAvailability;
    }

    public void setDiscountAvailability(boolean discountAvailability) {
        this.discountAvailability = discountAvailability;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }
}
