package at.shockbytes.clock.core

/**
 * Interface for the view. This makes the view implementation platform independent.
 */
interface AnalogClockViewDefinition {

    fun update(update: AnalogClockUpdate)
}