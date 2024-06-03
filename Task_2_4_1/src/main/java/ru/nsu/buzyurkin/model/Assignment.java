package ru.nsu.buzyurkin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Assignment class represents an assignment for a student,
 * including the student ID, task ID, and branch.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Assignment extends GroovyConfigurable {
    private String studentId;
    private String taskId;
    private String branch = "master";
}
