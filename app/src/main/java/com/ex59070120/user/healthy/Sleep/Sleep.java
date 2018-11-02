package com.ex59070120.user.healthy.Sleep;

import java.util.Date;

public class Sleep {
    private String date;
    private String time_sleep;
    private String time_wakeup;

    public Sleep(String date,String time_sleep,String time_wakeup) {
        this.date = date;
        this.time_sleep = time_sleep;
        this.time_wakeup = time_wakeup;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_sleep() {
        return time_sleep;
    }

    public void setTime_sleep(String time_sleep) {
        this.time_sleep = time_sleep;
    }

    public String getTime_wakeup() {
        return time_wakeup;
    }

    public void setTime_wakeup(String time_wakeup) {
        this.time_wakeup = time_wakeup;
    }
}
