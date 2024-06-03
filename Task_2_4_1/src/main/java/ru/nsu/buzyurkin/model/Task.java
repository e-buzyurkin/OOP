package ru.nsu.buzyurkin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Task class represents a task with an ID, name, score,
 * and a flag indicating whether to run tests.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends GroovyConfigurable {
    private String id;
    private String name;
    private float score;
    private boolean runTests;
}
