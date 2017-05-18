package com.kgb.threading.timeline;

import com.kgb.threading.TLThread;

import java.util.LinkedList;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/15/17.
 */
public class TimelineRepo {
    public static final TimelineRepo INSTANACE = new TimelineRepo();

    public static TLThread getCurrentThread() {
        return (TLThread) TLThread.currentThread();
    }

    private final ThreadGroup mTimelineGroup;
    private final LinkedList<Timeline> mTimelines = new LinkedList<>();

    private TimelineRepo() {
        mTimelineGroup = new ThreadGroup(Thread.currentThread().getThreadGroup(), "timeline_repo");
    }

    public void cleanAndStopTimeline(Timeline currTimeline) {
        for (Timeline timeline : mTimelines) {
            if (currTimeline == timeline) {
                timeline.setActive(false);
                timeline.finishThread();
            }
        }
        mTimelines.remove(currTimeline);
    }

    public Timeline getActiveTimeline() {
        if (mTimelines.size() == 0) {
            return createTimelineFromName(Timeline.AVAILABLE_TIMELINES_NAMES[0]);
        }
        return mTimelines.get(0);
    }

    public Timeline createTimelineFromName(String timelineName) {
        for (Timeline timeline : mTimelines) {
            if (timeline.getTimelineName().equals(timelineName)) {
                return setActiveTimeline(timelineName);
            }
        }
        Timeline timeline = Timeline.createNewTimeline(timelineName, mTimelineGroup);
        addAndActivate(timeline);
        return timeline;
    }

    public void addAndActivate(Timeline timeline) {
        if (mTimelines.size() > 0) {
            mTimelines.get(0).setActive(false);
        }
        mTimelines.addFirst(timeline);
    }

    private Timeline setActiveTimeline(String timelineName) {
        Timeline activeTimeline = null;
        for (Timeline timeline : mTimelines) {
            if (timeline.getTimelineName() == timelineName) {
                mTimelines.get(0).setActive(false);
                timeline.setActive(true);
                activeTimeline = timeline;
            }
        }
        if (activeTimeline != null) {
            mTimelines.addFirst(activeTimeline);
        }
        return activeTimeline;
    }
}
