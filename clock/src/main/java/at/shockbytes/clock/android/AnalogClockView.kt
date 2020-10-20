package at.shockbytes.clock.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withRotation
import at.shockbytes.clock.core.*
import at.shockbytes.clock.core.AnalogClockUpdate
import at.shockbytes.clock.core.data.toRadian
import at.shockbytes.clock.rx.AndroidSchedulersFacade
import kotlin.math.*

/**
 * Author:  Martin Macheiner
 * Date:    17.10.2020
 */
class AnalogClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs), AnalogClockViewDefinition {

    /**
     * For convenience purposes I just put the engine in the [AnalogClockViewDefinition]
     * implementation. The engine itself is platform-independent but the dependencies are
     * android-specific. Putting everything in one class makes the usage more compact and easier
     * to use.
     */
    private var engine: AnalogClockViewEngine = AnalogClockViewEngine(
        dateFormatter = JodaTimeDateFormatter,
        schedulers = AndroidSchedulersFacade,
        millisProvider = System::currentTimeMillis
    ).apply {
        installView(this@AnalogClockView)
    }

    var options: AnalogClockOptions = AnalogClockOptions.default()
        set(value) {
            field = value
            engine.options = value
        }

    var theme: AnalogClockViewTheme = AnalogClockViewTheme.default(context)

    fun start() = engine.start()

    fun stop() = engine.stop()

    private lateinit var tickUpdate: AnalogClockUpdate

    override fun update(update: AnalogClockUpdate) {
        this.tickUpdate = update
        invalidate()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (::tickUpdate.isInitialized && canvas != null) {

            drawBackground(canvas)

            if (theme.drawHourDecoration) {
                drawHourDecoration(canvas)
            }

            drawHours(canvas)
            drawMinutes(canvas)

            if (theme.drawSeconds) {
                drawSeconds(canvas)
            }
        }
    }

    private fun drawBackground(canvas: Canvas) {

        val center = center()
        val radius = radius()

        canvas.drawCircle(center.x, center.y, radius, theme.backgroundPaint)
        canvas.drawCircle(center.x, center.y, radius - theme.borderWidth, theme.bgClearPaint)
    }

    private fun drawHourDecoration(canvas: Canvas) {

        val center = center()

        for (i in 0..360 step 30) {

            val tickHeight =
                if (i % 90 == 0) theme.decorationTickLong else theme.decorationTickShort

            canvas.withRotation(i.toFloat(), pivotX = center.x, pivotY = center.y) {
                canvas.drawLine(
                    center.x,
                    height.toFloat(),
                    center.x,
                    height - tickHeight,
                    theme.decorationPaint
                )
            }
        }
    }

    private fun drawHours(canvas: Canvas) {
        if (tickUpdate.angleHours.value != -1) {
            canvas.drawPointer(
                theme.mainPointerPaint,
                theme.hourPointerLength.percentage,
                tickUpdate.angleHours.value,
                theme.mainOverflowLength
            )
        }
    }

    private fun drawMinutes(canvas: Canvas) {
        if (tickUpdate.angleMinutes.value != -1) {
            canvas.drawPointer(
                theme.mainPointerPaint,
                theme.minutePointerLength.percentage,
                tickUpdate.angleMinutes.value,
                theme.mainOverflowLength
            )
        }
    }

    private fun drawSeconds(canvas: Canvas) {

        if (tickUpdate.angleSeconds.value != -1) {
            canvas.drawPointer(
                theme.secondPointerPaint,
                theme.secondsPointerLength.percentage,
                tickUpdate.angleSeconds.value,
                theme.secondsOverflowLength
            )
        }
    }

    private fun Canvas.drawPointer(
        paint: Paint,
        pointerLengthPercentage: Float,
        angle: Int,
        overflowLength: Float
    ) {

        val center = center()

        val cEnd = pointerLengthPercentage * radius()
        val alphaEnd = angle - 90f
        val end = computeEndPoint(center, cEnd, alphaEnd)

        val alphaStart = angle + 90f
        val start = computeEndPoint(center, overflowLength, alphaStart)

        drawLine(start.x, start.y, end.x, end.y, paint)
    }

    private fun computeEndPoint(
        center: PointF,
        c: Float,
        alpha: Float
    ): PointF {

        val b = sin(alpha.toRadian()) * c
        val a = cos(alpha.toRadian()) * c

        val endX = center.x + a
        val endY = center.y + b

        return PointF(endX.toFloat(), endY.toFloat())
    }

    private fun center(): PointF {
        return PointF(width.div(2f), height.div(2f))
    }

    private fun radius(): Float {
        return max(height, width).div(2f)
    }
}