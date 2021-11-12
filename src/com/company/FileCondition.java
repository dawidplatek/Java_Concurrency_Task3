package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileCondition {

    private File file;

    Lock lock = new ReentrantLock();
    private Condition fileRead = lock.newCondition();
    private Condition fileWrite = lock.newCondition();
    private boolean newContent = true;

    private volatile ArrayList<String> queue = new ArrayList<>();

    public FileCondition(File file) {
        this.file = file;
    }

    public ArrayList<String> readFileContent() throws IOException {
        this.lock.lock();
        try {

            while(!this.newContent) {
                this.fileWrite.await();
            }

            ArrayList<String> fileContent = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }
            bufferedReader.close();

            this.newContent = false;
            this.fileRead.signal();
            System.out.println("Test 1 " + this.newContent);
            return fileContent;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(((ReentrantLock)this.lock).isHeldByCurrentThread()) {
                this.lock.unlock();
            }
        }
    }

    public void writeToFile(ArrayList<String> fileContent, String equation) throws IOException {
        this.lock.lock();
        try {

            while(this.newContent) {
                this.fileRead.await();
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file));
            for(String lineContent : fileContent) {
                bufferedWriter.write(lineContent);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            bufferedWriter.close();

            this.newContent = true;
            this.fileWrite.signal();

            System.out.println("Test 2 " + this.newContent);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(((ReentrantLock)this.lock).isHeldByCurrentThread()) {
                this.lock.unlock();
            }
        }
    }

    public String readEquation() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
        String equation;
        while((equation = bufferedReader.readLine()) != null) {
            if(equation.endsWith("=") && !(this.queue.contains(equation))) {
                break;
            }
        }
        this.queue.add(equation);
        bufferedReader.close();
        return equation;
    }



}
