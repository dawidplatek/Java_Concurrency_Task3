package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class EquationFuture extends FutureTask<String> {

    private BufferedWriter in;
    private String equation;


    public EquationFuture(Equation equation, BufferedWriter in) {
        super(equation);
        this.equation = equation.getEquation();
        this.in = in;
    }

    @Override
    protected void done() {
        try {
            this.in.newLine();
            this.in.write(this.equation + this.get());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
