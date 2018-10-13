package com.example.acsim.junction.model;

public class Coin {
    private String coinType;
    private int coinValue;

    public Coin() {
    }

    public Coin(String coinType, int coinValue) {
        this.coinType = coinType;
        this.coinValue = coinValue;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }
}
