package at.shockbytes.clock.core

import at.shockbytes.clock.core.data.FormattedDate

interface AnalogClockDateFormatter {

    fun formatMillis(millis: Long): FormattedDate
}
