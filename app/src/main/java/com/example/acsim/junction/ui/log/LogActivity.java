package com.example.acsim.junction.ui.log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.acsim.junction.R;
import com.example.acsim.junction.data.LogRepo;
import com.example.acsim.junction.model.Log;

import java.util.List;

public class LogActivity extends AppCompatActivity implements LogContractor.View {

    RecyclerView recyclerView;
    LogAdapter logAdapter;
    LogPresenter logPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        recyclerView = findViewById(R.id.recycleView);

        logAdapter = new LogAdapter();
        recyclerView.setAdapter(logAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        logPresenter = new LogPresenter(this, LogRepo.getInstance());
        logPresenter.getLogList();
    }

    @Override
    public void showLogList(List<Log> logs) {
        logAdapter.setLogs(logs);
    }
}
