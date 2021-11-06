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

    public void calculateEquations() throws InterruptedException {
        for(int i = 0; i < 36; i++) {
            this.tasks.add(Executors.callable(new EquationFuture(this.file)));
        }
        this.executorService.invokeAll(this.tasks);
        this.executorService.shutdown();
    }
}
