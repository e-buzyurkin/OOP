package ru.nsu.buzyurkin.result;

import java.util.List;
import ru.nsu.buzyurkin.model.Student;

/**
 * The StudentResult record holds the result of evaluating a student's performance.
 *
 * @param student the student whose performance is evaluated.
 * @param evaluated a flag indicating whether the student's work was successfully evaluated.
 * @param taskResults the list of results for individual tasks.
 * @param score the total score obtained by the student.
 */
public record StudentResult(
        Student student,
        boolean evaluated,
        List<TaskResult> taskResults,
        float score
) {}
