package com.github.beichenlpl.entity;

import com.github.beichenlpl.nlutils.base.task.Task;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.29
 */
public class TaskA extends Task {
    @Override
    public void run() throws Exception {
        Thread.sleep(1000);
        System.out.println("TaskA run");
    }
}
