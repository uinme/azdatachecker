package org.uinme.tools.azdatachecker.csvutil;

import java.util.List;

import lombok.Data;

@Data
public class Diff {
    public static enum Status {
        INCREASED,
        DECREASED
    }
    
    private List<DiffRow> originalRows;
    private List<DiffRow> modifiedRows;
    private Status status;
}
