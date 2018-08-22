package com.gapso.mareysfx.modules

import com.gapso.mareysfx.com.gapso.mareysfx.MareysFX
import com.gapso.mareysfx.interfaces.IDrawable

class MareysTrainModule (val chart: MareysFX) : IDrawable{

    override fun draw() {
        val ctx = this.chart.canvas.graphicsContext2D
        val trains = this.chart.trains


        ctx.beginPath()
        ctx.lineWidth = 3.0
        for (t in trains) {
            t.draw(this.chart)
        }
        ctx.stroke()
    }

}