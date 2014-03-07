/*
 */
package org.schemaanalyst.mutation.analysis.util;

import org.apache.commons.lang3.time.StopWatch;

import java.util.EnumMap;

/**
 * <p>
 * Wrapper for using Apache Commons StopWatch class.
 * </p>
 *
 * @author Chris J. Wright
 */
public class ExperimentTimer {

    /**
     * The categories of timing point.
     */
    public enum TimingPoint {

        /**
         * The total time taken
         */
        TOTAL_TIME,
        /**
         * The time for mutation
         */
        MUTATION_TIME,
        /**
         * The time to construct and execute CREATEs
         */
        CREATES_TIME,
        /**
         * The time to construct and execute DROPs
         */
        DROPS_TIME,
        /**
         * The time to construct and execute INSERTs
         */
        INSERTS_TIME,
        /**
         * The time executing in parallel
         */
        PARALLEL_TIME
    }

    /**
     * The state of a stopwatch.
     */
    private enum StopWatchState {

        RUNNING, STOPPED
    }
    private EnumMap<TimingPoint, StopWatch> map;
    private EnumMap<TimingPoint, StopWatchState> stateMap;

    /**
     * Default constructor.
     */
    public ExperimentTimer() {
        map = new EnumMap<>(TimingPoint.class);
        stateMap = new EnumMap<>(TimingPoint.class);
        for (TimingPoint point : TimingPoint.values()) {
            map.put(point, constructSuspendedStopWatch());
            stateMap.put(point, StopWatchState.STOPPED);
        }
    }

    /**
     * Start the timer for the given point.
     *
     * @param point
     */
    public void start(TimingPoint point) {
        StopWatch stopWatch = map.get(point);
        stopWatch.resume();
        stateMap.put(point, StopWatchState.RUNNING);
    }

    /**
     * Stop the timer for the given point.
     *
     * @param point
     */
    public void stop(TimingPoint point) {
        StopWatch stopWatch = map.get(point);
        stopWatch.suspend();
        stateMap.put(point, StopWatchState.STOPPED);
    }

    /**
     * Stop all timers.
     */
    public void stopAll() {
        for (TimingPoint point : TimingPoint.values()) {
            if (stateMap.get(point).equals(StopWatchState.RUNNING)) {
                stop(point);
            }
        }
    }

    /**
     * Finalise the times recorded by the timers. This must be called before
     * calling {@code getTime}.
     */
    public void finalise() {
        for (TimingPoint point : TimingPoint.values()) {
            if (stateMap.get(point).equals(StopWatchState.RUNNING)) {
                map.get(point).stop();
            }
        }
    }

    /**
     * Retrieve the time taken from the timer for a given point.
     *
     * @param point
     * @return The time taken
     */
    public long getTime(TimingPoint point) {
        return map.get(point).getTime();
    }

    /**
     * Construct a stop watch that has been started and suspended.
     *
     * @return The stop watch
     */
    private StopWatch constructSuspendedStopWatch() {
        StopWatch dropsStopwatch = new StopWatch();
        dropsStopwatch.start();
        dropsStopwatch.suspend();
        return dropsStopwatch;
    }
}
