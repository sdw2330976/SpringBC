package com.sdw.soft.demo.jdk8;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author: shangyd
 * @create: 2019-03-08 14:32:02
 **/
public class StreamTest {

    private enum Status {
        OPEN, CLOSED,;
    }

    @Data
    @ToString
    static class Task {
        private final Status status;
        private final Integer points;
    }


    public static void main(String[] args) {
        final Collection<Task> tasks = Arrays.asList(
                new Task(Status.OPEN, 5),
                new Task(Status.OPEN, 13),
                new Task(Status.CLOSED, 8)
        );
        int sum = tasks.stream().parallel().filter(task -> task.status == Status.OPEN)
                .mapToInt(Task::getPoints).sum();
        System.out.println(sum);
    }

}
