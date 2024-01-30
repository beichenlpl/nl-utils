package com.github.beichenlpl.nlutils.base.task;

/**
 * @author beichenlpl
 * @since 2023/09/05
 */
public abstract class Task {

    private String taskId = this.getClass().getSimpleName() + "_" + System.currentTimeMillis();

    private long taskCostTime;

    public abstract void run() throws Exception;

    public final String getTaskId() {
        return taskId;
    }

    public final void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    protected final void setTaskCostTime(long taskCostTime) {
        this.taskCostTime = taskCostTime;
    }

    /**
     * 获取任务耗时, 单位纳秒
     * Obtaining task time, in nanoseconds
     */
    public final long getTaskCostTime() {
        return taskCostTime;
    }
}
