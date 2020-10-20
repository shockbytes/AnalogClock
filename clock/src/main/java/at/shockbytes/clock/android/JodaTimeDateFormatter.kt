package at.shockbytes.clock.android

import at.shockbytes.clock.core.data.FormattedDate
import at.shockbytes.clock.core.AnalogClockDateFormatter
import org.joda.time.DateTime

object JodaTimeDateFormatter: AnalogClockDateFormatter {

    override fun formatMillis(millis: Long): FormattedDate {
        return with (DateTime(millis)) {
            FormattedDate(hourOfDay, minuteOfHour, secondOfMinute)
        }
    }
}