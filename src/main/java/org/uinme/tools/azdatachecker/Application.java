package org.uinme.tools.azdatachecker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.uinme.tools.azdatachecker.csvutil.Csv;
import org.uinme.tools.azdatachecker.csvutil.CsvDiff;
import org.uinme.tools.azdatachecker.csvutil.CsvFileItem;
import org.uinme.tools.azdatachecker.csvutil.CsvReader;
import org.uinme.tools.azdatachecker.csvutil.Diff;
import org.uinme.tools.azdatachecker.csvutil.FileDigest;

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

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        Application app = ctx.getBean(Application.class);
        app.run(args);
    }

    public void run(String... args) {
        Path dataDirectory = Paths.get("C:/Users/uinme/Desktop/csv");
        try {
            Map<String, List<CsvFileItem>> csvFileItems = Files.list(dataDirectory).map(p -> {
               String baseName = p.toFile().getName();
               String name = baseName.substring(0, baseName.indexOf("."));
               Matcher matcher = Pattern.compile(".*_(\\d{14})$").matcher(name);
               CsvFileItem item = new CsvFileItem();
               item.setPath(p);
               if (matcher.find()) {
                   LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                   item.setModifiedDate(dateTime);
                   item.setBaseName(name.replaceAll("_\\d{14}$", ""));
               } else {
                   item.setModifiedDate(LocalDateTime.MIN);
                   item.setBaseName(name);
               }
               return item;
            }).collect(Collectors.groupingBy(CsvFileItem::getBaseName));
            
            csvFileItems.forEach((k, v) -> v.sort((i1, i2) -> i1.getModifiedDate().compareTo(i2.getModifiedDate())));
            
            for (String key : csvFileItems.keySet()) {
                List<CsvFileItem> item = csvFileItems.get(key);
                if (item.size() > 1) {
                    System.out.println(item.get(item.size() - 1));
                } else {
                    System.out.println(item);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        Csv data1 = csvReader.read(Paths.get("C:/Users/uinme/Desktop/data1.csv"));
        Csv data2 = csvReader.read(Paths.get("C:/Users/uinme/Desktop/data2.csv"));
        data1.sortMutable();
        data2.sortMutable();
        
        Diff diff = csvDiff.diff(data1.getData(), data2.getData());

        Context ctx = new Context();
        ctx.setVariable("diff", diff);

        try (Writer writer = new FileWriter("sample.html")) {
            templateEngine.process("sample", ctx, writer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        byte[] file1 = fileDigest.getDigest(Paths.get("C:/Users/uinme/Desktop/data.csv"));
        byte[] file2 = fileDigest.getDigest(Paths.get("C:/Users/uinme/Desktop/datacp.csv"));
        
        if (Arrays.equals(file1, file2)) {
            System.out.println("Same");
        }
    }

}
