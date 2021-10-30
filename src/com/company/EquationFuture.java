package com.company;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EquationFuture extends FutureTask<String> {

    private String filePath;
    private String equation;
    private Stream<String> lines;


    public EquationFuture(Equation equation, Stream<String> lines, String filePath) {
        super(equation);
        this.equation = equation.getEquation();
        this.filePath = filePath;
        this.lines = lines;
    }

    @Override
    protected void done() {
        try (Stream<String> lines = Files.lines(Path.of(this.filePath))) {
            List<String> replaced = lines
                    .map(line-> {
                        try {
                            return line.concat(this.get().toString());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return line;
                    })
                    .collect(Collectors.toList());
            Files.write(Path.of(this.filePath), replaced);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
