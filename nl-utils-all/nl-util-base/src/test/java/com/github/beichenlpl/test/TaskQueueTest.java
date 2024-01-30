package com.github.beichenlpl.test;

import com.github.beichenlpl.entity.TaskA;
import com.github.beichenlpl.entity.TaskB;
import com.github.beichenlpl.nlutils.base.task.TaskQueue;
import org.junit.Test;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.29
 */
public class TaskQueueTest {

    @Test
    public void test() throws Exception {
        TaskQueue taskQueue = new TaskQueue();
        TaskA taskA = new TaskA();
        TaskB taskB = new TaskB();
        taskQueue.add(taskA);
        taskQueue.add(taskB);

        taskQueue.runAll();
        System.out.println(taskA.getTaskCostTime());
        System.out.println(taskB.getTaskCostTime());
    }
}
