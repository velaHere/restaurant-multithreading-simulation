package com.vela.restaurant.queue;

import com.vela.restaurant.order.Order;
import com.vela.restaurant.core.Restaurant;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {

    private final Queue<Order> orders = new LinkedList<>();
    private final @NotNull Restaurant restaurant;

    public OrderQueue(@NotNull Restaurant restaurant){
        this.restaurant=restaurant;
    }

    public synchronized void placeOrder(@NotNull Order order){
        this.orders.add(order);
        this.notifyAll();
    }

    public void takeOrder(WaiterTasksQueue waiterTasksQueue){
        Order order;
        synchronized (this){
            try{
                while(orders.isEmpty()) wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
            order = this.orders.poll();
        }

        if(order==null) return;
        System.out.println(Thread.currentThread().getName() +
                ": preparing " + order.item() + " for " + order.customer().getPersonName() +
                " at " + time());
        try{
            Thread.sleep(order.time() * 1000L);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ": prepared -> " + order.item() +
                " at " + time());
        waiterTasksQueue.addGiveDishToCustomerTask(order);
    }

    @NotNull
    public Restaurant getRestaurant(){
        return this.restaurant;
    }

    private int time(){
        return  (int)(System.currentTimeMillis()-this.restaurant.START_TIME)/1000;
    }
}
