package com.example.weatherappusingrestapi;

import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("main")
    Main main;

    @SerializedName("coord")
    Coord coord;

    public Main getMain()
    {
        return main;
    }

    public Coord getCoord(){return coord;}

    public void setMain(Main main)
    {
        this.main = main;
    }

}
