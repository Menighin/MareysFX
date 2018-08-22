package com.gapso.mareysfx.entities;

import com.gapso.mareysfx.com.gapso.mareysfx.MareysFX
import com.gapso.mareysfx.interfaces.IDrawable
import java.util.ArrayList;

class MareysTrain (val id: String, val label: String, val group: String, var schedule: ArrayList<TrainSchedule> = ArrayList()) {

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

        if (this.points.size == 0)
            calculatePoints(chart)

        val ctx = chart.canvas.graphicsContext2D

        ctx.moveTo(this.points.first().x.toDouble(), this.points.first().y.toDouble())

        for ((i, p) in this.points.withIndex()) {
            if (i == 0) continue // Skips the first point because it should be just a moveTo
            ctx.lineTo(p.x.toDouble(), p.y.toDouble())
        }

    }

    override fun toString(): String {
        return "${id} - [${schedule}]"
    }


}
