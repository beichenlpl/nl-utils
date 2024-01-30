package com.github.beichenlpl.entity;

import com.github.beichenlpl.nlutils.base.task.Task;

/**
 * @author lpl
 * @version 1.0
 * @since 2024.01.29
 */
public class TaskB extends Task {
    @Override
    public void run() {
        System.out.println("TaskB run");
    }
}
