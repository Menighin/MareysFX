package com.gapso.mareysfx.entities

import java.time.LocalDateTime

class TrainSchedule (var time: LocalDateTime, var distance: Double) {

    override fun toString(): String {
        return "(${time.hour}:${time.minute}, ${distance})"
    }

}