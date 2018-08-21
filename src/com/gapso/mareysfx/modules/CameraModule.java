package com.gapso.mareysfx.modules;

public class CameraModule {

    private double _translateX;
    private double _translateY;
    private double _scale;

    public CameraModule() {
        this._translateX = .0;
        this._translateY = .0;
        this._scale = 1;
    }

    public double getTranslateX() {
        return _translateX;
    }

    public void setTranslateX(double _translateX) {
        this._translateX = _translateX;
    }

    public double getTranslateY() {
        return _translateY;
    }

    public void setTranslateY(double _translateY) {
        this._translateY = _translateY;
    }

    public double getScale() {
        return _scale;
    }

    public void setScale(double _scale) {
        this._scale = _scale;
    }
}
