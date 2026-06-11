package main;

import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String matlabHome = loadOrDetectMatlab();
        if (matlabHome == null) {
            System.err.println("MATLAB not found!");
            System.exit(1);
        }
        UI ui = new UI();
    }

    private static String loadOrDetectMatlab() throws Exception {
        Path config = Paths.get("matlab.config");

        // If already configured, just read it
        if (Files.exists(config)) {
            return Files.readString(config).trim();
        }

        // First launch — detect MATLAB
        System.out.println("First launch, detecting MATLAB...");
        ProcessBuilder pb = new ProcessBuilder("matlab", "-batch", "disp(matlabroot)");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
        );

        String matlabRoot = null;
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("C:") || line.startsWith("/")) {
                matlabRoot = line;
                break;
            }
        }
        process.waitFor();

        // Save for next time
        if (matlabRoot != null) {
            Files.writeString(config, matlabRoot);
        }

        return matlabRoot;
    }
}