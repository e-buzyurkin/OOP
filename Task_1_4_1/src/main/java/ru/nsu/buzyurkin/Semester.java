package ru.nsu.buzyurkin;

import java.util.HashMap;
import java.util.Map;

/**
 * The Semester class represents a collection of subjects
 *          and their corresponding grades for a semester.
 * It provides methods to add subjects with grades and
 *          check if a stipend is acquired based on the grades.
 */
public class Semester {
    /**
     * A map to store subjects and their corresponding grades for the semester.
     */
    public Map<String, Grade> semester;

    /**
     * Constructs a new Semester object with an empty map.
     */
    public Semester() {
        this.semester = new HashMap<>();
    }

    /**
     * Adds a subject with its corresponding grade to the semester.
     *
     * @param subject The name of the subject.
     * @param grade   The Grade object representing the grade obtained in the subject.
     */
    public void addSubject(String subject, Grade grade) {
        semester.put(subject, grade);
    }

    /**
     * Checks if the grades in the semester are sufficient to acquire a stipend.
     *
     * @return true if the stipend is acquired
     *          (all grades are equal to or greater than the B grade),
     *         false otherwise.
     */
    public boolean stipendAcquired() {
        for (Grade grade : semester.values()) {
            if (grade.getGrade() < Grade.B.getGrade()) {
                return false;
            }
        }

        return true;
    }
}
