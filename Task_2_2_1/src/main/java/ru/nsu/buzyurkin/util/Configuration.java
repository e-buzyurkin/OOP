package ru.nsu.buzyurkin.util;

import java.util.List;

/** Configuration for a simulation. Holds simulation parameters. */
public class Configuration {
    public int storageCapacity;
    public List<Integer> bakers; // Baking times (minutes) per baker
    public List<Integer> couriers; // Delivery times (minutes) per courier
    public int minTime;
    public int maxTime;
    public int ordersCount;
}
