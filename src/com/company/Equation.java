package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Equation implements Callable<String> {

    private final ONP calculator;
    private File file;

    public Equation(File file) {
        this.calculator = new ONP();
        this.file = file;
    }

    @Override
    public String call() throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(this.file.toPath(), StandardCharsets.UTF_8));
        for(String line : fileContent) {
            if(line.endsWith("=")) {
                return line + this.calculator.obliczOnp(this.calculator.przeksztalcNaOnp(line));
            }
        }
        return "";
    }
}
