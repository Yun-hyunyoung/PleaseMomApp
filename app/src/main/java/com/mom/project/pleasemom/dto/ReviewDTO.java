package com.mom.project.pleasemom.dto;

import java.io.Serializable;

/**
 * Created by 08_718 on 2016-11-07.
 */
public class ReviewDTO implements Serializable{
    private int review_num;
    private String review_content;
    private double review_star;
    private MemberDTO reqMemDto;
    private MemberDTO guiMemDto;
    private DataDTO boardDto;
    private String review_wdate;
    private String diffDate;

    public ReviewDTO() { }

    public ReviewDTO(int review_num, String review_content, double review_star, MemberDTO reqMemDto, MemberDTO guiMemDto, DataDTO boardDto, String review_wdate, String diffDate) {
        this.review_num = review_num;
        this.review_content = review_content;
        this.review_star = review_star;
        this.reqMemDto = reqMemDto;
        this.guiMemDto = guiMemDto;
        this.boardDto = boardDto;
        this.review_wdate = review_wdate;
        this.diffDate = diffDate;
    }

    public int getReview_num() {
        return review_num;
    }

    public void setReview_num(int review_num) {
        this.review_num = review_num;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

    public double getReview_star() {
        return review_star;
    }

    public void setReview_star(double review_star) {
        this.review_star = review_star;
    }

    public MemberDTO getReqMemDto() {
        return reqMemDto;
    }

    public void setReqMemDto(MemberDTO reqMemDto) {
        this.reqMemDto = reqMemDto;
    }

    public MemberDTO getGuiMemDto() {
        return guiMemDto;
    }

    public void setGuiMemDto(MemberDTO guiMemDto) {
        this.guiMemDto = guiMemDto;
    }

    public DataDTO getBoardDto() {
        return boardDto;
    }

    public void setBoardDto(DataDTO boardDto) {
        this.boardDto = boardDto;
    }

    public String getReview_wdate() {
        return review_wdate;
    }

    public void setReview_wdate(String review_wdate) {
        this.review_wdate = review_wdate;
    }

    public String getDiffDate() {
        return diffDate;
    }

    public void setDiffDate(String diffDate) {
        this.diffDate = diffDate;
    }

    @Override
    public String toString() {
        return "ReviewDTO{" +
                "review_num=" + review_num +
                ", review_content='" + review_content + '\'' +
                ", review_star=" + review_star +
                ", reqMemDto=" + reqMemDto +
                ", guiMemDto=" + guiMemDto +
                ", boardDto=" + boardDto +
                ", review_wdate='" + review_wdate + '\'' +
                ", diffDate='" + diffDate + '\'' +
                '}';
    }
}
