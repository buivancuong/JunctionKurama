package com.example.acsim.junction.ui.log;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acsim.junction.R;
import com.example.acsim.junction.model.Log;
import com.example.acsim.junction.ui.main.MainContractor;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private List<Log> logs;

    public LogAdapter() {
        logs = new ArrayList<>();
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_log, viewGroup, false);
        return new LogViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull LogViewHolder logViewHolder, int i) {
        logViewHolder.BindLog(logs.get(i));
    }

    @Override
    public int getItemCount() {
        return logs != null ? logs.size() : 0;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public class LogViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ID, tv_From, tv_To, tv_TimeStamp, tv_TxHash;
        ConstraintLayout constraintLayout;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ID = itemView.findViewById(R.id.textViewIDValue);
            tv_From = itemView.findViewById(R.id.textViewFromValue);
            tv_To = itemView.findViewById(R.id.textViewToValue);
            tv_TimeStamp = itemView.findViewById(R.id.textViewTimeStampValue);
            tv_TxHash = itemView.findViewById(R.id.textViewTxHash);
            constraintLayout = itemView.findViewById(R.id.item_log);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void BindLog(final Log log) {
            tv_ID.setText(log.getId());
            tv_From.setText(log.getFrom());
            tv_To.setText(log.getTo());
            tv_TimeStamp.setText(Math.toIntExact(log.getTimestamp()));
            tv_TxHash.setText(log.getTxHash());
        }
    }
}
