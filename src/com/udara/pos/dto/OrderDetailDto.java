package com.udara.pos.dto;

import java.util.Date;
import java.util.List;

public class OrderDetailDto  {
    private int code;
    private Date issuedDate;
    private double totalCost;
    private String customerEmail;
    private double discount;
    private String userEmail;

    private List<ItemDetailDto> itemDetailDto;

    public OrderDetailDto() {
    }

    public OrderDetailDto(int code, Date issuedDate, double totalCost, String customerEmail, double discount, String userEmail, List<ItemDetailDto> itemDetailDto) {
        this.code = code;
        this.issuedDate = issuedDate;
        this.totalCost = totalCost;
        this.customerEmail = customerEmail;
        this.discount = discount;
        this.userEmail = userEmail;
        this.setItemDetailsDto(itemDetailDto);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<ItemDetailDto> getItemDetailsDto() {
        return itemDetailDto;
    }

    public void setItemDetailsDto(List<ItemDetailDto> itemDetailDto) {
        this.itemDetailDto = itemDetailDto;
    }
}
