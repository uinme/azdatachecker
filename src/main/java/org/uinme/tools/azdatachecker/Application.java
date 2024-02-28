package org.uinme.tools.azdatachecker;

import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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
        List<List<String>> data1 = csvReader.read(Paths.get("//192.168.10.109/uinme/share/csv/data1.csv"));
        List<List<String>> data2 = csvReader.read(Paths.get("//192.168.10.109/uinme/share/csv/data1.csv"));
        
        data1.stream().sorted((s1, s2) -> {
            String joined1 = String.join(",", s1);
            String joined2 = String.join(",", s2);
            return joined1.compareTo(joined2);
        }).forEach(System.out::println);
        
        
        
    }
    
}
