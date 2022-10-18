package com.example.ewastemanagement;

public class PostModel {
    private String waste,pickupdate,pickuptime,address;
    private String notes,rewardType,imageurl,email;

    public PostModel(String email,String waste, String pickupdate, String pickuptime, String address, String notes, String rewardType, String imageurl) {
        this.email=email;
        this.waste = waste;
        this.pickupdate = pickupdate;
        this.pickuptime = pickuptime;
        this.address = address;
        this.notes = notes;
        this.rewardType = rewardType;
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWaste() {
        return waste;
    }

    public void setWaste(String waste) {
        this.waste = waste;
    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
