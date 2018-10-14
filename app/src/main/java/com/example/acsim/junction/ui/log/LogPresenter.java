package com.example.acsim.junction.ui.log;

import com.example.acsim.junction.data.LogRepository;
import com.example.acsim.junction.model.Log;

import java.util.List;

public class LogPresenter implements LogContractor.Presenter {

    private LogContractor.View view;
    private LogRepository logRepository;

    public LogPresenter(LogContractor.View view, LogRepository logRepository) {
        this.view = view;
        this.logRepository = logRepository;
    }

    @Override
    public void getLogList() {
        List<Log> logs = logRepository.getAllLog();
        view.showLogList(logs);
    }
}
