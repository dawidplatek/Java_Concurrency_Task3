package com.company;

import java.util.concurrent.Executors;


public class Main {

    private static final String filePath = "rownania.txt";

    public static void main(String[] args) throws Exception {
        EquationHandler equationHandler = new EquationHandler(filePath, Executors.newFixedThreadPool(5));
        equationHandler.calculateEquations();
    }

}

