package com.vela.restaurant.order;

import com.vela.restaurant.people.Customer;
import org.jetbrains.annotations.NotNull;

public record Order(@NotNull Customer customer, @NotNull String item, int time) {

}
