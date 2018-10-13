package com.example.acsim.junction.model;

import android.util.Log;

import com.google.gson.Gson;

public class GetItemInfo {
    private String itemID;
    private String idCard;


    public GetItemInfo(String idItem, String idCard) {
        this.itemID = idItem;
        this.idCard = idCard;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String Ob2Json(GetItemInfo getItemInfo) {
//        Gson gson = new Gson();
        Log.i("ID Card: ", getItemInfo.getIdCard());
        Log.i("ID item: ", getItemInfo.getItemID());
//        String json = gson.toJson(getItemInfo);
        return "{\"idCard\":\"" + getItemInfo.getIdCard() + "\", \"idItem\":\"" + getItemInfo.getItemID() + "\"}";
    }
}
