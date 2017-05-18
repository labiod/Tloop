package com.kgb.threading.tasks;

import java.util.Date;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/15/17.
 */
public abstract class Task {
    private String debugName;
    private Date startDate;

    Task(String debugName, Date startDate) {
        this.debugName = debugName;
        this.startDate = startDate;
    }

    public abstract Runnable getTaskAction();

    public Date getStartDate() {
        return startDate;
    }

    public String getTaskName() {
        return debugName;
    }
}
