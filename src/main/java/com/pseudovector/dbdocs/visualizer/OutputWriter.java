package com.pseudovector.dbdocs.visualizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutputWriter {
    Path filePath;
	String content;
	
	/**
	 * Constructor
	 */
	public OutputWriter(Path filePath,String content) {
		this.filePath = filePath;
		this.content = content;		
	}
	
	/**
	 * Write content to file
	 * @throws IOException
	 */
	public void write() throws IOException {
        Objects.requireNonNull(this.filePath, "No FilePath. Will not write anything.");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath.toFile()))) {
            writer.write(content);
            writer.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Failed to write content to file: {}", e.getMessage(), e);
            }
            throw e;
        }
	} 
}
