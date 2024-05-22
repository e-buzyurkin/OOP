package ru.nsu.buzyurkin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper=false)
@Data
public class Config extends GroovyConfigurable {
    private List<Task> tasks;
    private String groupName;
    private List<Student> students;
    private List<Assignment> assignments;
    private String repositoriesDirectory;
}
