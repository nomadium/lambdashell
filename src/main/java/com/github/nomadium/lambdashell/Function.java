package com.github.nomadium.lambdashell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class Function {

    public String handler(String command, Context context) {
        LambdaLogger logger = context.getLogger();
        String output = "";
        logger.log("command to run: " + command);
        
        try {
          output = executeCommand(command);
        } catch (Exception e) {
          e.printStackTrace();
        }

        return output;
    }

    private static String executeCommand(String command) throws
        IOException, InterruptedException {
        StringBuffer output = new StringBuffer();

        Process p;

        p = Runtime.getRuntime().exec(command);
        p.waitFor();

        BufferedReader reader = 
            new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = "";
        while ((line = reader.readLine())!= null) {
            output.append(line + "\n");
        }

        return output.toString();
    }
}
