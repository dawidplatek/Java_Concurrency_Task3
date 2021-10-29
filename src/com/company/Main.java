package com.company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;
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

    static void writeFile(List<String> expressions) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        String finalExpression = " ";
        for (int i = 0; i < expressions.size(); i++) {
            finalExpression = expressions.get(i) + " " + computeExpression(expressions.get(i)) +"\n";
            fos.write(finalExpression.getBytes());
        }
        fos.close();
    }

    public static void main(String[] args) throws Exception {

        Future<List<String>> emptyList = executorService.submit(new Main());
        List<String> consumedList= emptyList.get();
            writeFile(consumedList);
        executorService.shutdown();
    }

}

