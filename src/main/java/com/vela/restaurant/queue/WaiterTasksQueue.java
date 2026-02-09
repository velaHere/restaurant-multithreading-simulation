package com.vela.restaurant.queue;

import com.vela.restaurant.people.Customer;
import com.vela.restaurant.order.Order;
import com.vela.restaurant.core.Restaurant;
import com.vela.restaurant.task.TaskPriority;
import com.vela.restaurant.task.WaiterTask;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.PriorityQueue;

public class WaiterTasksQueue {

    private final @NotNull Object waiterLock;
    private final PriorityQueue<WaiterTask> tasks =
            new PriorityQueue<>(Comparator.comparing(WaiterTask::getPriority)
                    .thenComparingLong(t -> t.SEQ));
    private final @NotNull OrderQueue orderQueue;
    private final @NotNull Restaurant restaurant;
    private long currentTaskNumber;

    public WaiterTasksQueue(@NotNull Object waiterLock,@NotNull OrderQueue orderQueue){
        this.waiterLock=waiterLock;
        this.orderQueue=orderQueue;
        this.restaurant=orderQueue.getRestaurant();
        currentTaskNumber=0;
    }

    public void addTakeOrderFromCustomerTask(@NotNull Customer customer){
        synchronized (waiterLock){
            this.tasks.add(new WaiterTask(()->{
                System.out.println(Thread.currentThread().getName()+
                        ": Placing order -> " + customer.getOrder().item() +
                        " at " + time());
                threadSleep();
                System.out.println(Thread.currentThread().getName()
                        +": Placed order for " + customer.getOrder().item() +
                        " at " + time());
                this.orderQueue.placeOrder(customer.getOrder());
            }, TaskPriority.LOW, this.currentTaskNumber));
            this.currentTaskNumber++;
            this.waiterLock.notifyAll();
        }
    }

    public void addGiveDishToCustomerTask(@NotNull Order dish){
        synchronized (waiterLock){
            this.tasks.add(new WaiterTask(()->{
                System.out.println(Thread.currentThread().getName()+
                        ": going to chef to get " + dish.item() +
                        " at " + time());
                threadSleep();
                System.out.println("Given " + dish.item() + " to " + dish.customer().getPersonName()+
                        " at " + time());
            }, TaskPriority.HIGH, this.currentTaskNumber));
            this.currentTaskNumber++;
            this.waiterLock.notifyAll();
        }
    }

    public void perform(){
        WaiterTask task = null;
        synchronized (waiterLock){
            try{
                while(this.tasks.isEmpty()) this.waiterLock.wait();
                task = this.tasks.poll();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        if(task==null) return;
        task.perform();
        // Here in the perform task I am doing this ->
        /*
            in the addTakeOrderFromCustomerTask() the waiter will be using 2 locks 1st for acquiring the task
            from the tasks queue and after that releasing that lock it acquires the OrderQueue lock to add
            order in the orders queue
         */
    }

    private static void threadSleep(){
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    private int time(){
        return  (int)(System.currentTimeMillis()-this.restaurant.START_TIME)/1000;
    }
}
