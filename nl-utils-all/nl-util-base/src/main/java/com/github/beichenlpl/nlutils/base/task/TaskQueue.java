package com.github.beichenlpl.nlutils.base.task;

import com.github.beichenlpl.nlutils.base.common.BaseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * @author beichenlpl
 * @since 2023/09/05
 */
public class TaskQueue extends ConcurrentLinkedQueue<Task> {

    public void runAll() throws Exception {
        for (Task task : this) {
            runTaskCore(task.getTaskId());
        }
    }

    public void runTask(String taskId, String... taskIds) throws Exception {
        if (BaseUtils.isNotEmpty(taskId)) {
            runTaskCore(taskId);
        }

        if (BaseUtils.isNotEmpty(taskIds)){
            for (String id : taskIds) {
                runTaskCore(id);
            }
        }
    }

    private void runTaskCore(String taskId) throws Exception {
        for (Task task : this) {
            if (BaseUtils.equals(task.getTaskId(), taskId)) {
                long startTime = System.nanoTime();
                task.run();
                task.setTaskCostTime(System.nanoTime() - startTime);
            }
        }
    }

    @Override
    public boolean add(Task task) {
        if (BaseUtils.isNull(task)) {
            return false;
        }

        for (Task task1 : this) {
            if (task1.getTaskId().equals(task.getTaskId())) {
                return false;
            }
        }

        return super.add(task);
    }

    public boolean removeTask(String taskId) {
        if (BaseUtils.isEmpty(taskId)) {
            return false;
        }

        return removeIf(item -> BaseUtils.equals(item.getTaskId(), taskId));
    }

    public Task get(String taskId) {
        return this.stream().filter(item -> item.getTaskId().equals(taskId)).collect(Collectors.toList()).get(0);
    }

    public List<Task> getAll() {
        return new ArrayList<>(this);
    }

    public List<String> getTaskIds() {
        List<String> taskIds = new ArrayList<>();
        for (Task task : this) {
            taskIds.add(task.getTaskId());
        }
        return taskIds;
    }
}
