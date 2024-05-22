package ru.nsu.buzyurkin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Student extends GroovyConfigurable {
    private String id;
    private String name;
    private String repoURL;
}
