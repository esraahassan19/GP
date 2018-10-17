package com.example.esso;
import java.util.ArrayList;
public class received_Oreders  {
    private String ord_id;
    private String name;
    private String rest_name;
    private String date;
    private String time;
    private String Total_cost;
    private ArrayList<ord_item> ordered_items;
    private String table_num;
  public received_Oreders(){}
    public ArrayList<ord_item> getreciveditems(){return ordered_items;}
    public void setreciveditems(ArrayList<ord_item>t){this.ordered_items=t;}
    public String getrecivedname() {return name;}
    public void setrecivedname(String name) {
        this.name = name;
    }
    public String getname () {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
    public String getrest_name () {
        return rest_name;
    }
    public void setRest_name(String Rest_name) {
        this.rest_name = Rest_name;
    }
    public String getDate() {return date;}
    public void setDate(String Date) {
        this.date = Date;
    }
    public String gettime() {
        return time;
    }
    public void settime(String Time) {this.time = Time;}
    public String getCost() {return Total_cost;}
    public void setCost(String Cost) {this.Total_cost = Cost;}
    public String getOrd_id() {
        return ord_id;
    }
    public void setOrd_id(String Ord_id) {this.ord_id = Ord_id;}
    public String getTable_num() {
        return table_num;
    }
    public void setTable_num(String table_num) {this.table_num = table_num;}

}
