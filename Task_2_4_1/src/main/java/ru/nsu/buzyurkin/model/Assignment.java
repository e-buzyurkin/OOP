package ru.nsu.buzyurkin.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class Assignment extends GroovyConfigurable {
    private String studentId;
    private String taskId;
    private String branch = "master";
}
