package com.gapso.mareysfx.modules;

import com.gapso.mareysfx.interfaces.IDrawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class MareysCanvas extends Canvas {

    private IDrawable _drawable;

    public MareysCanvas(IDrawable drawable) {
        // Redraw canvas when size changes.
        this._drawable = drawable;
        widthProperty().addListener(evt -> this._drawable.draw());
        heightProperty().addListener(evt -> this._drawable.draw());
    }

    public MareysCanvas(IDrawable drawable, int width, int height) {
        super(width, height);
        this._drawable = drawable;
        widthProperty().addListener(evt -> this._drawable.draw());
        heightProperty().addListener(evt -> this._drawable.draw());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }
    @Override
    public double prefHeight(double width) {
        return getHeight();
    }


}
