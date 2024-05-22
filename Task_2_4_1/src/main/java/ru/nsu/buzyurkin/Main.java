package ru.nsu.buzyurkin;

import ru.nsu.buzyurkin.git.Report;
import ru.nsu.buzyurkin.model.Config;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.configureFromFile(new File("src/main/resources/config.groovy"));

        Report report = new Report(config);
        report.printReport();
    }
}
