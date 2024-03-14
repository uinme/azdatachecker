package org.uinme.tools.azdatachecker.csvutil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

public class CsvFileList {

    private Path dataDirectory;
    private Map<String, List<CsvFileItem>> csvFileItems;

    public CsvFileList(Path dataDirectory) {
        this.dataDirectory = dataDirectory;

        try {
            csvFileItems = Files.list(dataDirectory).map(p -> {
                String baseName = FilenameUtils.getBaseName(p.toFile().getName());
                Matcher matcher = Pattern.compile(".*_(\\d{14})$").matcher(baseName);
                CsvFileItem item = new CsvFileItem();
                item.setFullName(baseName);
                item.setPath(p);
                if (matcher.find()) {
                    LocalDateTime dateTime = LocalDateTime.parse(matcher.group(1),
                            DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    item.setModifiedDate(dateTime);
                    item.setBaseName(baseName.replaceAll("_\\d{14}$", ""));
                } else {
                    item.setModifiedDate(LocalDateTime.MIN);
                    item.setBaseName(baseName);
                }
                Matcher businessDateMatcher = Pattern.compile("(?<=[^\\d]*)\\d{8}(?=[^\\d]+)").matcher(baseName);
                if (businessDateMatcher.find()) {
                    String businessDateString = businessDateMatcher.group(0);
                    item.setBusinessDate(LocalDate.parse(businessDateString, DateTimeFormatter.ofPattern("yyyyMMdd")));
                } else {
                    item.setBusinessDate(LocalDate.MIN);
                }
                return item;
            }).collect(Collectors.groupingBy(CsvFileItem::getBaseName));

            csvFileItems.forEach((k, v) -> v.sort((i1, i2) -> i1.getModifiedDate().compareTo(i2.getModifiedDate())));

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<CsvFileItem> getLatestFiles() {
        List<CsvFileItem> items = new ArrayList<CsvFileItem>();
        for (String key : csvFileItems.keySet()) {
            List<CsvFileItem> item = csvFileItems.get(key);
            if (item.size() > 1) {
                items.add(item.get(item.size() - 1));
            } else {
                items.add(item.get(0));
            }
        }
        return items;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }
    
    public Map<String, List<CsvFileItem>> getCsvFileItems() {
        return csvFileItems;
    }

}
