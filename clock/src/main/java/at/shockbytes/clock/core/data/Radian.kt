package at.shockbytes.clock.core.data

data class Radian(val rad: Double) {

    companion object {

        fun fromDegrees(deg: Int): Radian {
            return deg.toRadian().let(::Radian)
        }
    }
}