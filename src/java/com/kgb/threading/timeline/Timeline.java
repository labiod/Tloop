package com.kgb.threading.timeline;

import com.kgb.threading.TLThread;
import com.kgb.threading.ThreadClock;
import com.kgb.threading.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/16/17.
 */
public class Timeline implements Runnable {
    class TimelineTaskManager {
        public void addTask(Task taskAction) {
            synchronized (mLock) {
                mTaskToAdd.add(taskAction);
            }
        }

        public void addTasks(List<Task> tasks) {
            synchronized (mLock) {
                mTaskToAdd.addAll(tasks);
            }
        }

    }

    public static final String[] AVAILABLE_TIMELINES_NAMES = {"First", "Backup", "Need more time"};

    static Timeline createNewTimeline(String debugName, ThreadGroup threadGroup) {
        return new Timeline(debugName, threadGroup);
    }

    public static Timeline currentTimeline() {
        Thread thread = Thread.currentThread();
        return thread instanceof TLThread ? ((TLThread) thread).getThreadTimeline() : TimelineRepo.INSTANACE.getActiveTimeline();
    }

    private static long getTimeShift() {
        Thread thread = Thread.currentThread();
        if (thread instanceof TLThread) {
            long timeShift = ((TLThread) thread).getThreadTimeline().getClock().getTimePhaseShift();
            return (long) (timeShift -Math.PI/2);
        }
        return 0;
    }

    private boolean mActive;

    private List<Task> mTaskToRun = new ArrayList<>();
    private List<Task> mTaskToAdd = new ArrayList<>();
    private List<Task> mTaskToRemove = new ArrayList<>();
    private boolean finished = false;
    private TLThread mThread;
    private ThreadClock mClock;
    private String mDebugName;
    private final Object mLock = new Object();

    private Timeline(String debugName, ThreadGroup threadGroup) {
        this.mDebugName = debugName;
        mThread = new TLThread(debugName, threadGroup, this);
        mClock = new ThreadClock(Timeline.getTimeShift());
        mActive = true;
        TimelineTaskGenerator.generateConstTask(this);
        mThread.start();
    }

    public ThreadClock getClock() {
        return mClock;
    }

    public void finishThread() {
        finished = true;
    }

    @Override
    public void run() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (!finished) {
                if (mTaskToRun.size() > 0) {
                    Date currDate = new Date(mClock.currentTimeMillis());
                    System.out.println(String.format("Timeline '%s' - date: %s", mDebugName, dateFormat.format(currDate)));
                    for (Task task : mTaskToRun) {
                        if (task.getStartDate().getTime() / 1000 == mClock.currentTimeMillis() / 1000) {
                            task.getTaskAction().run();
                            mTaskToRemove.add(task);
                        }
                    }
                }
                if (mTaskToRemove.size() > 0) {
                    synchronized (mLock) {
                        mTaskToRun.removeAll(mTaskToRemove);
                        mTaskToRemove.clear();
                    }
                }
                if (mTaskToAdd.size() > 0) {
                    synchronized (mLock) {
                        mTaskToRun.addAll(mTaskToAdd);
                        mTaskToAdd.clear();
                    }
                }
                Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void setActive(boolean active) {
        mActive = active;
    }

    public boolean isActive() {
        return mActive;
    }

    public String getTimelineName() {
        return mDebugName;
    }

    TimelineTaskManager getTaskManager() {
        return new TimelineTaskManager();
    }
}
