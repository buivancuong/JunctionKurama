package com.example.acsim.junction.ui.log;

import com.example.acsim.junction.model.Log;

import java.util.List;

public interface LogContractor {
    interface View{
        public void showLogList(List<Log> logs);
    }
    interface Presenter{
        void getLogList();
    }
}
