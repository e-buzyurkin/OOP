package ru.nsu.buzyurkin.result;

import ru.nsu.buzyurkin.model.Student;

import java.util.List;

public record StudentResult(
        Student student,
        boolean evaluated,
        List<TaskResult> taskResults,
        float score
) {}
