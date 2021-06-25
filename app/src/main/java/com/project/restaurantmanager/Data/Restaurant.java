package com.project.restaurantmanager.Data;

public class Restaurant {
    String name;
    String city;
    String address;
    String contact;
    String rid;
    String image;
    int reservaiton;
    int starttime;
    int endtime;

    public Restaurant(String name, String city, String address, String contact, String rid, String image,int starttime,int endtime) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.contact = contact;
        this.rid = rid;
        this.image = image;
    }

    public Restaurant(String name, String city, String address, String contact, String rid, String image, int reservaiton, int starttime, int endtime) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.contact = contact;
        this.rid = rid;
        this.image = image;
        this.reservaiton = reservaiton;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getReservaiton() {
        return reservaiton;
    }

    public void setReservaiton(int reservaiton) {
        this.reservaiton = reservaiton;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }
}
