package ru.nsu.buzyurkin.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The Config class holds the configuration details including tasks, group name, students, assignments,
 * and the directory for repositories.
 */
@EqualsAndHashCode(callSuper=false)
@Data
public class Config extends GroovyConfigurable {
    private List<Task> tasks;
    private String groupName;
    private List<Student> students;
    private List<Assignment> assignments;
    private String repositoriesDirectory;
}
