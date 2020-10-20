package at.shockbytes.clock.android

import android.content.Context
import android.graphics.Paint
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import at.shockbytes.clock.R
import at.shockbytes.clock.core.data.Percentage

class AnalogClockViewTheme private constructor(
    private val context: Context,
    @ColorRes private val borderBackgroundRes: Int,
    @ColorRes private val backgroundRes: Int,
    @DimenRes private val borderWidthDimen: Int,
    @ColorRes private val secondsPointerColorRes: Int,
    @ColorRes private val mainPointerColorRes: Int,
    @DimenRes private val mainPointerWidthDimen: Int,
    @DimenRes private val secondsPointerWidthDimen: Int,
    @DimenRes private val mainPointerOverflowLengthDimen: Int,
    @DimenRes private val secondsPointerOverflowLengthDimen: Int,
    @DimenRes private val decorationTickLongDimen: Int,
    @DimenRes private val decorationTickShortDimen: Int,
    val hourPointerLength: Percentage,
    val minutePointerLength: Percentage,
    val secondsPointerLength: Percentage,
    val drawSeconds: Boolean,
    val drawHourDecoration: Boolean,
) {

    val backgroundPaint: Paint = Paint().apply {
        color = ContextCompat.getColor(context, borderBackgroundRes)
        isAntiAlias = true
    }

    val decorationPaint = Paint().apply {
        color = ContextCompat.getColor(context, borderBackgroundRes)
        isAntiAlias = true
        strokeWidth = context.resources.getDimension(secondsPointerWidthDimen)
    }

    val bgClearPaint = Paint().apply {
        color = ContextCompat.getColor(context, backgroundRes)
        isAntiAlias = true
    }

    val secondPointerPaint = Paint().apply {
        color = ContextCompat.getColor(context, secondsPointerColorRes)
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeWidth = context.resources.getDimension(secondsPointerWidthDimen)
    }

    val mainPointerPaint = Paint().apply {
        color = ContextCompat.getColor(context, mainPointerColorRes)
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeWidth = context.resources.getDimension(mainPointerWidthDimen)
    }

    val borderWidth = context.resources.getDimension(borderWidthDimen)
    val mainOverflowLength = context.resources.getDimension(mainPointerOverflowLengthDimen)
    val secondsOverflowLength = context.resources.getDimension(secondsPointerOverflowLengthDimen)

    val decorationTickShort = context.resources.getDimension(decorationTickShortDimen)
    val decorationTickLong = context.resources.getDimension(decorationTickLongDimen)


    companion object {

        fun default(context: Context): AnalogClockViewTheme {
            return custom(context = context)
        }

        fun custom(
            context: Context,
            @ColorRes borderBackgroundRes: Int = R.color.border_color,
            @ColorRes backgroundRes: Int = R.color.background_color,
            @DimenRes borderWidthDimen: Int = R.dimen.border_width,
            @ColorRes secondsPointerColorRes: Int = R.color.second_pointer_color,
            @ColorRes mainPointerColorRes: Int = R.color.main_pointer_color,
            @DimenRes mainPointerWidthDimen: Int = R.dimen.main_pointer_width,
            @DimenRes secondsPointerWidthDimen: Int =  R.dimen.seconds_pointer_width,
            @DimenRes mainPointerOverflowLengthDimen: Int = R.dimen.main_pointer_overflow_width,
            @DimenRes secondsPointerOverflowLengthDimen: Int = R.dimen.seconds_pointer_overflow_width,
            @DimenRes decorationTickLongDimen: Int = R.dimen.decoration_tick_long,
            @DimenRes decorationTickShortDimen: Int = R.dimen.decoration_tick_short,
            hourPointerLength: Percentage = Percentage(0.45f),
            minutePointerLength: Percentage = Percentage(0.7f),
            secondsPointerLength: Percentage = Percentage(0.85f),
            drawSeconds: Boolean = true,
            drawHourDecoration: Boolean = true,
        ): AnalogClockViewTheme {
            return AnalogClockViewTheme(
                context = context,
                borderBackgroundRes = borderBackgroundRes,
                backgroundRes = backgroundRes,
                borderWidthDimen = borderWidthDimen,
                mainPointerColorRes = mainPointerColorRes,
                secondsPointerColorRes = secondsPointerColorRes,
                hourPointerLength = hourPointerLength,
                minutePointerLength = minutePointerLength,
                secondsPointerLength = secondsPointerLength,
                drawSeconds = drawSeconds,
                drawHourDecoration = drawHourDecoration,
                mainPointerWidthDimen = mainPointerWidthDimen,
                secondsPointerWidthDimen = secondsPointerWidthDimen,
                mainPointerOverflowLengthDimen = mainPointerOverflowLengthDimen,
                secondsPointerOverflowLengthDimen = secondsPointerOverflowLengthDimen,
                decorationTickLongDimen = decorationTickLongDimen,
                decorationTickShortDimen = decorationTickShortDimen,
            )
        }
    }
}