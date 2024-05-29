package ru.nsu.buzyurkin;

import java.io.File;
import ru.nsu.buzyurkin.git.Report;
import ru.nsu.buzyurkin.model.Config;


/**
 * The Main class is the entry point of the application. It loads a configuration
 * from a Groovy file and generates a report based on that configuration.
 */
public class Main {
    /**
     * The main method is the entry point of the application.
     * It loads a configuration from a Groovy file and generates a report based on that configuration.
     *
     * @param args Command line arguments (not used).
     * @throws Exception If an error occurs during configuration loading or report generation.
     */
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.configureFromFile(new File("src/main/resources/config.groovy"));

        Report report = new Report(config);
        report.printReport();
    }
}
