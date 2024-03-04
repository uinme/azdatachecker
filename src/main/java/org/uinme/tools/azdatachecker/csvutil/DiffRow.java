package org.uinme.tools.azdatachecker.csvutil;

import java.util.List;

import lombok.Data;

@Data
public class DiffRow {
    private List<DiffColumn> columns;
}
