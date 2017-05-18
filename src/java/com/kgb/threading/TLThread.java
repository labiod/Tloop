package com.kgb.threading;

import com.kgb.threading.timeline.Timeline;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/15/17.
 */
public class TLThread extends Thread {

    private final Timeline mTimeline;

    public TLThread(String threadName, ThreadGroup threadGroup, Timeline timeline) {
        super(threadGroup, timeline, threadName);
        mTimeline = timeline;
    }

    public Timeline getThreadTimeline() {
        return mTimeline;
    }
}
