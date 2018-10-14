package com.example.acsim.junction.data;

import com.example.acsim.junction.model.Log;

import java.util.ArrayList;
import java.util.List;

public class LogRepo implements LogRepository {

    private static LogRepo instance = null;

    private ArrayList<Log> logs;

    private LogRepo() {
    }

    public static LogRepo getInstance() {
        if (instance == null) {
            instance = new LogRepo();
        }
        return instance;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Log> logs) {
        this.logs = logs;
    }

    @Override
    public List<Log> getAllLog() {
        return logs;
    }
}
