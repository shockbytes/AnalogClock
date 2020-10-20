package at.shockbytes.clock.rx

import io.reactivex.rxjava3.core.Scheduler

interface SchedulersFacade {

    fun ui(): Scheduler

    fun computation(): Scheduler
}