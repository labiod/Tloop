package com.kgb.threading.tasks;

import java.util.Date;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/17/17.
 */
public class TaskFactory {
    public static Task create(TaskInfo info) {
        switch (info.getTaskType()) {
            case JUMP:
                return new JumpTasks(info.getTaskName(), info.getStartDate(),
                        (Date) info.getAdditionalData().get("jump_date"));
            case NEWTIMELINE:
                return new CreateTimelineTask(info.getTaskName(), info.getStartDate(),
                        (String) info.getAdditionalData().get("timeline_name"));
            case CLEAN:
                return new CleanTask(info.getTaskName(), info.getStartDate());
        }
        return null;
    }
}
