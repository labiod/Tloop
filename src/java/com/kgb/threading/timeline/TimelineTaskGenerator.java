package com.kgb.threading.timeline;

import com.kgb.threading.tasks.Task;
import com.kgb.threading.tasks.TaskFactory;
import com.kgb.threading.tasks.TaskInfo;
import com.kgb.threading.timeline.Timeline;

import java.io.*;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/15/17.
 */
class TimelineTaskGenerator {
    static void generateConstTask(Timeline timeline) {
        try {
            File file = new File("src/res/taskdb.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                while ((line = reader.readLine()) != null) {
                    TaskInfo info = new TaskInfo(timeline, line);

                    Task task = TaskFactory.create(info);
                    timeline.getTaskManager().addTask(task);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
