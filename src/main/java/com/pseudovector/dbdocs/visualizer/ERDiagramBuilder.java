package com.pseudovector.dbdocs.visualizer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.pseudovector.dbdocs.entity.Model;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

@Slf4j
@Setter
@NoArgsConstructor
public class ERDiagramBuilder {

    private String dbName;
    private boolean lROption = false;
    private boolean entitiesOnly = false;
    private VisualizeFormat outputFormat = VisualizeFormat.DOT;
    private Path outputFilePath;

    public ERDiagramBuilder(String dbName) {
        this.dbName = dbName;
    }

    public static ERDiagramBuilder withSchema(String dbName) {
        return new ERDiagramBuilder(dbName);
    }

    public ERDiagramBuilder withOutputFilePath(Path outputFilePath) throws IOException {
        Path dirPath = outputFilePath.getParent();
        if (!Files.isDirectory(dirPath)) {
            Files.createDirectories(dirPath);
        }
        this.setOutputFilePath(outputFilePath);
        return this;
    }

    public ERDiagramBuilder withFormat(VisualizeFormat format) {
        this.setOutputFormat(format);
        return this;
    }

    public ERDiagramBuilder withLROption() {
        this.setLROption(true);
        return this;
    }

    public ERDiagramBuilder withEntityOnly() {
        this.setEntitiesOnly(true);
        return this;
    }

    public void execute() throws IOException {
        Model model = ERModelResolver.bySchema(this.dbName);
        Visualizer visualizer = Visualizer.builder()
                                          .tables(model.getTableList())
                                          .lrEnabled(this.lROption)
                                          .entitiesOnly(this.entitiesOnly)
                                          .build();
        FileFormatOption fileFormatOption = null;
        switch (this.outputFormat) {
            case DOT:
                OutputWriter dotWriter = new OutputWriter(this.outputFilePath, visualizer.getDotRepresentation());
                dotWriter.write();
                break;
            case PLANT:
                OutputWriter plantWriter = new OutputWriter(this.outputFilePath, visualizer.getPlantRepresentation());
                plantWriter.write();
                break;
            case PNG:
                if (log.isInfoEnabled()) {
                    log.info("Format for diagram: PNG");
                }
                fileFormatOption = new FileFormatOption(FileFormat.PNG);
                break;
            case SVG:
                if (log.isInfoEnabled()) {
                    log.info("Format for diagram: SVG");
                }
                fileFormatOption = new FileFormatOption(FileFormat.SVG);
                break;
            case PDF: 
                if (log.isInfoEnabled()) {
                    log.info("Format for diagram: PDF");
                }
                fileFormatOption = new FileFormatOption(FileFormat.PDF);
                break;
            default:
                break;
        }
        if (null != fileFormatOption) {
            String plantString = visualizer.getPlantRepresentation();
            SourceStringReader sourceStringReader = new SourceStringReader(plantString);
            try (OutputStream outStream = new FileOutputStream(outputFilePath.toFile())) {
                String description = sourceStringReader.outputImage(outStream, fileFormatOption).getDescription();
                if (log.isInfoEnabled()) {
                    log.info("Built ER diagram {}, Description of output image: {}", outputFilePath.getFileName(), description);  
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Failed to output image: {}", e.getMessage(), e);
                }
            }
        }
    }
} 
