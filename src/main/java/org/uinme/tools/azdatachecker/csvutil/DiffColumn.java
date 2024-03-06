package org.uinme.tools.azdatachecker.csvutil;

import lombok.Data;

@Data
public class DiffColumn {
    private String value;
    private boolean isDiff;
    private String status;
}
