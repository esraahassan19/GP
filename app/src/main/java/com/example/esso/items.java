package com.example.esso;
public class items{
    private String image;
    private String name;
    private String price;
    private String des;
    private String cat;
  private String restaurant_name;
    private  String item_id;

    public items(String image, String name, String price) {this.image = image;this.name = name;this.price = price;}
    public items() {}
    public String getImage() {
        return image;
    }
    public void setImage(String image) {this.image = image;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getdes() {
        return des;
    }
    public void setdes(String des) {this.des = des;}
    public String getcat() {
        return cat;
    }
    public void setcat(String cat) {this.cat = cat;}
    public String getRestaurant_name() {
        return restaurant_name;
    }
    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }
    public String getItem_id () {
        return item_id;
    }
    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
}

