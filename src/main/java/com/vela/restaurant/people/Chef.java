package com.vela.restaurant.people;

import com.vela.restaurant.queue.OrderQueue;
import com.vela.restaurant.queue.WaiterTasksQueue;
import org.jetbrains.annotations.NotNull;

public final class Chef extends Person {

    private final @NotNull OrderQueue orderQueue;
    private final @NotNull WaiterTasksQueue waiterTasksQueue;

    public Chef(@NotNull String name, @NotNull OrderQueue queue,@NotNull WaiterTasksQueue waiterTasksQueue) {
        super(name);
        this.orderQueue=queue;
        this.waiterTasksQueue=waiterTasksQueue;
    }

    @Override
    public void run(){
        while(isWorking){
            this.orderQueue.takeOrder(waiterTasksQueue);
        }
    }
}
