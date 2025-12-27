package ru.praktikumservices.stand.qadesk.models;

import lombok.Data;

@Data
public class Announcement {
    private String name;
    private String category;
    private String condition;
    private String city;
    private String description;
    private String price;
    private byte[] image1;
    private byte[] image2;
    private byte[] image3;

}
