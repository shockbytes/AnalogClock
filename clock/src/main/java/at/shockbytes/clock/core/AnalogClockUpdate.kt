package at.shockbytes.clock.core

import at.shockbytes.clock.core.data.Degree

data class AnalogClockUpdate(
    val angleHours: Degree,
    val angleMinutes: Degree,
    val angleSeconds: Degree
)