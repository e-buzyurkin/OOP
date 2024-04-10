package ru.nsu.buzyurkin.pizzeria;

import ru.nsu.buzyurkin.util.StoppableThread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a courier in a pizzeria, responsible for delivering pizza orders.
 */
public class Courier extends StoppableThread {
    private boolean isRunning = true;
    private static int DELIVERY_TIME = 2000;

    private int trunkSize;
    private Storage storage;
    private int courierId;
    private OrderTable orderTable;
    private Logger log;

    /**
     * Constructs a Courier object with the specified parameters.
     *
     * @param trunkSize   The size of the courier's trunk.
     * @param storage     The storage containing orders for delivery.
     * @param courierId   The ID of the courier.
     * @param orderTable  The order table to mark finished orders.
     * @param logger      The logger for logging messages.
     */
    public Courier(int trunkSize, Storage storage, int courierId,
                   OrderTable orderTable, Logger logger) {
        this.trunkSize = trunkSize;
        this.storage = storage;
        this.courierId = courierId;
        this.orderTable = orderTable;
        this.log = logger;
    }

    /**
     * Overrides the run method of StoppableThread to define the courier's behavior.
     */
    @Override
    public void run() {
        while (isRunning) {
            List<Integer> orders = new ArrayList<>();

            for (int i = 0; i < trunkSize; i++) {
                int order;
                try {
                    order = storage.take();
                } catch (InterruptedException e) {
                    return;
                }
                orders.add(order);

                log.info(String.format("[Courier#%d]: Took order %d", courierId, order));

                if (storage.isEmpty()) {
                    break;
                }
            }

            for (Integer order : orders) {
                try {
                    Thread.sleep(DELIVERY_TIME);
                } catch (InterruptedException e) {

                }

                orderTable.markFinished(order);
                log.info(String.format("[Courier#%d]: Delivered order %d",
                        courierId, order));
            }
        }
    }

    /**
     * Requests the courier to stop delivering orders.
     */
    @Override
    public void requestStop() {
        isRunning = false;
        interrupt();
    }
}
