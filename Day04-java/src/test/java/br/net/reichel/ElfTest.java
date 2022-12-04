package br.net.reichel;


import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ElfTest {

    private final Elf elf = new Elf();

    @Test
    public void shouldParseFullOverlapInput() throws IOException {
        assertEquals(605, elf.processFilePart1("input.txt").getFullOverlapping());
    }

    @Test
    public void shouldParseFullOverlapSample() throws IOException {
        assertEquals(2, elf.processFilePart1("sample.txt").getFullOverlapping());
    }


    @Test
    public void shouldParsePartialOverlapInput() throws IOException {
        assertEquals(914, elf.processFilePart1("input.txt").getPartialOverlapping());
    }

    @Test
    public void shouldParsePartialOverlapSample() throws IOException {
        assertEquals(4, elf.processFilePart1("sample.txt").getPartialOverlapping());
    }

    @Test
    public void shouldParseLine1() {
        final String[] tokens = elf.parseLine("21-86,19-70");
        assertEquals("21", tokens[0]);
        assertEquals("86", tokens[1]);
        assertEquals("19", tokens[2]);
        assertEquals("70", tokens[3]);
    }

    @Test
    public void shouldParseLine2() {
        final String[] tokens = elf.parseLine("121-67890,1-7000");
        assertEquals("121", tokens[0]);
        assertEquals("67890", tokens[1]);
        assertEquals("1", tokens[2]);
        assertEquals("7000", tokens[3]);
    }

    @Test
    public void shouldGetRange() {
        assertEquals("[1..4]", elf.generateRange(1, 4).toString());
        assertEquals("[4..4]", elf.generateRange(4, 4).toString());
    }

    @Test
    public void shouldContainFullOverlap() {
        assertTrue(elf.hasFullOverlap(Range.closed(1, 4), Range.closed(4, 4)));
        assertTrue(elf.hasFullOverlap(Range.closed(4, 4), Range.closed(1, 4)));
        assertTrue(elf.hasFullOverlap(Range.closed(1, 10), Range.closed(2, 8)));
        assertTrue(elf.hasFullOverlap(Range.closed(2, 8), Range.closed(1, 10)));
        assertTrue(elf.hasFullOverlap(Range.closed(2, 2), Range.closed(2, 2)));
    }

    @Test
    public void shouldPartiallyOverlap() {
        assertTrue(elf.hasPartialOverlap(Range.closed(5, 7), Range.closed(7, 9)));
        assertTrue(elf.hasPartialOverlap(Range.closed(2, 8), Range.closed(3, 7)));
        assertTrue(elf.hasPartialOverlap(Range.closed(6, 6), Range.closed(4, 6)));
        assertTrue(elf.hasPartialOverlap(Range.closed(2, 6), Range.closed(4, 8)));
    }

}