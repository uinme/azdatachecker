package org.uinme.tools.azdatachecker.csvutil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CsvDiff {
    
    private final String INCREASE_DECREASE = "increase/decrease";

    public Diff diff(List<List<String>> original, List<List<String>> modified) {
        int rowSize = Math.max(original.size(), modified.size());
        int colSize = Math.max(original.get(0).size(), modified.get(0).size());

        Diff diff = new Diff();
        diff.setOriginalRows(new ArrayList<DiffRow>());
        diff.setModifiedRows(new ArrayList<DiffRow>());

        for (int i = 0; i < rowSize; i++) {
            DiffRow originalRow = new DiffRow();
            originalRow.setColumns(new ArrayList<DiffColumn>());
            DiffRow modifiedRow = new DiffRow();
            modifiedRow.setColumns(new ArrayList<DiffColumn>());

            for (int j = 0; j < colSize; j++) {
                DiffColumn originalColumn = new DiffColumn();
                DiffColumn modifiedColumn = new DiffColumn();
                originalColumn.setStatus("");
                modifiedColumn.setStatus("");

                boolean outBound = false;
                
                if (i >= original.size() || j >= original.get(i).size()) {
                    originalColumn.setValue(" ");
                    originalColumn.setStatus(INCREASE_DECREASE);
                    outBound = true;
                } else {
                    originalColumn.setValue(original.get(i).get(j));
                }

                if (i >= modified.size() || j >= modified.get(i).size()) {
                    modifiedColumn.setValue(" ");
                    modifiedColumn.setStatus(INCREASE_DECREASE);
                    outBound = true;
                } else {
                    modifiedColumn.setValue(modified.get(i).get(j));
                }

                if (!outBound && originalColumn.getValue().equals(modifiedColumn.getValue())) {
                    modifiedColumn.setDiff(false);
                } else {
                    modifiedColumn.setDiff(true);
                }

                if (i < original.size() && j < original.get(i).size()) {
                    originalRow.getColumns().add(originalColumn);
                }
                modifiedRow.getColumns().add(modifiedColumn);
            }

            diff.getOriginalRows().add(originalRow);
            diff.getModifiedRows().add(modifiedRow);
        }

        return diff;
    }
}
