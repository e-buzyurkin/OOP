package ru.nsu.buzyurkin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Student class represents a student with an ID, name, and repository URL.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends GroovyConfigurable {
    private String id;
    private String name;
    private String repoURL;
}
