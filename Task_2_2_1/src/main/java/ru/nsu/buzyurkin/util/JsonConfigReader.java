package ru.nsu.buzyurkin.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import ru.nsu.buzyurkin.pizzeria.Baker;
import ru.nsu.buzyurkin.pizzeria.Courier;
import ru.nsu.buzyurkin.pizzeria.OrderTable;
import ru.nsu.buzyurkin.pizzeria.Storage;

/**
 * A class to read configuration data from a JSON file
 * and provide methods to access the configuration parameters.
 */
public class JsonConfigReader {
    private Configuration config;

    /**
     * Constructs a JsonConfigReader object by reading configuration from a JSON file.
     *
     * @param filename The name of the JSON file to read the configuration from.
     */
    public JsonConfigReader(String filename) {
        String json = readFromFile(filename);

        config = new Gson().fromJson(json, Configuration.class);
    }

    /**
     * Retrieves a list of bakers based on the configuration.
     *
     * @param logger     The logger to log messages.
     * @param orderTable The order table to handle orders.
     * @param orderQueue The blocking queue for orders.
     * @param storage    The storage for ingredients.
     * @return A list of Baker objects created based on the configuration.
     */
    public List<Baker> getBakers(Logger logger, OrderTable orderTable,
                                 BlockingQueue<Integer> orderQueue, Storage storage) {
        List<Baker> bakers = new ArrayList<>();

        int bakerId = 0;
        for (int exp : config.bakers) {
            bakers.add(new Baker(bakerId, exp, logger,
                    orderTable, orderQueue, storage));
            bakerId++;
        }

        return bakers;
    }

    /**
     * Retrieves the count of bakers specified in the configuration.
     *
     * @return The count of bakers.
     */
    public int getBakersCount() {
        return config.bakers.size();
    }

    /**
     * Retrieves a list of couriers based on the configuration.
     *
     * @param storage    The storage for orders.
     * @param orderTable The order table to handle orders.
     * @param logger     The logger to log messages.
     * @return A list of Courier objects created based on the configuration.
     */
    public List<Courier> getCouriers(Storage storage, OrderTable orderTable, Logger logger) {
        List<Courier> couriers = new ArrayList<>();

        int courierId = 0;
        for (int trunkSize : config.couriers) {
            couriers.add(new Courier(trunkSize, storage,
                    courierId, orderTable, logger));
            courierId++;
        }

        return couriers;
    }

    /**
     * Retrieves the capacity of the storage specified in the configuration.
     *
     * @return The capacity of the storage.
     */
    public int getStorageSize() {
        return config.storageCapacity;
    }

    /**
     * Retrieves the minimum time specified in the configuration.
     *
     * @return The minimum time.
     */
    public int getMinTime() {
        return config.minTime;
    }

    /**
     * Retrieves the maximum time specified in the configuration.
     *
     * @return The maximum time.
     */
    public int getMaxTime() {
        return config.maxTime;
    }

    /**
     * Retrieves the count of orders specified in the configuration.
     *
     * @return The count of orders.
     */
    public int getOrdersCount() {
        return config.ordersCount;
    }

    private String readFromFile(String filename) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        StringBuilder wholeFile = new StringBuilder();
        while (true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                break;
            }

            if (line == null) {
                break;
            }

            wholeFile.append(line);
        }

        return wholeFile.toString();
    }
}
