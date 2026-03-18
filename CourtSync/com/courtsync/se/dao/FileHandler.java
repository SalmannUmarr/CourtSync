package com.courtsync.se.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton responsible for low-level file operations in data/ folder.
 * Provides readAll, append, and writeAll for users.csv.
 */
public class FileHandler {
    private static FileHandler instance;
    private final Path dataDir;

    private FileHandler() {
        dataDir = Path.of("data");
        try {
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
            ensureFile("users.csv");
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data directory", e);
        }
    }

    private void ensureFile(String fileName) throws IOException {
        Path p = dataDir.resolve(fileName);
        if (!Files.exists(p)) Files.createFile(p);
    }

    public static synchronized FileHandler getInstance() {
        if (instance == null) instance = new FileHandler();
        return instance;
    }

    public List<String> readAll(String fileName) throws IOException {
        Path p = dataDir.resolve(fileName);
        if (!Files.exists(p)) return new ArrayList<>();
        return Files.readAllLines(p);
    }

    public synchronized void append(String fileName, String line) throws IOException {
        Path p = dataDir.resolve(fileName);
        try (var bw = Files.newBufferedWriter(p, StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        }
    }

    public synchronized void writeAll(String fileName, List<String> lines) throws IOException {
        Path p = dataDir.resolve(fileName);
        Files.write(p, lines);
    }
}
