package com.vela.restaurant.core;

public enum ConsoleCommand {
    ADD_CUSTOMER("add customer"),
    ADD_WAITER("add waiter"),
    ADD_CHEF("add chef"),
    STATUS("status"),
    CLOSE("close"),
    HELP("help");

    private final String string;

    ConsoleCommand(String string){
        this.string=string;
    }

    @Override
    public String toString() {
        return this.string;
    }

    public static ConsoleCommand getCommand(String command){
        for (ConsoleCommand command1: ConsoleCommand.values()){
            if(command1.string.equals(command)) return command1;
        }
        return HELP;
    }
}
