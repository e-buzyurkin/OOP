package ru.nsu.buzyurkin;

/**
 * The Grade enum represents different grades with corresponding numeric values.
 * Each grade has an associated integer value that indicates its level.
 */
public enum Grade {
    A(5),
    B(4),
    C(3),
    D(2);

    private int grade;

    /**
     * Constructs a Grade enum with the specified numeric value.
     *
     * @param grade The numeric value associated with the grade.
     */
    Grade(int grade) {
        this.grade = grade;
    }

    /**
     * Gets the numeric value associated with the grade.
     *
     * @return The numeric value of the grade.
     */
    public int getGrade() {
        return grade;
    }
}
