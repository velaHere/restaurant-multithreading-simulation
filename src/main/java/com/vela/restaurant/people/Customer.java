package com.vela.restaurant.people;

import com.vela.restaurant.order.Order;
import org.jetbrains.annotations.NotNull;

public final class Customer extends Person {

    private final @NotNull Order order;

    public Customer(@NotNull String name, @NotNull String itemName, int time) {
        super(name);
        this.order=new Order(this, itemName, time);
    }

    @NotNull
    public Order getOrder(){
        return this.order;
    }

    @Override
    public void run(){

    }
}
