package com.example.esso;

import java.util.List;

public class Restaurant {
    private  String name;
    private String  Qr_img;
    private  String checkWaiting;
    private List<String> Categories;
    private  String number_minuts;
    private String Number_of_tables;
    public String getQr_image()
    {
        return Qr_img;
    }
    public void setQr_image(String Qr_img)
    {
        this.Qr_img=Qr_img;
    }

    public void setQr_name(String name)
    {
        this.name=name;
    }
    public String getQr_name()
    {
        return name;
    }

    public void  setcat_nam (List<String>  cat_name)
    {
        this.Categories=cat_name;
    }
    public List<String>  getcat_nam ()
    {
        return Categories;
    }

    public void setCheckWaiting(String checkWaiting)
    {
        this.checkWaiting=checkWaiting;
    }
    public String getCheckWaiting()
    {
        return checkWaiting;
    }

    public void  setNumber_minuts (String number_minuts)
    {
        this.number_minuts=number_minuts;
    }
    public String getNumber_minuts()
    {
        return number_minuts;
    }

    public String getNumber_of_tables()
    {
        return Number_of_tables;
    }
    public void SetNumber_of_of_tables(String Number_of_tables )
    {
        this.Number_of_tables=Number_of_tables;
    }

}
