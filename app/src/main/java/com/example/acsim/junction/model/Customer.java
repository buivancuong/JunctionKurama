package com.example.acsim.junction.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String idCard;
    private String customerName;
    private String userID;
    private String phoneNum;
    private String userName;
    private String passWord;
    private String role;
    private List<Coin> coinList = new ArrayList<>();

    public Customer() {
    }

    public Customer(String idCard, String customerName, String userID) {
        this.idCard = idCard;
        this.customerName = customerName;
        this.userID = userID;
    }

    private Customer (Builder builder) {
        this.idCard = builder.idCard;
        this.customerName = builder.customerName;
        this.userID = builder.userID;
        this.phoneNum = builder.phoneNum;
        this.userName = builder.userName;
        this.passWord = builder.passWord;
        this.role = builder.role;
    }

    public static class Builder {

        private String idCard = "";
        private String customerName = "";
        private String userID = "";
        private String phoneNum = "";
        private String userName = "";
        private String passWord = "";
        private String role = "";

        public Builder setIdCard(String idCard) {
            this.idCard = idCard;
            return this;
        }

        public Builder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder setUserID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPassWord(String passWord) {
            this.passWord = passWord;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Customer builder() {
            return new Customer(this);
        }
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserID() {
        return userID;
    }

    public List<Coin> getCoinList() {
        return coinList;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
