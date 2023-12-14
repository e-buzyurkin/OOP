package ru.nsu.buzyurkin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The RecordBook class represents a record of academic performance,
 *          storing information about semesters,
 *          grades, and diploma-related details.
 */
public class RecordBook {
    private Map<Integer, Semester> recordBook;

    /**
     * Constructs a new RecordBook with an empty map to store semesters.
     */
    public RecordBook() {
        this.recordBook = new HashMap<>();
    }

    /**
     * Adds a semester to the record book.
     *
     * @param semester The Semester object representing
     *                 the academic performance for a specific semester.
     */
    public void addSemester(Semester semester) {
        recordBook.put(recordBook.size() + 1, semester);
    }

    private Grade diplomaGrade;

    /**
     * Sets the grade for the diploma.
     *
     * @param diplomaGrade The Grade object representing the grade achieved for the diploma.
     */
    public void setDiplomaGrade(Grade diplomaGrade) {
        this.diplomaGrade = diplomaGrade;
    }

    /**
     * Gets the grade for the diploma.
     *
     * @return The Grade object representing the grade achieved for the diploma.
     */
    public Grade getDiplomaGrade() {
        return this.diplomaGrade;
    }

    /**
     * Calculates the all-time Grade Point Average (GPA) based on the grades from all semesters.
     *
     * @return The all-time GPA as a double value.
     */
    public double allTimeGPA() {
        int subjectsCount = 0;
        int gradesSummary = 0;

        for (Semester semester : recordBook.values()) {
            subjectsCount += semester.semester.size();
            gradesSummary += semester.semester.values().stream().mapToInt(Grade::getGrade).sum();
        }

        return (double) gradesSummary / subjectsCount;
    }

    /**
     * Checks if a stipend is possible based on the grades of the latest semester.
     *
     * @return true if a stipend is possible, false otherwise.
     */
    public boolean stipendPossible() {
        return recordBook.get(recordBook.size()).stipendAcquired();
    }

    /**
     * Checks if the academic performance qualifies for an honors degree.
     *
     * @return true if an honors degree is possible, false otherwise.
     */
    public boolean honoursDegree() {
        if (diplomaGrade.getGrade() != Grade.A.getGrade()) {
            return false;
        }

        int semestersCount = recordBook.size();
        Set<String> subjectsCovered = new HashSet<>();
        int gradesSummary = 0;

        for (int i = semestersCount; i > 0; i--) {
            Semester curSemester = recordBook.get(i);

            for (String subject : curSemester.semester.keySet()){
                if (curSemester.semester.get(subject).getGrade() < Grade.B.getGrade()) {
                    return false;
                }

                if (subjectsCovered.contains(subject)) {
                    continue;
                }

                subjectsCovered.add(subject);
                gradesSummary += curSemester.semester.get(subject).getGrade();
            }
        }

        double diplomaAvgGrade = (double) gradesSummary / subjectsCovered.size();

        if (diplomaAvgGrade < 4.75) {
            return false;
        }

        return true;
    }
}
