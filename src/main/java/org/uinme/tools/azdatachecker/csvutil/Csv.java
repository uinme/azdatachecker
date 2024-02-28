package org.uinme.tools.azdatachecker.csvutil;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<List<String>> sortImutable(final int... keyNo) {
        return this.data.stream().sorted((s1, s2) -> {
            String j1 = "";
            String j2 = "";
            for (int n : keyNo) {
                j1 += s1.get(n);
                j2 += s2.get(n);
            }
            return j1.compareTo(j2);
        }).collect(Collectors.toList());
    }

    public List<List<String>> sortImutable() {
        return this.data.stream().sorted((s1, s2) -> {
            String j1 = String.join("", s1);
            String j2 = String.join("", s2);
            return j1.compareTo(j2);
        }).collect(Collectors.toList());
    }

    public void sortMutable(final int... keyNo) {
        this.data.sort((s1, s2) -> {
            String j1 = "";
            String j2 = "";
            for (int n : keyNo) {
                j1 += s1.get(n);
                j2 += s2.get(n);
            }
            return j1.compareTo(j2);
        });
    }

    public void sortMutable() {
        this.data.sort((s1, s2) -> {
            String j1 = String.join("", s1);
            String j2 = String.join("", s2);
            return j1.compareTo(j2);
        });
    }

}
