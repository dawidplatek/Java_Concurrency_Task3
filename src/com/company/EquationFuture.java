package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class EquationFuture extends FutureTask<String> {

    private File file;

    public EquationFuture(File file) {
        super(new Equation(file));
        this.file = file;
    }

    @Override
    protected void done() {
        try {

            ArrayList<String> fileContent = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }
            bufferedReader.close();

            String equationAnswer = this.get();
            if(equationAnswer == "") return;
            System.out.println(equationAnswer);

            String equation = equationAnswer.split("=")[0] + "=";
            fileContent.set(fileContent.indexOf(equation), equationAnswer);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file));
            for(String lineContent : fileContent) {
                bufferedWriter.write(lineContent);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
