package com.gapso.mareysfx.entities;

public class MareysStation {

    private String id;
    private String label;
    private double distance;

    public MareysStation() {
    }

    public MareysStation(String id, String label, double distance) {
        this.id = id;
        this.label = label;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
