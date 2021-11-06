package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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
            String result = this.get();
            if (result == "") {
                return;
            }
            System.out.println(result);
            String[] resultSplit = this.get().split("=");
            resultSplit[0] += "=";

            List<String> fileContent = new ArrayList<>(Files.readAllLines(this.file.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(resultSplit[0])) {
                    fileContent.set(i, result);
                    break;
                }
            }
            Files.write(this.file.toPath(), fileContent, StandardCharsets.UTF_8);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
