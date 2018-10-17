package com.example.esso;

public class ord_item {
    private String item_id;
    private String item_name;
    private String quantity;
    private String price;

    public ord_item(String quantity,String price,String item_name){
        this.quantity=quantity;
        this.price=price;
        this.item_name=item_name;}
    public ord_item(){}
    public String getItemid() {return item_id;}
    public void setItemid(String itemid) {
        this.item_id = itemid;
    }

    public String getQuantity() {return quantity;}
    public void setQuantity(String quantity) {this.quantity = quantity;}

    public String getprice(){return this.price;}
    public void setprice(String price){this.price=price;}

    public String getName() {
        return item_name;
    }
    public void setName(String name) {
        this.item_name = name;
    }

}

