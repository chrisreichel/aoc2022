package br.net.reichel;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Elf {

    private static final String REGEX_STG1 = "move (\\d+) from (\\d+) to (\\d+)";
    final Pattern patternStg1 = Pattern.compile(REGEX_STG1);

    public final static int STG_CRATES = 1;
    public final static int STG_STACK_NAMES = 2;

    public final static int STG_BLANK = 3;

    public final static int STG_MOVEMENTS = 4;

    public void processCraneFile(String fileName) {
        BufferedReader objReader = null;

        final Map<Integer, Stack<String>> loadBay = new HashMap();
        final Map<Integer, Stack<String>> stacks = new HashMap();

        try {

            int parsingStage = 0;
            String strCurrentLine;
            final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            final File file = new File(classloader.getResource(fileName).getFile());
            objReader = new BufferedReader(new FileReader(file));
            while ((strCurrentLine = objReader.readLine()) != null) {

                parsingStage = (parsingStage != STG_MOVEMENTS)?getParseStage(strCurrentLine) : STG_MOVEMENTS;

                switch (parsingStage) {
                    case STG_CRATES:
                        //System.out.println("Lendo a pilha de caixas " + strCurrentLine);
                        final Map<Integer, String> craneOperations = getPositionedValues(strCurrentLine);
                        craneOperations.entrySet().stream().forEach(entry -> {
                            final Integer key = entry.getKey();
                            final Stack<String> createPile = loadBay.getOrDefault(key, new Stack<>());
                            createPile.add(entry.getValue());
                            loadBay.putIfAbsent(key, createPile);
                        });
                        //debugLoadBay(loadBay);
                        break;
                    case STG_STACK_NAMES:
                        System.out.println("reading stack names and fixing stacks");
                        debug(loadBay);
                        loadBay.entrySet().stream().forEach(stackEntry -> {
                            final Stack<String> originalQueued = stackEntry.getValue();
                            final Stack<String> piledCorrectly = new Stack<>();
                            originalQueued.stream().forEach(s -> piledCorrectly.push(s));
                            stacks.put(stackEntry.getKey(), piledCorrectly);
                        });
                        //debug(stacks);
                        break;
                    case STG_MOVEMENTS:
                        System.out.println("Lendo os movimentos: " + strCurrentLine);
                        final Integer[] movement = parseMovement(strCurrentLine);
                        final Integer numOfCrates = movement[0];
                        final Stack<String> stackSource = stacks.getOrDefault(movement[1], new Stack<>());
                        final Stack<String> stackDestiny = stacks.getOrDefault(movement[2], new Stack<>());
                        for(int i = 0; i < numOfCrates; i++){
                            final String crate = stackSource.pop();
                            stackDestiny.push(crate);
                        }
                        //debug(stacks);
                        break;
                    default:
                        System.out.println("Ignored " + strCurrentLine);



                }
            }
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
        debug(stacks);

    }

    public void debug(Map<Integer, Stack<String>> stacks){
        stacks.entrySet().stream().forEach(entry -> {
            System.out.print("\n\r -> " + entry.getKey());
            entry.getValue().stream().forEach(s -> System.out.print(" ["+ s +"]"));
        });
        System.out.println("\n\r-----------");
    }

    public void debugLoadBay(Map<Integer, Queue<String>> loadBay){
        loadBay.entrySet().stream().forEach(entry -> {
            System.out.print("\n\r -> " + entry.getKey());
            entry.getValue().stream().forEach(s -> System.out.print(" ["+ s +"]"));
        });
        System.out.println("\n\r-----------");
    }

    public int getParseStage(String line){
        if(line.contains("[")){
            return STG_CRATES;
        }
        else if(line.contains("move")){
            return STG_MOVEMENTS;
        }
        else if(StringUtils.isBlank(line)){
            return STG_BLANK;
        }
        return STG_STACK_NAMES;
    }

    public Integer[] parseMovement(String line) {
        final Integer[] tokens = new Integer[3];
        final Matcher matcher = patternStg1.matcher(line);
        if (matcher.find()) {
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = Integer.parseInt(matcher.group(i + 1));
            }
        } else {
            System.out.println("NO MATCH");
        }
        return tokens;
    }

    public int getNumOfPiles(String stg1Line) {
        return (stg1Line.length() + 1) / 4;
    }

    public Map<Integer, String> getPositionedValues(String stg1Line) {
        final Map<Integer, String> response = new HashMap<>();
        final String line = stg1Line + " ";
        int i = 1;
        for(String value : Splitter.fixedLength(4).split(line)){
            if(StringUtils.isNotBlank(value)){
                response.put(i, StringUtils.chop(value.replace("[", "").replace("]", "")));
            }
            i++;
        }
        return response;
    }
}
