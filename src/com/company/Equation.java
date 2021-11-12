package com.company;

import java.io.*;
import java.util.concurrent.Callable;

public class Equation implements Callable<String> {

    private final ONP calculator;
    private FileCondition fileCondition;

    public Equation(FileCondition fileCondition) {
        this.calculator = new ONP();
        this.fileCondition = fileCondition;
    }

    @Override
    public String call() throws IOException, InterruptedException {
        String equation = this.fileCondition.readEquation();
        return equation != null ? equation + this.calculateEquation(equation) : null;
    }


    private String calculateEquation(String equation) {
        String answer = this.calculator.obliczOnp(this.calculator.przeksztalcNaOnp(equation));
        return answer;
    }
}
