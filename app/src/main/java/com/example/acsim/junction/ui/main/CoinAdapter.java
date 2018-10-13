package com.example.acsim.junction.ui.main;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acsim.junction.R;
import com.example.acsim.junction.model.Coin;

import java.util.ArrayList;
import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

    private List<Coin> coinList;

    private CoinClickListener coinClickListener;

    public CoinAdapter() {
        coinList = new ArrayList<>();
    }

    public void setCoinClickListener (CoinClickListener coinClickListener) {
        this.coinClickListener = coinClickListener;
    }

    public void setCoinList(List<Coin> coinList) {
        this.coinList = coinList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_coin, viewGroup, false);
        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder coinViewHolder, int i) {
        coinViewHolder.bindCoin(coinList.get(i));
    }

    @Override
    public int getItemCount() {
        return coinList != null ? coinList.size() : 0;
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder {

        TextView tv_coinType, tv_coinValue;
        ConstraintLayout CL_coinItem;
//        String coinType;
//        Integer coinValue;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_coinType = itemView.findViewById(R.id.textViewItemCoinType);
            tv_coinValue = itemView.findViewById(R.id.textViewItemCoinValue);
            CL_coinItem = itemView.findViewById(R.id.coinItem);
        }

        @SuppressLint("SetTextI18n")
        public void bindCoin(final Coin coin) {
            tv_coinType.setText(coin.getCoinType());
            tv_coinValue.setText(coin.getCoinValue() + "");

            CL_coinItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (coinClickListener != null) {
                        coinClickListener.onCoinItemClick(coin);
                    }
                }
            });
        }
    }

    public interface CoinClickListener {
        void onCoinItemClick (Coin coin);
        void onCoinItemLongClick (Coin coin);
    }
}
