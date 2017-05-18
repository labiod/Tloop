package com.kgb.threading.tasks;

import com.kgb.threading.ThreadClock;
import com.kgb.threading.timeline.Timeline;

import java.util.Date;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/15/17.
 */
class JumpTasks extends Task {
    private final Date jumpDate;

    JumpTasks(String debugName, Date startDate, Date jumpDate) {
        super(debugName, startDate);
        this.jumpDate = jumpDate;
    }

    @Override
    public Runnable getTaskAction() {
        return () -> {
            Timeline timeline = Timeline.currentTimeline();
            System.out.println(String.format("***Timeline: %s, Task : '%s' - Jump to date: %s",
                    timeline.getTimelineName(), getTaskName(), jumpDate));

            ThreadClock clock = timeline.getClock();
            clock.setDate(jumpDate);
        };
    }
}
