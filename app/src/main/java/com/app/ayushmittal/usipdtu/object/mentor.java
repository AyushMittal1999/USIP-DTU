package com.app.ayushmittal.usipdtu.object;

public class mentor {

    String name;
    String moblie;
    String email;
    String dept;

   public mentor(){

    }

    public mentor(String name, String moblie, String email, String dept) {
        this.name = name;
        this.moblie = moblie;
        this.email = email;
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
