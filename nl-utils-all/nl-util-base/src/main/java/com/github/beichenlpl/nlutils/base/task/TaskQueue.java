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
public class TaskQueue<E extends Task> extends ConcurrentLinkedQueue<E> {

    public void runAll() {
        for (E e : this) {
            e.run();
        }
    }

    public void runTask(String... taskIds) {
        for (String taskId : taskIds) {
            this.stream().filter(item -> item.getTaskId().equals(taskId)).forEach(Task::run);
        }
    }

    @Override
    public boolean add(E e) {
        if (BaseUtils.isNull(e) || BaseUtils.isNull(e.getTaskId())) {
            return false;
        }

        for (E el : this) {
            if (el.getTaskId().equals(e.getTaskId())) {
                return false;
            }
        }

        return super.add(e);
    }

    public boolean removeTask(E e) {
        if (BaseUtils.isNull(e) || BaseUtils.isNull(e.getTaskId())) {
            return false;
        }

        return removeIf(item -> item.getTaskId().equals(e.getTaskId()));
    }

    public E get(String taskId) {
        return this.stream().filter(item -> item.getTaskId().equals(taskId)).collect(Collectors.toList()).get(0);
    }

    public List<E> getAll() {
        return new ArrayList<>(this);
    }

    public List<String> getTaskIds() {
        List<String> taskIds = new ArrayList<>();
        for (E e : this) {
            taskIds.add(e.getTaskId());
        }
        return taskIds;
    }
}
