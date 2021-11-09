package com.company;

import java.io.*;
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
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
        String equation;
        while((equation = bufferedReader.readLine()) != null) {
            if(equation.endsWith("=")) {
                break;
            }
        }
        bufferedReader.close();
        if(equation == null) return "";
        return equation + this.calculator.obliczOnp(this.calculator.przeksztalcNaOnp(equation));
    }
}
