package com.company;

import java.util.concurrent.Callable;

public class Equation implements Callable<String> {

    private final String equation;
    private final ONP calculator;

    public Equation(String equation) {
        this.calculator = new ONP();
        this.equation = equation;
    }


    @Override
    public String call() {
        String result = this.calculator.obliczOnp(this.calculator.przeksztalcNaOnp(this.equation));
        System.out.println(this.equation + result);
        return result;
    }

    public String getEquation() {
        return this.equation;
    }
}
