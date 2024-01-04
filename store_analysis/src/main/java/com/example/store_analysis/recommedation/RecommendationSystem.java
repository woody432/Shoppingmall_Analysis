package com.example.store_analysis.recommedation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class RecommendationSystem
{
    public String pythonOutput;

    public RecommendationSystem(String gender, float height, float weight) throws IOException, InterruptedException
    {
        String gender_;
        String Script_Path = "C:\\Users\\cuci\\Downloads\\recommendationSystem.py";

//        String[] paramsArray = {"1", "163", "53"};
        if(gender.equals("M")) gender_ = "0";
        else gender_ = "1";

        String[] paramsArray = {gender_, Float.toString(height), Float.toString(weight)};

        ProcessBuilder processBuilder = new ProcessBuilder("python", Script_Path);
        processBuilder.command().addAll(Arrays.asList(paramsArray)); // 파라미터 전달

        processBuilder.redirectErrorStream(true);
        Process demoProcess = processBuilder.start();
        demoProcess.waitFor();

        // Get the input stream of the process
        InputStream inputStream = demoProcess.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder output = new StringBuilder();
        String outputLine;

        // Read the output of the process line by line
        while ((outputLine = bufferedReader.readLine()) != null) {
            output.append(outputLine).append("\n");
        }

        // Close the BufferedReader
        bufferedReader.close();

        // Convert the output to a String
        pythonOutput = output.toString();

        // Print or use the pythonOutput as needed
//        System.out.println("Python Output:\n" + pythonOutput);
    }
}

