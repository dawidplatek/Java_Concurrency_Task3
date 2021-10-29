package com.company;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;


public class Main implements Callable<List<String>>{

    private static final String filePath = "rownania.txt";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    public List<String> call() throws Exception {
        List<String> listOfLines = new ArrayList<>();
        Stream<String> lines = Files.lines(Paths.get(filePath));
        lines.map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .forEach(listOfLines::add);
        return listOfLines;
    }

    static String computeExpression(String expression) {
        ONP onp = new ONP();
        return onp.obliczOnp(onp.przeksztalcNaOnp(expression));
    }

    public static void main(String[] args) throws Exception {

        Future<List<String>> listOfLines = executorService.submit(new Main());
        List<String> strings = listOfLines.get();
        for (String exp : strings) {
            String result = computeExpression(exp);
            System.out.println(exp + " " + result);
        }
        executorService.shutdown();
    }

}

