package com.gapso.mareysfx.interfaces;

import com.gapso.mareysfx.modules.CameraModule;
import javafx.scene.canvas.Canvas;

public interface IChart extends IDrawable {

    Canvas getCanvas();

    CameraModule getCamera();
}
