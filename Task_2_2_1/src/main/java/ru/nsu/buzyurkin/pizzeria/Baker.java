package ru.nsu.buzyurkin.pizzeria;

import ru.nsu.buzyurkin.menu.MenuItem;
import ru.nsu.buzyurkin.menu.Order;
import ru.nsu.buzyurkin.util.BlockingQueue;
import ru.nsu.buzyurkin.util.StoppableThread;

import java.util.logging.Logger;

/**
 * Represents a baker in a pizzeria, responsible for baking pizza orders.
 */
public class Baker extends StoppableThread {
    private boolean isRunning = true;
    private static int DEFAULT_TIMETOMAKE = 2500;

    private int experience;
    private int bakerId;
    private BlockingQueue<Integer> orderQueue;
    private Storage storage;
    private OrderTable orderTable;
    private Logger log;

    /**
     * Constructs a Baker object with the specified parameters.
     *
     * @param bakerId    The ID of the baker.
     * @param experience The experience level of the baker.
     * @param logger     The logger for logging messages.
     * @param orderTable The order table to fetch orders.
     * @param orderQueue The order queue to get orders from.
     * @param storage    The storage for storing finished orders.
     */
    public Baker(int bakerId, int experience, Logger logger,
                 OrderTable orderTable,
                 BlockingQueue<Integer> orderQueue,
                 Storage storage) {
        this.bakerId = bakerId;
        this.experience = experience;
        this.log = logger;
        this.orderTable = orderTable;
        this.orderQueue = orderQueue;
        this.storage = storage;
    }

    /**
     * Overrides the run method of StoppableThread to define the baker's behavior.
     */
    @Override
    public void run() {
        while (isRunning) {
            int orderId;
            try {
                orderId = orderQueue.pop();
            } catch (InterruptedException e) {
                return;
            }

            log.info(String.format("[Baker#%d]: Took order %d", bakerId, orderId));

            try {
                Thread.sleep((long) getProductionTime(orderTable.getById(orderId)) * experience);
            } catch (InterruptedException e) {

            }

            try {
                storage.add(orderId);
            } catch (InterruptedException e) {
                return;
            }

            log.info(String.format("Baker#%d: Stored order %d", bakerId, orderId));
        }
    }

    /**
     * Calculates the production time for a given order.
     *
     * @param order The order for which production time needs to be calculated.
     * @return The production time in milliseconds.
     */
    private int getProductionTime(Order order) {
        return order.order().size() * 200;
    }

    /**
     * Requests the baker to stop processing orders.
     */
    @Override
    public void requestStop() {
        isRunning = false;
        interrupt();
    }
}
