package com.nikita;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {

    @Test
    public void testToCalculateDataWithAllSign() {
        String expected = Main.calculate("100+2-1*4/2");
        while (expected.contains("/") | expected.contains("*") | expected.contains("+") | expected.contains("-")){
            expected = Main.calculate(expected);
        }
        assertEquals(expected ,"100.0");
    }

    @Test
    public void testToCalculateDataWithPunctuationMarks() {
        boolean expectedTrue = Main.containLetters("100a?+1");
        assertTrue(expectedTrue);
    }

    @Test
    public void testingDivisionByZero() {
        boolean expectedTrue = Main.divisionByZero("100/0");
        assertTrue(expectedTrue);
    }
}