package ru.nsu.buzyurkin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecordBookTests {
    private RecordBook recordBook;

    @BeforeEach
    public void createRecordBook() {
        recordBook = new RecordBook();
    }

    /*
        Cannot have Honours Degree because last 2 grades were B's not A's.
     */
    @Test
    public void testHonoursDegree1() {
        Semester sem1 = new Semester();
        sem1.addSubject("Calculus", Grade.A);
        sem1.addSubject("English", Grade.A);
        sem1.addSubject("Discrete Math", Grade.A);
        sem1.addSubject("Imperative Programming", Grade.A);

        Semester sem2 = new Semester();
        sem2.addSubject("Calculus", Grade.A);
        sem2.addSubject("English", Grade.A);
        sem2.addSubject("Discrete Math", Grade.B);
        sem2.addSubject("Imperative Programming", Grade.B);

        recordBook.addSemester(sem1);
        recordBook.addSemester(sem2);

        recordBook.setDiplomaGrade(Grade.A);

        assertFalse(recordBook.honoursDegree());
    }

    /*
        Cannot have Honours Degree because of C in the first semester.
     */
    @Test
    public void testHonoursDegree2() {
        Semester sem1 = new Semester();
        sem1.addSubject("Calculus", Grade.A);
        sem1.addSubject("English", Grade.C);
        sem1.addSubject("Discrete Math", Grade.A);
        sem1.addSubject("Imperative Programming", Grade.A);

        Semester sem2 = new Semester();
        sem2.addSubject("Calculus", Grade.A);
        sem2.addSubject("English", Grade.A);
        sem2.addSubject("Discrete Math", Grade.A);
        sem2.addSubject("Imperative Programming", Grade.A);

        recordBook.addSemester(sem1);
        recordBook.addSemester(sem2);

        recordBook.setDiplomaGrade(Grade.A);

        assertFalse(recordBook.honoursDegree());
    }

    /*
        Can have Honours Degree because last semester grades were A's.
     */
    @Test
    public void testHonoursDegree3() {
        Semester sem1 = new Semester();
        sem1.addSubject("Calculus", Grade.B);
        sem1.addSubject("English", Grade.B);
        sem1.addSubject("Discrete Math", Grade.B);
        sem1.addSubject("Imperative Programming", Grade.B);

        Semester sem2 = new Semester();
        sem2.addSubject("Calculus", Grade.A);
        sem2.addSubject("English", Grade.B);
        sem2.addSubject("Discrete Math", Grade.A);
        sem2.addSubject("Imperative Programming", Grade.A);

        recordBook.addSemester(sem1);
        recordBook.addSemester(sem2);

        recordBook.setDiplomaGrade(Grade.A);

        assertTrue(recordBook.honoursDegree());
    }

    @Test
    public void testAllTimeGPA() {
        Semester sem1 = new Semester();
        sem1.addSubject("Calculus", Grade.A);
        sem1.addSubject("English", Grade.A);
        sem1.addSubject("Discrete Math", Grade.B);
        sem1.addSubject("Imperative Programming", Grade.B);

        Semester sem2 = new Semester();
        sem2.addSubject("Calculus", Grade.A);
        sem2.addSubject("English", Grade.B);
        sem2.addSubject("Discrete Math", Grade.A);
        sem2.addSubject("Imperative Programming", Grade.A);

        recordBook.addSemester(sem1);
        recordBook.addSemester(sem2);

        recordBook.setDiplomaGrade(Grade.A);

        assertEquals(4.625, recordBook.allTimeGPA());
    }

    @Test
    public void testStipendPossibility() {
        Semester sem1 = new Semester();
        sem1.addSubject("Calculus", Grade.A);
        sem1.addSubject("English", Grade.C);
        sem1.addSubject("Discrete Math", Grade.A);
        sem1.addSubject("Imperative Programming", Grade.A);

        Semester sem2 = new Semester();
        sem2.addSubject("Calculus", Grade.A);
        sem2.addSubject("English", Grade.A);
        sem2.addSubject("Discrete Math", Grade.A);
        sem2.addSubject("Imperative Programming", Grade.A);

        recordBook.addSemester(sem1);
        recordBook.addSemester(sem2);

        assertTrue(recordBook.stipendPossible());
    }

    @Test
    public void testNoStipend() {
        Semester sem1 = new Semester();
        sem1.addSubject("Calculus", Grade.A);
        sem1.addSubject("English", Grade.C);
        sem1.addSubject("Discrete Math", Grade.A);
        sem1.addSubject("Imperative Programming", Grade.A);

        Semester sem2 = new Semester();
        sem2.addSubject("Calculus", Grade.A);
        sem2.addSubject("English", Grade.A);
        sem2.addSubject("Discrete Math", Grade.C);
        sem2.addSubject("Imperative Programming", Grade.A);

        recordBook.addSemester(sem1);
        recordBook.addSemester(sem2);

        assertFalse(recordBook.stipendPossible());
    }
}
