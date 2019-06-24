package com.app.ayushmittal.usipdtu.object;

public class work_report {

    intern student;
    mentor mentor;
    String month;
    String hours;
    String remarks;

    public work_report(){
        student= new intern();
    }

    public work_report(intern student, String month) {
        this.student = student;
        this.month = month;
    }

    public com.app.ayushmittal.usipdtu.object.mentor getMentor() {
        return mentor;
    }

    public void setMentor(com.app.ayushmittal.usipdtu.object.mentor mentor) {
        this.mentor = mentor;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public intern getStudent() {
        return student;
    }

    public void setStudent(intern student) {
        this.student = student;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
