package com.gapso.mareysfx.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeWindow {

    private LocalDateTime _start;
    private LocalDateTime _end;

    public TimeWindow() {
    }

    public TimeWindow(LocalDateTime start, LocalDateTime end) {
        this._start = start;
        this._end = end;
    }

    public LocalDateTime getStart() {
        return _start;
    }

    public void setStart(LocalDateTime start) {
        this._start = start;
    }

    public LocalDateTime getEnd() {
        return _end;
    }

    public void setEnd(LocalDateTime end) {
        this._end = end;
    }

    public int getMinutesSpan() {
        return (int) _start.until(_end, ChronoUnit.MINUTES);
    }
}
