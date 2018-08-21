package com.gapso.mareysfx.entities;

import com.gapso.mareysfx.com.gapso.mareysfx.MareysFX
import java.util.ArrayList;

class MareysTrain(val id: String, val label: String, val group: String, var schedule: ArrayList<TrainSchedule> = ArrayList()) {

    var points: ArrayList<Point> = ArrayList()
        private set


    private fun calculatePoints(chart: MareysFX) {
        val axis = chart.mareysAxis

        for(s in schedule) {
            val p = Point(axis.valueToXAXis(s.time), axis.valueToYAxis(s.distance))
            points.add(p)
        }
    }

    fun draw(chart: MareysFX) {




    }



}
