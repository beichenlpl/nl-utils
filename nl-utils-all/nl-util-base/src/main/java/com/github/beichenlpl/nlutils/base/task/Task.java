package com.github.beichenlpl.nlutils.base.task;

/**
 * @author beichenlpl
 * @since 2023/09/05
 */
public abstract class Task {

    private String taskId;

    public abstract void run();

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
