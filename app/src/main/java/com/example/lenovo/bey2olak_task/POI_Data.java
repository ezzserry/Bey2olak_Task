package com.example.lenovo.bey2olak_task;

/**
 * Created by LENOVO on 28/02/2016.
 */
public class POI_Data {
    int id;
    String name, address, img;
     boolean isSelected;

    public POI_Data(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public POI_Data(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public POI_Data() {

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
