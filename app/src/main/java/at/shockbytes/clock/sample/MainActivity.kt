package at.shockbytes.clock.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import at.shockbytes.clock.R
import at.shockbytes.clock.android.AnalogClockView
import at.shockbytes.clock.android.AnalogClockViewTheme
import at.shockbytes.clock.core.AnalogClockOptions

class MainActivity : AppCompatActivity() {

    private val analogClockView: AnalogClockView by lazy {
        findViewById(R.id.analogClockView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        analogClockView.apply {
            options = AnalogClockOptions(
                showSeconds = true,
                hourFormat = AnalogClockOptions.HourFormat.AmPmFormat
            )
            theme = AnalogClockViewTheme.custom(
                context = this@MainActivity,
                drawHourDecoration = false
            )
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        analogClockView.stop()
    }
}