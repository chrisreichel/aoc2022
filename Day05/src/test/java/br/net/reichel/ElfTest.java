package br.net.reichel;


import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElfTest {
    @Test
    public void shouldMatchFirstStageParsing(){
        final Elf elf = new Elf(9000);
        assertEquals(1, elf.getParseStage("        [X]"));
        assertEquals(1, elf.getParseStage("    [C] [X]"));
        assertEquals(1, elf.getParseStage("    [D]    "));
        assertEquals(1, elf.getParseStage("[N] [C]    "));
        assertEquals(1, elf.getParseStage("[Z] [M] [P]"));
        assertEquals(1, elf.getParseStage("[H] [B] [J] [V] [B] [M]     [N] [P]"));
        assertEquals(1, elf.getParseStage("[G] [G] [G] [N] [V] [V] [T] [Q] [F]"));
    }

    @Test
    public void shouldMatchSecondStageParsing(){
        final Elf elf = new Elf(9000);
        assertEquals(2, elf.getParseStage(" 1   2   3   4   5   6   7   8   9 "));
        assertEquals(2, elf.getParseStage(" 1   2   3 "));
    }

    @Test
    public void shouldMatchThirdStageParsing(){
        final Elf elf = new Elf(9000);
        assertEquals(3, elf.getParseStage(""));
    }

    @Test
    public void shouldMatchForthStageParsing(){
        final Elf elf = new Elf(9000);
        assertEquals(4, elf.getParseStage("move 6 from 9 to 3"));
        assertEquals(4, elf.getParseStage("move 1 from 2 to 1"));
        assertEquals(4, elf.getParseStage("move 32 from 3 to 5"));
    }

    @Test
    public void shouldGetNumOfPiles(){
        final Elf elf = new Elf(9000);
        assertEquals(3, elf.getNumOfPiles("        [X]"));
        assertEquals(3, elf.getNumOfPiles("    [C] [X]"));
        assertEquals(3, elf.getNumOfPiles("    [D]    "));
        assertEquals(3, elf.getNumOfPiles("[N] [C]    "));
        assertEquals(3, elf.getNumOfPiles("[Z] [M] [P]"));
        assertEquals(9, elf.getNumOfPiles("[H] [B] [J] [V] [B] [M]     [N] [P]"));
        assertEquals(9, elf.getNumOfPiles("[G] [G] [G] [N] [V] [V] [T] [Q] [F]"));
    }

    @Test
    public void shouldGetValuesOnPositionsLast(){
        final Elf elf = new Elf(9000);
        final Map<Integer, String> r1 = elf.getPositionedValues("        [X]");
        assertEquals(1, r1.size());
        assertEquals("X", r1.get(3));

        final Map<Integer, String> r2 = elf.getPositionedValues("    [C] [X]");
        assertEquals(2, r2.size());
        assertEquals("C", r2.get(2));
        assertEquals("X", r2.get(3));

        final Map<Integer, String> r3 = elf.getPositionedValues("    [D]    ");
        assertEquals(1, r3.size());
        assertEquals("D", r3.get(2));

        final Map<Integer, String> r4 = elf.getPositionedValues("[Z] [M] [P]");
        assertEquals(3, r4.size());
        assertEquals("Z", r4.get(1));
        assertEquals("M", r4.get(2));
        assertEquals("P", r4.get(3));
    }

    @Test
    public void shouldRunSamplePart1() {
        final Elf elf = new Elf(9000);
        assertEquals("CMZ", elf.processCraneFile("sample.txt"));
    }

    @Test
    public void shouldRunFullPart1() {
        final Elf elf = new Elf(9000);
        assertEquals("FCVRLMVQP", elf.processCraneFile("input.txt"));
    }

    @Test
    public void shouldRunSamplePart2() {
        final Elf elf = new Elf(9001);
        assertEquals("MCD", elf.processCraneFile("sample.txt"));
    }

    @Test
    public void shouldRunFullPart2() {
        final Elf elf = new Elf(9001);
        assertEquals("RWLWGJGFD", elf.processCraneFile("input.txt"));
    }
}