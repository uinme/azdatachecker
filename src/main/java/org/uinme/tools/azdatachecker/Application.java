package org.uinme.tools.azdatachecker;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.uinme.tools.azdatachecker.csvutil.Csv;
import org.uinme.tools.azdatachecker.csvutil.CsvReader;

@SpringBootApplication
public class Application {
    @Autowired
    private CsvReader csvReader;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        Application app = ctx.getBean(Application.class);
        app.run(args);
    }
    
    public void run(String... args) {
        Csv data1 = csvReader.read(Paths.get("C:/Users/uinme/Desktop/data1.csv"));
        data1.sortImutable().stream().forEach(System.out::println);
    }
    
}
