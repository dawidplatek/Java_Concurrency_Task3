package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class EquationHandler {

    private final ExecutorService executorService;
    private final String filePath;
    private BufferedWriter bufferedWriter;

    private final List<Callable<Object>> results;

    public EquationHandler(String filePath, ExecutorService executorService) {
        this.filePath = filePath;
        this.executorService = executorService;
        this.results = new ArrayList<>();
    }

    public void loadFile() throws Exception {
        List<String> listOfLines = new ArrayList<>();
        Stream<String> lines = Files.lines(Paths.get(filePath));
        lines.map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .forEach(listOfLines::add);
        this.bufferedWriter = new BufferedWriter(new FileWriter(this.filePath));
        for(String equation : listOfLines) {
            EquationFuture equationFuture = new EquationFuture(new Equation(equation), bufferedWriter);
            this.results.add(Executors.callable(equationFuture));
        }
    }

    public void calculateEquations() throws InterruptedException, IOException {
        this.executorService.invokeAll(this.results);
        this.bufferedWriter.flush();
        this.bufferedWriter.close();
        this.executorService.shutdown();
    }
}
