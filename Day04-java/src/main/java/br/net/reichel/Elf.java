package br.net.reichel;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Elf {

    private static final String REGEX = "(\\d+)-(\\d+),(\\d+)-(\\d+)";

    public OverlappingSectionResponse processFilePart1(String fileName) {
        BufferedReader objReader = null;
        int numOfFullOverlaps = 0;
        int numOfPartialOverlaps = 0;
        try {
            String strCurrentLine;
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            File file = new File(classloader.getResource(fileName).getFile());
            objReader = new BufferedReader(new FileReader(file));
            while ((strCurrentLine = objReader.readLine()) != null) {

                final String[] tokens = parseLine(strCurrentLine);
                final Range<Integer> leftElfRange = generateRange(tokens[0], tokens[1]);
                final Range<Integer> rightElfRange = generateRange(tokens[2], tokens[3]);

                if (hasFullOverlap(leftElfRange, rightElfRange)) {
                    numOfFullOverlaps++;
                }
                if (hasPartialOverlap(leftElfRange, rightElfRange)) {
                    numOfPartialOverlaps++;
                }
            }
            System.out.println("Full overlaps: " + numOfFullOverlaps + " and partial overlaps: " + numOfPartialOverlaps);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objReader != null) {
                    objReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new OverlappingSectionResponse(numOfFullOverlaps, numOfPartialOverlaps);
    }

    public String[] parseLine(String line) {
        final String[] tokens = new String[4];
        final Pattern pattern = Pattern.compile(REGEX);
        final Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = matcher.group(i + 1);
            }
        } else {
            System.out.println("NO MATCH");
        }
        return tokens;
    }

    public Range<Integer> generateRange(String lower, String upper) {
        return generateRange(Integer.parseInt(lower), Integer.parseInt(upper));
    }

    public Range<Integer> generateRange(Integer lower, Integer upper) {
        return Range.closed(lower, upper);
    }

    public boolean hasFullOverlap(Range<Integer> firstElfRange, Range<Integer> secondElfRange) {
        return firstElfRange.encloses(secondElfRange) || secondElfRange.encloses(firstElfRange);
    }

    public boolean hasPartialOverlap(Range<Integer> firstElfRange, Range<Integer> secondElfRange) {
        final RangeSet<Integer> firstElfSet = TreeRangeSet.create();
        firstElfSet.add(firstElfRange);
        final RangeSet<Integer> secondElfSet = TreeRangeSet.create();
        secondElfSet.add(secondElfRange);

        return firstElfSet.intersects(secondElfRange) || secondElfSet.intersects(firstElfRange);
    }

    public class OverlappingSectionResponse {
        private Integer fullOverlapping;
        private Integer partialOverlapping;

        public OverlappingSectionResponse(Integer fullOverlapping, Integer partialOverlapping) {
            this.fullOverlapping = fullOverlapping;
            this.partialOverlapping = partialOverlapping;
        }

        public Integer getFullOverlapping() {
            return fullOverlapping;
        }

        public Integer getPartialOverlapping() {
            return partialOverlapping;
        }
    }
}
