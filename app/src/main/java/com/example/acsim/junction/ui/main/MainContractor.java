package com.example.acsim.junction.ui.main;

import com.example.acsim.junction.model.Coin;

import java.util.List;

public interface MainContractor {
    interface View {
        void showCoinList(List<Coin> coinList);
        void showCustomerName();
        void showCustomerIDCard();
        void showCustomerUserID();
    }
    interface Presenter {
        void getCoinList();
        String getCustomerIDCard();
        String getCustomerName();
        String getCustomerUserID();
    }
}
