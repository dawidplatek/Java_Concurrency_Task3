package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class EquationFuture extends FutureTask<String> {

    private FileCondition fileCondition;

    public EquationFuture(FileCondition fileCondition) {
        super(new Equation(fileCondition));
        this.fileCondition = fileCondition;
    }

    @Override
    protected void done() {
        try {
            String equation = this.get();
            System.out.println(equation);
            if(equation == null) return;
            ArrayList<String> fileContent = this.fileCondition.readFileContent();
            String[] equationString = equation.split("=");
            fileContent.set(fileContent.indexOf(equationString[0] + "="), equation);
            this.fileCondition.writeToFile(fileContent, equation);
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
