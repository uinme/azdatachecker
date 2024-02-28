package org.uinme.tools.azdatachecker.csvutil;

import java.util.List;

import org.springframework.lang.Nullable;

public class Csv {
    @Nullable
    private List<List<String>> data;
    
    public List<List<String>> getData() {
        return this.data;
    }
    
    public void setData(List<List<String>> data) {
        this.data = data;
    }
}
