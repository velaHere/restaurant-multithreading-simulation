package com.vela.restaurant.people;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class Person extends Thread{

    protected final @NotNull String name;
    protected final @NotNull UUID uuid;
    protected volatile boolean isWorking;

    protected Person(@NotNull String name){
        super(name);
        this.name = name;
        this.uuid=UUID.randomUUID();
        this.isWorking=true;
    }

    @NotNull
    public String getPersonName(){
        return this.name;
    }
}
