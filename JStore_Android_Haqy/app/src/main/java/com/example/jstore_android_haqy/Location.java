package com.example.jstore_android_haqy;

public class Location {
    private String province;
    private String description;
    private String city;

    public Location(String province, String description, String city) {
        this.province = province;
        this.description = description;
        this.city = city;
    }

    public String getProvince() {
        return this.province;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCity() {
        return this.city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
