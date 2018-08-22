package com.gapso.mareysfx.com.gapso.mareysfx;

import com.gapso.mareysfx.entities.MareysStation;
import com.gapso.mareysfx.entities.MareysTrain;
import com.gapso.mareysfx.entities.TimeWindow;
import com.gapso.mareysfx.interfaces.IChart;
import com.gapso.mareysfx.modules.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MareysFX extends Pane implements IChart {

    private CanvasRenderer _canvasRenderer;
    private MareysCanvas _canvas;
    private MareysAxis _mareysAxis;
    private InteractionModule _interactionModule;
    private CameraModule _cameraModule;
    private MareysTrainModule _mareysTrainModule;
    private ArrayList<MareysStation> _stations;
    private TimeWindow _timeWindow;
    private ArrayList<MareysTrain> _trains;

    public MareysFX(int width, int height) {
        this._canvasRenderer = new CanvasRenderer(this, width, height);
        this._canvas = this._canvasRenderer.getcanvas();
        this._mareysAxis = new MareysAxis(this);

        this._cameraModule = new CameraModule();
        this._interactionModule = new InteractionModule(this);
        this._mareysTrainModule = new MareysTrainModule(this);

        getChildren().add(_canvasRenderer.getcanvas());
    }

    public ArrayList<MareysStation> getStations() {
        return _stations;
    }

    public void setStations(ArrayList<MareysStation> stations) {
        ArrayList<MareysStation> stationsSorted = new ArrayList<>(stations);
        stationsSorted.sort((o1, o2) -> (int) (o1.getDistance() - o2.getDistance()));
        this._stations = stationsSorted;
    }

    public ArrayList<MareysTrain> getTrains() {
        return _trains;
    }

    public void setTrains(ArrayList<MareysTrain> trains) {
        this._trains = trains;
    }

    public TimeWindow getTimeWindow() {
        return _timeWindow;
    }

    public void setTimeWindow(TimeWindow timeWindow) {
        this._timeWindow = timeWindow;
    }

    public CanvasRenderer getCanvasRenderer() {
        return this._canvasRenderer;
    }

    public MareysAxis getMareysAxis() {
        return _mareysAxis;
    }

    public void setMreysAxis(MareysAxis mareysAxis) {
        this._mareysAxis = mareysAxis;
    }

    public void initDrawing() {
        this._canvasRenderer.initDrawing();
    }

    @Override
    public void draw() {
        _mareysAxis.draw();
        _mareysTrainModule.draw();
    }

    @Override
    public Canvas getCanvas() {
        return this._canvas;
    }

    @Override
    public CameraModule getCamera() {
        return this._cameraModule;
    }
}
