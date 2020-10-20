package at.shockbytes.clock.core

data class AnalogClockOptions(
    val showSeconds: Boolean,
    val hourFormat: HourFormat
) {

    sealed class HourFormat {

        abstract fun amountHours(): Int

        object FullDayFormat : HourFormat() {
            override fun amountHours(): Int = 24
        }

        object AmPmFormat : HourFormat() {
            override fun amountHours(): Int = 12
        }
    }

    companion object {

        fun default(): AnalogClockOptions {
            return AnalogClockOptions(showSeconds = true, hourFormat = HourFormat.AmPmFormat)
        }
    }
}