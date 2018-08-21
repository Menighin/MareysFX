package com.gapso.mareysfx.modules;

import com.gapso.mareysfx.com.gapso.mareysfx.MareysFX;
import com.gapso.mareysfx.entities.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MareysAxis {

    public static final int Y_AXIS_TOP_MARGIN  = 15;
    public static final int Y_AXIS_LEFT_MARGIN = 5;
    public static final int Y_AXIS_WIDTH = 120;
    public static final int X_AXIS_HEIGHT = 60;
    public static final int X_AXIS_TOP_MARGIN = 15;
    public static final Font STATION_LABEL_FONT = Font.font("Verdana", 14);
    public static final Font TIME_LABEL_FONT = Font.font("Verdana", 9);
    public static final Color LABEL_COLOR = Color.BLACK;
    public static final Color GRID_COLOR = Color.web("#CCC");
    public static final Color GRID_HIGHLIGHT_COLOR = Color.web("#AAA");

    private MareysFX _mareysFX;
    private MareysCanvas _canvas;
    private DrawingArea _drawingArea;
    private double _xFactor;
    private double _yFactor;

    public MareysAxis(MareysFX mareysFX) {
        this._mareysFX = mareysFX;
        this._canvas = mareysFX.getCanvasRenderer().getcanvas();
        calculateHelpers();
    }

    public DrawingArea getDrawingArea() {
        return this._drawingArea;
    }

    public double getXFactor() {
        return _xFactor;
    }

    public double getYFactor() {
        return _yFactor;
    }

    private void calculateHelpers() {
        GraphicsContext ctx = _canvas.getGraphicsContext2D();
        int height = this._mareysFX.getCanvasRenderer().getHeight();
        int width  = this._mareysFX.getCanvasRenderer().getWidth();
        ArrayList<MareysStation> stations = _mareysFX.getStations();
        TimeWindow timeWindow = _mareysFX.getTimeWindow();

        // Only makes sense to calculate this if there is stations set
        if (stations == null || timeWindow == null)
            return;

        // Drawing area should be at least 3 times the number of minutes on the window
        int minutes = timeWindow.getMinutesSpan();
        if (width < minutes * 3)
            width = minutes * 3;

        this._drawingArea = new DrawingArea(
            new Point(Y_AXIS_WIDTH, Y_AXIS_TOP_MARGIN),
            new Point(width, height + Y_AXIS_TOP_MARGIN - X_AXIS_HEIGHT)
        );

        // Defining the axis factors for transformations
        this._xFactor = (double)(_drawingArea.getP2().getX() - _drawingArea.getP1().getX())
                / (double)(timeWindow.getStart().until(timeWindow.getEnd(), ChronoUnit.MINUTES));

        this._yFactor = (_drawingArea.getP2().getY() - _drawingArea.getP1().getY())
                / _mareysFX.getStations().get(stations.size() - 1).getDistance();
    }

    public void draw() {

        // If the drawing area is not calculated yet, calculate it
        if (this._drawingArea == null)
            this.calculateHelpers();

        drawStations();
        drawTime();
    }

    private void drawStations() {
        CameraModule camera = _mareysFX.getCamera();
        GraphicsContext ctx = _canvas.getGraphicsContext2D();

        ctx.setFont(STATION_LABEL_FONT);
        ctx.setFill(LABEL_COLOR);
        ctx.setLineWidth(1);
        ctx.setStroke(GRID_COLOR);

        ctx.beginPath();
        for (MareysStation s : _mareysFX.getStations()) {
            int y = (int) Math.round(Y_AXIS_TOP_MARGIN + s.getDistance() * _yFactor);

            // Draw label
            double labelX = Y_AXIS_LEFT_MARGIN;
            ctx.fillText(s.getLabel(), labelX, y);

            // Draw horizontal line
            ctx.moveTo(_drawingArea.getP1().getX(), y);
            ctx.lineTo(_drawingArea.getP2().getX(), y);
        }
        ctx.stroke();
    }

    private void drawTime() {
        GraphicsContext ctx = this._canvas.getGraphicsContext2D();
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.setFont(TIME_LABEL_FONT);
        ctx.setFill(LABEL_COLOR);

        TimeWindow timeWindow = _mareysFX.getTimeWindow();

        int lineEachMinutes = getXAxisLineInterval();

        int totalMinutes = (int) timeWindow.getStart().until(timeWindow.getEnd(), ChronoUnit.MINUTES);

        ArrayList<Line> lines = new ArrayList<>();
        ArrayList<Line> highlightLines = new ArrayList<>();

        LocalDateTime currTime = timeWindow.getStart().minusHours(0);

        // Generating lines and drawing the labels
        for (int i = 0; i <= totalMinutes; i++) {
            int x = (int) Math.round(_drawingArea.getP1().getX() + i * _xFactor);

            if (i % lineEachMinutes == 0 && i % 60 != 0) {
                lines.add(new Line(
                   new Point(x, _drawingArea.getP2().getY()),
                   new Point(x, _drawingArea.getP1().getY())
                ));
            }

            if (i % 60 == 0) {
                highlightLines.add(new Line(
                    new Point(x, _drawingArea.getP2().getY()),
                    new Point(x, _drawingArea.getP1().getY())
                ));
            }

            // Draw label
            if (i > 0 && i % 30 == 0) {
                String label = String.format("%02d:%02d", currTime.getHour(), currTime.getMinute());
                ctx.fillText(label, x, _drawingArea.getP2().getY() + X_AXIS_TOP_MARGIN);
            }

            currTime = currTime.plusMinutes(1);
        }

        // Drawing the grid
        // Normal lines
        ctx.beginPath();
        ctx.setLineWidth(1);
        ctx.setStroke(GRID_COLOR);
        for (Line l : lines) {
            ctx.moveTo(l.getP1().getX(), l.getP1().getY());
            ctx.lineTo(l.getP2().getX(), l.getP2().getY());
        }
        ctx.stroke();

        // Highlighted lines
        ctx.beginPath();
        ctx.setLineWidth(1);
        ctx.setStroke(GRID_HIGHLIGHT_COLOR);
        for (Line l : highlightLines) {
            ctx.moveTo(l.getP1().getX(), l.getP1().getY());
            ctx.lineTo(l.getP2().getX(), l.getP2().getY());
        }
        ctx.stroke();

    }

    /**
     * Converts the given LocalDateTime to a position X on the chart
     * @param dateTime The LocalDateTime to be converted
     * @return
     */
    public int valueToXAXis(LocalDateTime dateTime) {
        return (int) Math.round(_drawingArea.getP1().getX() + _xFactor * _mareysFX.getTimeWindow().getStart().until(dateTime, ChronoUnit.MINUTES));
    }

    /**
     * Converts the given distance to a position Y on the chart
     * @param value The distance
     * @return
     */
    public int valueToYAxis(double value) {
        return (int) Math.round(_drawingArea.getP1().getY() + _yFactor * value);
    }

    /**
     * Converts a given X position to a value in time
     * @param x
     * @return
     */
    public LocalDateTime xAxisToValue(int x) {
        int minutes = (int) Math.round((x - _drawingArea.getP1().getX()) / _xFactor);
        return _mareysFX.getTimeWindow().getStart().plusMinutes(minutes);
    }

    /**
     * Converts a given Y position to a distance
     * @param y
     * @return
     */
    public double yAxisToValue(int y) {
        return (y - _drawingArea.getP1().getY()) / _yFactor;
    }

    private int getXAxisLineInterval() {
        double scale = _mareysFX.getCamera().getScale();
        if (scale > 4) return 5;
        else return 10;
    }

    public class DrawingArea {

        private Point _p1;
        private Point _p2;

        public DrawingArea() {
        }

        public DrawingArea(Point p1, Point p2) {
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

}