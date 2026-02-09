package com.vela.restaurant.core;

import java.util.Scanner;

public class ConsoleController extends Thread{

    private final Restaurant restaurant;

    public ConsoleController(){
        this.restaurant=new Restaurant("JavaCafe");
        this.setName("Console-Thread");
    }

    @Override
    public void run(){
        Scanner scanner = new Scanner(System.in);

        while(restaurant.isOpen()){
            System.out.print(restaurant.NAME+"> ");
            String input = scanner.nextLine().trim().toLowerCase();
            ConsoleCommand command = ConsoleCommand.getCommand(input);

            switch (command){
                case ADD_CUSTOMER -> this.restaurant.addCustomer();
                case ADD_WAITER -> this.restaurant.addWaiter();
                case ADD_CHEF -> this.restaurant.addChef();
                case STATUS -> this.restaurant.status();
                case CLOSE -> this.restaurant.close();
                case HELP -> this.printHelp();
            }
        }
    }

    private void printHelp() {
        System.out.println("""
                Commands:
                 > add customer
                 > add waiter
                 > add chef
                 > status
                 > close
                 > help
                """);
    }
}
