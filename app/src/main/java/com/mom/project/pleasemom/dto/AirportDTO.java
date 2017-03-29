package com.mom.project.pleasemom.dto;

/**
 * Created by 08_718 on 2016-11-11.
 */
public class AirportDTO {
    private int num;
    private String name_kr;
    private String country_kr;
    private String city_kr;
    private String location;

    public AirportDTO() {
    }

    public AirportDTO(int num, String name_kr, String country_kr, String city_kr, String location) {
        this.num = num;
        this.name_kr = name_kr;
        this.country_kr = country_kr;
        this.city_kr = city_kr;
        this.location = location;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName_kr() {
        return name_kr;
    }

    public void setName_kr(String name_kr) {
        this.name_kr = name_kr;
    }

    public String getCountry_kr() {
        return country_kr;
    }

    public void setCountry_kr(String country_kr) {
        this.country_kr = country_kr;
    }

    public String getCity_kr() {
        return city_kr;
    }

    public void setCity_kr(String city_kr) {
        this.city_kr = city_kr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "AirportDTO{" +
                "num=" + num +
                ", name_kr='" + name_kr + '\'' +
                ", country_kr='" + country_kr + '\'' +
                ", city_kr='" + city_kr + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
