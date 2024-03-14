package org.uinme.tools.azdatachecker;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.thymeleaf.TemplateEngine;
import org.uinme.tools.azdatachecker.csvutil.CsvDiff;
import org.uinme.tools.azdatachecker.csvutil.CsvFileItem;
import org.uinme.tools.azdatachecker.csvutil.CsvFileList;
import org.uinme.tools.azdatachecker.csvutil.CsvReader;
import org.uinme.tools.azdatachecker.csvutil.FileDigest;
import org.uinme.tools.azdatachecker.mapper.TestMapper;
import org.uinme.tools.azdatachecker.mapper_sqlite.DDLMapper;

@SpringBootApplication
public class Application {

    @Autowired
    private CsvReader csvReader;
    @Autowired
    private CsvDiff csvDiff;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private FileDigest fileDigest;
    @Autowired
    private DDLMapper ddlMapper;
    @Autowired
    private TestMapper testMapper;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        Application app = ctx.getBean(Application.class);
        app.run(args);
    }

    public void run(String... args) {
        CsvFileList csvFileList = new CsvFileList(Paths.get("C:/Users/uinme/Desktop/csv"));
        
        Resource currentResource = new ClassPathResource(".");
        String currentDirectory;
        Path downloadDirectory;
        
        try {
            currentDirectory = currentResource.getFile().toString();
            downloadDirectory = Paths.get(currentDirectory, "download");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        if (!Files.exists(downloadDirectory)) {
            try {
                Files.createDirectory(downloadDirectory);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        
        List<CsvFileItem> csvFileItems = csvFileList.getLatestFiles();
        
        for (CsvFileItem csvFileItem : csvFileItems) {
            try {
                Files.copy(csvFileItem.getPath(), Paths.get(downloadDirectory.toString(), csvFileItem.getBaseName() + ".csv"));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        
//        ddlMapper.createTable();
//        testMapper.selectAll();
        
        // Compare CSV count to DB count
        
        // Output report file with csv
        
        // Compare CSV
        
        // Output csv diff report
        
//        Csv data1 = csvReader.read(Paths.get("C:/Users/uinme/Desktop/data1.csv"));
//        Csv data2 = csvReader.read(Paths.get("C:/Users/uinme/Desktop/data2.csv"));
//        data1.sortMutable();
//        data2.sortMutable();
//        
//        Diff diff = csvDiff.diff(data1.getData(), data2.getData());
//
//        Context ctx = new Context();
//        ctx.setVariable("diff", diff);
//
//        try (Writer writer = new FileWriter("sample.html")) {
//            templateEngine.process("sample", ctx, writer);
//        } catch (IOException e) {
//            throw new UncheckedIOException(e);
//        }
//        
//        byte[] file1 = fileDigest.getDigest(Paths.get("C:/Users/uinme/Desktop/data.csv"));
//        byte[] file2 = fileDigest.getDigest(Paths.get("C:/Users/uinme/Desktop/datacp.csv"));
//        
//        if (Arrays.equals(file1, file2)) {
//            System.out.println("Same");
//        }
    }

}
