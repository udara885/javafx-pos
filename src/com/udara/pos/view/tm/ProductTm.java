package com.udara.pos.view.tm;

import javafx.scene.control.Button;

public class ProductTm {
    private int code;
    private String description;
    private Button showMore;
    private Button deleteButton;

    public ProductTm() {
    }

    public ProductTm(int code, String description, Button showMore, Button deleteButton) {
        this.code = code;
        this.description = description;
        this.showMore = showMore;
        this.deleteButton = deleteButton;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Button getShowMore() {
        return showMore;
    }

    public void setShowMore(Button showMore) {
        this.showMore = showMore;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
