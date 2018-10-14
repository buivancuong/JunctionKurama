package com.example.acsim.junction.data;

import com.example.acsim.junction.model.Coin;
import com.example.acsim.junction.model.Customer;

import java.util.List;

public class CoinRepo implements CoinRepository {

    private static CoinRepo instance = null;

    private Customer customer;

    public static CoinRepo getInstance() {
        if (instance == null) {
            instance = new CoinRepo();
        }
        return instance;
    }

    private CoinRepo() {
//        customer = new Customer();
//        customer.setIdCard("030095001104");
//        customer.setCustomerName("Bui Van Cuong");
//        customer.setUserID("20130482");
//        customer.setUserName("cuongbv");
//        customer.getCoinList().add(new Coin("HCoin", 1000));
//        customer.getCoinList().add(new Coin("CCoin", 2000));
//        customer.getCoinList().add(new Coin("MCoin", 3000));
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public List<Coin> getAllCoin() {
        return customer.getCoinList();
    }

    @Override
    public List<Coin> updateCoinList(List<Coin> coins) {
        return null;
    }

    @Override
    public List<Coin> getSortCoinList() {
        return null;
    }

    @Override
    public String getCustomerIDCard() {
        return customer.getIdCard();
    }

    @Override
    public String getCustomerName() {
        return customer.getCustomerName();
    }

    @Override
    public String getCustomUserID() {
        return customer.getUserID();
    }
}
