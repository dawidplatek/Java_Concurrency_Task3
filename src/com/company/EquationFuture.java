package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.*;

public class EquationFuture extends FutureTask<String> {

    private final BufferedWriter bufferedWriter;
    private final String equation;


    public EquationFuture(Equation equation, BufferedWriter bufferedWriter) {
        super(equation);
        this.equation = equation.getEquation();
        this.bufferedWriter = bufferedWriter;
    }

    @Override
    protected void done() {
        try {
            this.bufferedWriter.write(this.equation + this.get());
            this.bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
