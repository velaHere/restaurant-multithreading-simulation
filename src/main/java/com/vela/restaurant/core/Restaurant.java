package com.vela.restaurant.core;

import com.vela.restaurant.people.Customer;
import com.vela.restaurant.queue.OrderQueue;
import com.vela.restaurant.queue.WaiterTasksQueue;
import com.vela.restaurant.people.Chef;
import com.vela.restaurant.people.Waiter;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    public final String NAME;
    private boolean isOpen;
    private final List<Waiter> waiters = new ArrayList<>();
    private final List<Chef> chefs = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final OrderQueue orderQueue;
    private final WaiterTasksQueue tasksQueue;
    private final Object waiterLock;
    public final long START_TIME;

    public Restaurant(String name){
        this.NAME=name;
        this.START_TIME=System.currentTimeMillis();
        this.orderQueue = new OrderQueue(this);
        this.waiterLock=new Object();
        this.tasksQueue = new WaiterTasksQueue(waiterLock, orderQueue);
        this.isOpen=true;
    }

    public void addCustomer(){
        Customer customer;
        synchronized(this){
            int i =this.customers.size();
            customer = new Customer("customer"+i, "dish"+i, 10+2*i);
            this.customers.add(customer);
        }
        this.tasksQueue.addTakeOrderFromCustomerTask(customer);
    }

    public synchronized void addWaiter(){
        int i = this.waiters.size();
        Waiter waiter = new Waiter("waiter"+i, this.tasksQueue);
        this.waiters.add(waiter);
        waiter.start();
    }

    public synchronized void addChef(){
        int i = this.chefs.size();
        Chef chef = new Chef("chef"+i, this.orderQueue, this.tasksQueue);
        this.chefs.add(chef);
        chef.start();
    }

    public void status(){
        synchronized (this){
            System.out.println("Customers: " + this.customers.size());
            System.out.println("Waiters: " + this.waiters.size());
            System.out.println("Chefs: " + this.chefs.size());
        }
    }

    public void close(){
        this.isOpen=false;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
