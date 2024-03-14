package azdatachecker;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;
import org.uinme.tools.azdatachecker.csvutil.CsvFileItem;
import org.uinme.tools.azdatachecker.csvutil.CsvFileList;


public class CsvFileListTest {

    @Test
    public void allFilesSelected() {
        CsvFileList csvFileList = getCsvFileList("csv1");
        List<CsvFileItem> itemList = csvFileList.getLatestFiles();
        assertTrue(
            itemList.stream()
                .map(item -> item.getBaseName())
                .collect(Collectors.toList())
                .containsAll(Arrays.asList(
                        "VIS20240101_XA_CIS1",
                        "VIS20240101_XB_CIS1",
                        "VIS20240101_XC_CIS1"))
        );
    }

    @Test
    public void latestFilesSelected() {
        CsvFileList csvFileList = getCsvFileList("csv1");
        List<CsvFileItem> itemList = csvFileList.getLatestFiles();
        assertTrue(
            itemList.stream()
            .map(item -> {
                try {
                    return Files.readAllLines(item.getPath(), StandardCharsets.UTF_8).get(0);
                } catch (IOException e) {
                    return "";
                }
            })
            .collect(Collectors.toList())
            .stream()
            .allMatch(value -> value.equals("latest"))
        );
    }
    
    @Test
    public void collectBusinessDateFromFileNameExtracted() {
        CsvFileList csvFileList = getCsvFileList("csv1");
        
        Map<String, List<CsvFileItem>> itemMap = csvFileList.getCsvFileItems();
        List<Boolean> result = new ArrayList<Boolean>();
        for (String key : itemMap.keySet()) {
            if (key.contains("VIS20240101_")) {
                for (CsvFileItem item : itemMap.get(key)) {
                    if (item.getBusinessDate().equals(LocalDate.of(2024, 1, 1))) {
                        result.add(true);
                    }
                }
            }
        }
        
        assertTrue(result.stream().allMatch(value -> value));
    }
    
    @Test
    public void CollectModifiedDateTimeFromFileNameExtracted() {
        CsvFileList csvFileList = getCsvFileList("csv1");
        
        Map<String, List<CsvFileItem>> itemMap = csvFileList.getCsvFileItems();
        List<Boolean> result = new ArrayList<Boolean>();
        for (String key : itemMap.keySet()) {
            if (key.contains("VIS20240101_")) {
                for (CsvFileItem item : itemMap.get(key)) {
                    if (item.getFullName().equals("VIS20240101_XB_CIS1_20240102000000")) {
                        if (item.getModifiedDate().equals(LocalDateTime.of(2024, 1, 2, 0, 0, 0))) {
                            result.add(true);
                        }
                    } else if (item.getFullName().equals("VIS20240101_XC_CIS1_20240102000000")) {
                        if (item.getModifiedDate().equals(LocalDateTime.of(2024, 1, 2, 0, 0, 0))) {
                            result.add(true);
                        }
                    } else if (item.getFullName().equals("VIS20240101_XC_CIS1_20240102000001")) {
                        if (item.getModifiedDate().equals(LocalDateTime.of(2024, 1, 2, 0, 0, 1))) {
                            result.add(true);
                        }
                    }
                }
            }
        }
        
        assertTrue(result.stream().allMatch(value -> value));
    }
    
    private CsvFileList getCsvFileList(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("csv1").getFile());
        CsvFileList csvFileList = new CsvFileList(Paths.get(file.getAbsolutePath()));
        return csvFileList;
    }

}
