package org.uinme.tools.azdatachecker.csvutil;

import java.nio.file.Path;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CsvFileItem {

    private Path path;
    private String baseName;
    private LocalDateTime modifiedDate;

}
