package com.company;

import java.util.concurrent.Callable;

public class Equation implements Callable<String> {

    private String equation;
    private ONP calculator;

    public Equation(String equation) {
        this.calculator = new ONP();
        this.equation = equation;
    }


    @Override
    public String call() throws Exception {
        String result = this.calculator.obliczOnp(this.calculator.przeksztalcNaOnp(this.equation));
        return result;
    }
}
