package com.example.workingstudent.ui.home;

import java.io.Serializable;

public class WorkDay implements Serializable {


    private int id;
    private int m_workingTime;
    private int m_breakTime;
    private String m_date;
    private int m_Lohn;
    private String m_notizen;


    public WorkDay(int id, int m_workingTime, int m_breakTime, String m_date, String m_notizen, int m_zuschlag) {
        this.id = id;
        this.m_workingTime = m_workingTime;
        this.m_breakTime = m_breakTime;
        this.m_date = m_date;
        this.m_notizen = m_notizen;
        this.m_Lohn = m_zuschlag;
    }

    public int getM_workingTime() {
        return m_workingTime;
    }

    public void setM_workingTime(int m_workingTime) {
        this.m_workingTime = m_workingTime;
    }

    public int getM_breakTime() {
        return m_breakTime;
    }

    public void setM_breakTime(int m_breakTime) {
        this.m_breakTime = m_breakTime;
    }

    public String getM_date() {
        return m_date;
    }

    public void setM_date(String m_date) {
        this.m_date = m_date;
    }

    public String getM_notizen() {
        return m_notizen;
    }

    public void setM_notizen(String m_notizen) {
        this.m_notizen = m_notizen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getM_Lohn() {
        return m_Lohn;
    }

    public void setM_Lohn(int m_Lohn) {
        this.m_Lohn = m_Lohn;
    }


}
