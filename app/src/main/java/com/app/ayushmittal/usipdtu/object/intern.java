package com.app.ayushmittal.usipdtu.object;

import android.provider.ContactsContract;

public class intern {
    String name;
    String roll;
    String email;
    String mobile;
    String duration;
    String mentor;
    boolean report;


    public intern(){

    }

    public intern(String name, String roll, String email, String mobile, String duration, String mentor) {
        this.name = name;
        this.roll = roll;
        this.email = email;
        this.mobile = mobile;
        this.duration = duration;
        this.mentor = mentor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public boolean isReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }


}
