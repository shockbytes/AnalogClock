package at.shockbytes.clock.core

import at.shockbytes.clock.core.data.Degree
import at.shockbytes.clock.rx.SchedulersFacade
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Basically platform independent engine for powering the analog clock.
 * Based on RxJava. (There could be an implementation based on TimerTask too)
 */
class AnalogClockViewEngine(
    private val dateFormatter: AnalogClockDateFormatter,
    private val schedulers: SchedulersFacade,
    private val millisProvider: () -> Long = System::currentTimeMillis,
    initialOptions: AnalogClockOptions = AnalogClockOptions.default(),
) {

    private val views: MutableSet<AnalogClockViewDefinition> = mutableSetOf()

    private var timerDisposable: Disposable? = null

    var options: AnalogClockOptions = initialOptions

    fun start() {
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(schedulers.computation())
            .observeOn(schedulers.ui())
            .subscribe(::handleTick)
    }

    private fun handleTick(unused: Long) {

        val viewUpdate = computeClockUpdate(millisProvider(), options)

        views.forEach { view ->
            view.update(viewUpdate)
        }
    }

    fun stop() {
        timerDisposable?.dispose()
    }

    private fun computeClockUpdate(millis: Long, options: AnalogClockOptions): AnalogClockUpdate {

        val df = dateFormatter.formatMillis(millis)

        val degreesPerHour = 360.div(options.hourFormat.amountHours())

        val angleHours = degreesPerHour.times(df.hour)
            .plus(degreesPerHour.div(60f).times(df.minutes).toInt())
            .let(::Degree)

        val angleMinutes = 360.div(60).times(df.minutes).let(::Degree)
        val angleSeconds = 360.div(60).times(df.seconds).let(::Degree)

        return AnalogClockUpdate(
            angleHours = angleHours,
            angleMinutes = angleMinutes,
            angleSeconds = angleSeconds,
        )
    }

    fun installView(view: AnalogClockViewDefinition) {
        views.add(view)
    }

    fun removeView(view: AnalogClockViewDefinition) {
        views.remove(view)
    }
}