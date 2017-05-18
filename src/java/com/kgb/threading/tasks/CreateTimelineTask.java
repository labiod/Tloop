package com.kgb.threading.tasks;

import com.kgb.threading.timeline.TimelineRepo;

import java.util.Date;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/17/17.
 */
public class CreateTimelineTask extends Task {
    private final String mTimelineName;

    public CreateTimelineTask(String taskName, Date startDate, String timelineName) {
        super(taskName, startDate);
        mTimelineName = timelineName;
    }


    @Override
    public Runnable getTaskAction() {
        return () -> {
            TimelineRepo.INSTANACE.createTimelineFromName(mTimelineName);
        };
    }
}
