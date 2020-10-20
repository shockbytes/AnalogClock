package at.shockbytes.clock.core.data

fun Int.toRadian(): Double {
    return toFloat().toRadian()
}

fun Float.toRadian(): Double {
    return div(180.toDouble()).times(Math.PI)
}
