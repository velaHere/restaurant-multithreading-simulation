package com.vela.restaurant.task;

import org.jetbrains.annotations.NotNull;

public class WaiterTask{

    private final @NotNull Runnable task;
    private final @NotNull TaskPriority priority;
    public final long SEQ;

    public WaiterTask(@NotNull Runnable task,@NotNull TaskPriority priority, long seqNo){
        this.task=task;
        this.priority=priority;
        this.SEQ=seqNo;
    }

    public void perform(){
        this.task.run();
    }

    @NotNull
    public TaskPriority getPriority(){
        return this.priority;
    }
}
