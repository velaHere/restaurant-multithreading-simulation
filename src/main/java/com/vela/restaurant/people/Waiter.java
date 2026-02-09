package com.vela.restaurant.people;

import com.vela.restaurant.queue.WaiterTasksQueue;
import org.jetbrains.annotations.NotNull;

public final class Waiter extends Person {

    private final @NotNull WaiterTasksQueue tasksQueue;

    public Waiter(@NotNull String name,@NotNull WaiterTasksQueue tasksQueue) {
        super(name);
        this.tasksQueue=tasksQueue;
    }

    @Override
    public void run(){
        while(this.isWorking){
            this.tasksQueue.perform();
        }
    }
}
