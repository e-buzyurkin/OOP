package ru.nsu.buzyurkin.pizzeria;

import ru.nsu.buzyurkin.util.BlockingQueue;
import ru.nsu.buzyurkin.util.JsonConfigReader;

import java.util.List;
import java.util.logging.Logger;

/**
 * Represents the main class of the pizzeria simulation.
 */
public class Pizzeria {
    /**
     * Runs the pizzeria simulation with the specified configuration
     * file path and maximum order size.
     *
     * @param configFilepath The file path of the JSON configuration file.
     * @param maxOrderSize   The maximum size of an order.
     */
    public static void runPizzeria(String configFilepath, int maxOrderSize) {
        JsonConfigReader configReader = new JsonConfigReader(configFilepath);

        BlockingQueue<Integer> orderQueue =
                new BlockingQueue<>(configReader.getBakersCount() * 3);
        OrderTable orderTable = new OrderTable();

        Storage storage = new Storage(configReader.getStorageSize());

        Logger logger = Logger.getLogger(Pizzeria.class.getName());
        //            FileHandler fh = new ConsoleHandler();
//            fh.setFormatter(new SimpleFormatter());
//        logger.addHandler(new ConsoleHandler());
        logger.info("Starting pizzeria");


        RandomOrderGenerator orderGenerator =
                new RandomOrderGenerator(configReader.getMinTime(), configReader.getMaxTime(),
                configReader.getOrdersCount(), orderQueue, orderTable, maxOrderSize);

        List<Baker> bakers = configReader.getBakers(logger, orderTable, orderQueue, storage);
        List<Courier> couriers = configReader.getCouriers(storage, orderTable, logger);

        orderGenerator.start();

        for (var baker : bakers) {
            baker.start();
        }

        for (var courier : couriers) {
            courier.start();
        }

        while (!orderGenerator.allFinished() || !orderTable.allFinished()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (var baker : bakers) {
            baker.requestStop();

            try {
                baker.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (var courier : couriers) {
            courier.requestStop();

            try {
                courier.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        orderGenerator.requestStop();
        try {
            orderGenerator.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
