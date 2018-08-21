package com.gapso.mareysfx.modules;

import com.gapso.mareysfx.interfaces.IChart;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class CanvasRenderer {

    private IChart _chart;
    private MareysCanvas _canvas;
    private AnimationTimer _animationTimer;
    private int _width;
    private int _height;

    public CanvasRenderer(IChart chart, int width, int height) {
        _chart = chart;
        _canvas = new MareysCanvas(_chart, width, height);
        _width = width;
        _height = height;
    }

    public MareysCanvas getcanvas() {
        return this._canvas;
    }

    public int getWidth() {
        return this._width;
    }

    public int getHeight() {
        return this._height;
    }

    public void initDrawing() {
        _animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Clear canvas
                GraphicsContext ctx = _canvas.getGraphicsContext2D();
                ctx.clearRect(0, 0, _width, _height);

                // Draw on it
                ctx.save();
                ctx.translate(_chart.getCamera().getTranslateX(), _chart.getCamera().getTranslateY());
                ctx.scale(_chart.getCamera().getScale(), _chart.getCamera().getScale());

                _chart.draw();

                ctx.restore();
            }
        };
        _animationTimer.start();
    }

}
