package com.example.acsim.junction.data;

import com.example.acsim.junction.model.Coin;

import java.util.List;

public interface CoinRepository {
    List<Coin> getAllCoin();
    List<Coin> updateCoinList(List<Coin> coins);
    List<Coin> getSortCoinList();

    String getCustomerIDCard();
    String getCustomerName();
    String getCustomUserID();
}
