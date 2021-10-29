package com.company;

import java.util.concurrent.*;

public class EquationFuture extends FutureTask<String> {

    private String filePath;

    public EquationFuture(Callable equation, String filePath) {
        super(equation);
        this.filePath = filePath;
    }

    @Override
    protected void done() {
        try {
            System.out.println(this.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
