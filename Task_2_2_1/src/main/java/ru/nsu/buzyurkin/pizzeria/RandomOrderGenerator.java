package ru.nsu.buzyurkin.pizzeria;

import ru.nsu.buzyurkin.menu.MenuItem;
import ru.nsu.buzyurkin.menu.Order;
import ru.nsu.buzyurkin.menu.OrderMenu;
import ru.nsu.buzyurkin.menu.drink.Drink;
import ru.nsu.buzyurkin.menu.drink.DrinkMenu;
import ru.nsu.buzyurkin.menu.pizza.Pizza;
import ru.nsu.buzyurkin.menu.pizza.PizzaCrust;
import ru.nsu.buzyurkin.menu.pizza.PizzaMenu;
import ru.nsu.buzyurkin.menu.pizza.PizzaSize;
import ru.nsu.buzyurkin.menu.snack.Snack;
import ru.nsu.buzyurkin.menu.snack.SnackMenu;
import ru.nsu.buzyurkin.util.BlockingQueue;
import ru.nsu.buzyurkin.util.RandomEnum;
import ru.nsu.buzyurkin.util.StoppableThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a random order generator for the pizzeria, responsible for generating random orders.
 */
public class RandomOrderGenerator extends StoppableThread {
    private static Random RANDOM = new Random();
    private boolean isRunning = true;
    private int totalOrders = 0;
    private int maxOrderSize;

    private int minTime;
    private int maxTime;
    private int ordersCount;

    private BlockingQueue<Integer> orderQueue;
    private OrderTable orderTable;

    /**
     * Constructs a RandomOrderGenerator object with the specified parameters.
     *
     * @param minTime     The minimum time interval for generating orders.
     * @param maxTime     The maximum time interval for generating orders.
     * @param ordersCount The total number of orders to generate.
     * @param orderQueue  The order queue to add generated orders to.
     * @param orderTable  The order table to add generated orders to.
     * @param maxOrderSize The maximum size of an order.
     */
    public RandomOrderGenerator(int minTime, int maxTime, int ordersCount,
                                BlockingQueue<Integer> orderQueue,
                                OrderTable orderTable,
                                int maxOrderSize) {
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.ordersCount = ordersCount;
        this.orderQueue = orderQueue;
        this.orderTable = orderTable;
        this.maxOrderSize = maxOrderSize;
    }

    /**
     * Overrides the run method of StoppableThread to define
     * the behavior of the random order generator.
     */
    @Override
    public void run() {
        int id;

        while (isRunning) {
            try {
                Thread.sleep(RANDOM.nextInt(minTime, maxTime));
            } catch (InterruptedException e) {
                return;
            }

            id = RANDOM.nextInt();

            try {
                orderTable.add(id, getRandomOrder());
                orderQueue.push(id);
            } catch (InterruptedException e) {
                return;
            }
            totalOrders++;

            if (totalOrders == ordersCount) {
                requestStop();
            }

        }
    }

    /**
     * Checks if all orders have been finished generating.
     *
     * @return true if all orders have been generated, false otherwise.
     */
    public boolean allFinished() {
        return totalOrders == ordersCount;
    }

    private int getRandomOrderSize() {
        return RANDOM.nextInt(1, maxOrderSize);
    }

    private Order getRandomOrder() {
        int orderSize = getRandomOrderSize();

        List<MenuItem> order = new ArrayList<>();

        for (int i = 0; i < orderSize; i++) {
            order.add(getRandomMenuItem());
        }

        return new Order(order);
    }

    private MenuItem getRandomMenuItem() {
        OrderMenu item = RandomEnum.randomEnum(OrderMenu.class);
        switch (item) {
            case DRINK -> {
                DrinkMenu drink = RandomEnum.randomEnum(DrinkMenu.class);

                return new Drink(drink);
            }

            case PIZZA -> {
                PizzaMenu pizzaName = RandomEnum.randomEnum(PizzaMenu.class);
                PizzaSize pizzaSize = RandomEnum.randomEnum(PizzaSize.class);
                PizzaCrust pizzaCrust = RandomEnum.randomEnum(PizzaCrust.class);

                return new Pizza(pizzaName, pizzaSize, pizzaCrust);
            }

            case SNACK -> {
                SnackMenu snack = RandomEnum.randomEnum(SnackMenu.class);

                return new Snack(snack);
            }

            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public void requestStop() {
        isRunning = false;
        interrupt();
    }

}
