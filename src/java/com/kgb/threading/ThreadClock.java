package com.kgb.threading;

import java.util.Date;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/15/17.
 */
public class ThreadClock {
    private long threadTimeDiff = 0L;
    private final long mTimePhaseShift;

    public ThreadClock() {
        this(0L);
    }

    public ThreadClock(long initDiff) {
        threadTimeDiff = initDiff;
        mTimePhaseShift = 0L;
    }

    public void setDate(Date date) {
        setTime(date.getTime());
    }
    public void setTime(long newTime) {
        threadTimeDiff = newTime - System.currentTimeMillis();
    }

    public void addSeconds(long seconds) {
        threadTimeDiff += seconds;
    }

    public void clearClock() {
        threadTimeDiff = 0;
    }
    public long currentTimeMillis() {
        return System.currentTimeMillis() + threadTimeDiff;
    }

    public long getTimePhaseShift() {
        return mTimePhaseShift;
    }
}
