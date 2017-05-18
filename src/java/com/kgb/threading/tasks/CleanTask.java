package com.kgb.threading.tasks;

import com.kgb.threading.timeline.Timeline;
import com.kgb.threading.timeline.TimelineRepo;

import java.util.Date;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/17/17.
 */
public class CleanTask extends Task {
    public CleanTask(String taskName, Date startDate) {
        super(taskName, startDate);
    }

    @Override
    public Runnable getTaskAction() {
        return () -> {
            System.out.println(String.format("Clean task '%s' on timeline : %s",
                     getTaskName(), Timeline.currentTimeline().getTimelineName()));
            TimelineRepo.INSTANACE.cleanAndStopTimeline(Timeline.currentTimeline());
        };
    }
}
