package com.kgb.threading.tasks;

import com.kgb.threading.ThreadClock;
import com.kgb.threading.timeline.Timeline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/17/17.
 */
public class TaskInfo {
    enum TaskType {
        JUMP,
        NEWTIMELINE,
        CLEAN;
    }

    private static final String TASK_INFOS_SPLITER = ";";

    private final TaskType mTaskType;
    private final String mTaskName;
    private final Date mStartDate;
    private Map<String, Object> mAdditionalData = new HashMap<>();

    public TaskInfo(Timeline timeline, String info) {
        String[] taskInfo = info.split(TASK_INFOS_SPLITER);
        mTaskType = TaskType.valueOf(taskInfo[0].toUpperCase());
        mTaskName = taskInfo[1].replace("\"", "");
        mStartDate = createDate(timeline.getClock(), taskInfo[2].replace("\"", ""));
        createAdditionalData(timeline, taskInfo);
    }

    TaskType getTaskType() {
        return mTaskType;
    }

    public String getTaskName() {
        return mTaskName;
    }

    private void createAdditionalData(Timeline timeline, String[] taskInfo) {
        switch (mTaskType) {
            case JUMP:
                mAdditionalData.put("jump_date", createDate(timeline.getClock(), taskInfo[3].replace("\"", "")));
                break;
            case NEWTIMELINE:
                mAdditionalData.put("timeline_name", getTimelineName(timeline, taskInfo[3]));
            case CLEAN:
                break;
        }
    }

    private String getTimelineName(Timeline currTimeline, String timelineName) {
        if (timelineName.startsWith("%next")) {
            String lastName = currTimeline.getTimelineName();
            int i = 0;
            for (String name : Timeline.AVAILABLE_TIMELINES_NAMES) {
                if (name.equals(lastName)) {
                    break;
                }
                ++i;
            }
            ++i;
            if (i >= Timeline.AVAILABLE_TIMELINES_NAMES.length) {
                i = 0;
            }
            return Timeline.AVAILABLE_TIMELINES_NAMES[i];
        } else {
            return timelineName.replace("\"", "");
        }
    }

    private Date createDate(ThreadClock clock, String dateString) {
        Date result = new Date();
        if (isInt(dateString)) {
            result = new Date(clock.currentTimeMillis() + Integer.parseInt(dateString) * 1000);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                result = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch(NumberFormatException er) {
            return false;
        }
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Map<String, Object> getAdditionalData() {
        return mAdditionalData;
    }
}
