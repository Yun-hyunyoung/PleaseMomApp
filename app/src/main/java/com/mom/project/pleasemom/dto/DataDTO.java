package com.mom.project.pleasemom.dto;

import java.io.Serializable;

/**
 * Created by sms on 2016-10-29.
 */
public class DataDTO implements Serializable{
    String scb_mediate;
    String scb_content;
    int scb_case;
    String scb_sdate;
    String scb_mem_num;
    String scb_num;
    String scb_title;
    String scb_via;
    String scb_wdate;
    String start;
    String arrival;

    public DataDTO(String scb_mediate, String scb_content, int scb_case, String scb_sdate, String scb_mem_num, String scb_num, String scb_title, String scb_via, String scb_wdate, String start, String arrival) {
        this.scb_mediate = scb_mediate;
        this.scb_content = scb_content;
        this.scb_case = scb_case;
        this.scb_sdate = scb_sdate;
        this.scb_mem_num = scb_mem_num;
        this.scb_num = scb_num;
        this.scb_title = scb_title;
        this.scb_via = scb_via;
        this.scb_wdate = scb_wdate;
        this.start = start;
        this.arrival = arrival;
    }

    public DataDTO() {

    }

    public String getScb_mediate() {
        return scb_mediate;
    }

    public void setScb_mediate(String scb_mediate) {
        this.scb_mediate = scb_mediate;
    }

    public String getScb_content() {
        return scb_content;
    }

    public void setScb_content(String scb_content) {
        this.scb_content = scb_content;
    }

    public int getScb_case() {
        return scb_case;
    }

    public void setScb_case(int scb_case) {
        this.scb_case = scb_case;
    }

    public String getScb_sdate() {
        return scb_sdate;
    }

    public void setScb_sdate(String scb_sdate) {
        this.scb_sdate = scb_sdate;
    }

    public String getScb_mem_num() {
        return scb_mem_num;
    }

    public void setScb_mem_num(String scb_mem_num) {
        this.scb_mem_num = scb_mem_num;
    }

    public String getScb_num() {
        return scb_num;
    }

    public void setScb_num(String scb_num) {
        this.scb_num = scb_num;
    }

    public String getScb_title() {
        return scb_title;
    }

    public void setScb_title(String scb_title) {
        this.scb_title = scb_title;
    }

    public String getScb_via() {
        return scb_via;
    }

    public void setScb_via(String scb_via) {
        this.scb_via = scb_via;
    }

    public String getScb_wdate() {
        return scb_wdate;
    }

    public void setScb_wdate(String scb_wdate) {
        this.scb_wdate = scb_wdate;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return "DataDTO{" +
                "scb_mediate='" + scb_mediate + '\'' +
                ", scb_content='" + scb_content + '\'' +
                ", scb_case=" + scb_case +
                ", scb_sdate='" + scb_sdate + '\'' +
                ", scb_mem_num='" + scb_mem_num + '\'' +
                ", scb_num='" + scb_num + '\'' +
                ", scb_title='" + scb_title + '\'' +
                ", scb_via='" + scb_via + '\'' +
                ", scb_wdate='" + scb_wdate + '\'' +
                ", start='" + start + '\'' +
                ", arrival='" + arrival + '\'' +
                '}';
    }
}
