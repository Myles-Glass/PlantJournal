package com.example.myles.first;

import java.io.Serializable;
import java.util.ArrayList;

public class PlantList implements Serializable{

    private static final long serialVersionUID = 1L;

    public ArrayList<Plant> plantList = new ArrayList<Plant>();

    public PlantList()
    {

    }

    public Plant getPlant(int i)
    {
        return plantList.get(i);
    }

    public void addPlant(Plant plant)
    {
        plantList.add(plant);
    }

    public void removePlant(Plant plant)
    {
        plantList.remove(plant);
    }

    public int getSize()
    {
        return plantList.size();
    }
}
