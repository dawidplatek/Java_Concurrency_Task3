package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EquationHandler {

    private final ExecutorService executorService;
    private File file;

    private List<Callable<Object>> tasks;

    public EquationHandler(String filePath, ExecutorService executorService) {
        this.file = new File(filePath);
        this.executorService = executorService;
        this.tasks = new ArrayList<>();
    }

    public void calculateEquations() throws InterruptedException, IOException {
        FileCondition fileCondition = new FileCondition(this.file);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
        int lines = 0;
        while(bufferedReader.readLine() != null) lines++;
        for(int i = 0; i < lines; i++) {
            this.tasks.add(Executors.callable(new EquationFuture(fileCondition)));
        }
        this.executorService.invokeAll(this.tasks);
        this.executorService.shutdown();
    }
}
