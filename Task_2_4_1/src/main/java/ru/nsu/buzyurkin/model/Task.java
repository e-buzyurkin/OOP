package ru.nsu.buzyurkin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Task extends GroovyConfigurable {
    private String id;
    private String name;
    private float score;
    private boolean runTests;
}
