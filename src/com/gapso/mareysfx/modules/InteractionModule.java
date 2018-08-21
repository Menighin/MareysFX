package com.gapso.mareysfx.modules;

import com.gapso.mareysfx.interfaces.IChart;
import com.gapso.mareysfx.utils.Utils;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;


public class InteractionModule {

    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    private static final double ZOOM_SPEED = .005d;

    private IChart _chart;

    private MouseEvent _touchPointer;
    private double _initialTranslateX;
    private double _initialTranslateY;

    public InteractionModule(IChart chart) {
        this._chart = chart;

        this.bindEvents();
    }

    private void bindEvents() {
        _chart.getCanvas().setOnMousePressed(evt -> onTouch(evt));
        _chart.getCanvas().setOnMouseDragged(evt -> onDrag(evt));
        _chart.getCanvas().setOnScroll(evt -> onMouseWheel(evt));
    }

    private void onTouch(MouseEvent evt) {
        this._touchPointer = evt;

        this._initialTranslateX = this._chart.getCamera().getTranslateX();
        this._initialTranslateY = this._chart.getCamera().getTranslateY();

    }

    private void onDrag(MouseEvent evt) {
        CameraModule camera = _chart.getCamera();

        camera.setTranslateX(this._initialTranslateX + evt.getX() - _touchPointer.getX());
        camera.setTranslateY(this._initialTranslateY + evt.getY() - _touchPointer.getY());
    }

    private void onMouseWheel(ScrollEvent evt) {

        CameraModule camera = _chart.getCamera();

        double delta = evt.getDeltaY();

        double scale = camera.getScale();
        double oldScale = scale;

        double zoom = delta * ZOOM_SPEED;
        if (delta < 0)
            zoom = zoom / (1 - zoom);

        scale *= (1 + zoom);

        scale = Utils.clamp(scale, MIN_SCALE, MAX_SCALE);

        double scaleFrac = scale / oldScale;
        double tx = (1 - scaleFrac) * evt.getX() + camera.getTranslateX() * scaleFrac;
        double ty = (1 - scaleFrac) * evt.getY() + camera.getTranslateY() * scaleFrac;

        camera.setTranslateX(tx);
        camera.setTranslateY(ty);
        camera.setScale(scale);

        evt.consume();
    }


}
