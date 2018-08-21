package com.gapso.mareysfx.entities;

public class Line {

    private Point _p1;
    private Point _p2;

    public Line() {
    }

    public Line(Point p1, Point p2) {
        this._p1 = p1;
        this._p2 = p2;
    }

    public Point getP1() {
        return _p1;
    }

    public void setP1(Point p1) {
        this._p1 = p1;
    }

    public Point getP2() {
        return _p2;
    }

    public void setP2(Point p2) {
        this._p2 = p2;
    }
}
