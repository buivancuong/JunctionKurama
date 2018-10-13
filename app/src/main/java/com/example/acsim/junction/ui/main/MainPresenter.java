package com.example.acsim.junction.ui.main;

import com.example.acsim.junction.data.CoinRepository;
import com.example.acsim.junction.model.Coin;

import java.util.List;

public class MainPresenter implements MainContractor.Presenter {

    private MainContractor.View view;
    private CoinRepository coinRepository;

    public MainPresenter (MainContractor.View view, CoinRepository coinRepository) {
        this.view = view;
        this.coinRepository = coinRepository;
    }

    @Override
    public void getCoinList() {
        List<Coin> coins = coinRepository.getAllCoin();
        view.showCoinList(coins);
    }

    @Override
    public String getCustomerIDCard() {
        return coinRepository.getCustomerIDCard();
    }

    @Override
    public String getCustomerName() {
        return coinRepository.getCustomerName();
    }

    @Override
    public String getCustomerUserID() {
        return coinRepository.getCustomUserID();
    }
}
