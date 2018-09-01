package com.example.myles.first;

import java.io.Serializable;

public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String light;
    private String water;
    private String soil;
    private boolean markForDeletion = false;

    public Plant()
    {

    }
    public Plant(String name, String light, String water, String soil)
    {
        this.name = name;
        this.light = light;
        this.water = water;
        this.soil = soil;
    }

    public String getName()
    {
        return name;
    }

    public String getLight()
    {
        return light;
    }

    public String getWater()
    {
        return water;
    }

    public String getSoil()
    {
        return soil;
    }

    public boolean getMarkForDeletion()
    {
        return markForDeletion;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLight(String light)
    {
        this.light = light;
    }

    public void setWater(String water)
    {
        this.water = water;
    }

    public void setSoil(String soil)
    {
        this.soil = soil;
    }

    public void setMarkForDeletion(boolean mark)
    {
        this.markForDeletion = mark;
    }
}
