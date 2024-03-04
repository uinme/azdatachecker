package org.uinme.tools.azdatachecker.csvutil;

import java.util.ArrayList;
import java.util.List;

public class CsvDiff {

    private List<List<String>> original;
    private List<List<String>> modified;
    private int maxRowSize;
    private int maxColumnSize;

    public CsvDiff(List<List<String>> original, List<List<String>> modified) {
        this.maxRowSize = Math.max(original.size(), modified.size());
        this.maxColumnSize = Math.max(original.get(0).size(), modified.get(0).size());
        this.original = original;
        this.modified = modified;
    }

    public Diff diff(List<List<String>> original, List<List<String>> modified) {
        //        int rowSize = Math.max(original.size(), modified.size());
        //        int colSize = Math.max(original.get(0).size(), modified.get(0).size());

        Diff diff = new Diff();
        diff.setOriginalRows(new ArrayList<DiffRow>());
        diff.setModifiedRows(new ArrayList<DiffRow>());

        for (int i = 0; i < maxRowSize; i++) {
            for (int j = 0; j < maxColumnSize; j++) {

            }
        }

        //        for (int i = 0; i < rowSize; i++) {
        //            DiffRow originalRow = new DiffRow();
        //            originalRow.setColumns(new ArrayList<DiffColumn>());
        //            DiffRow modifiedRow = new DiffRow();
        //            modifiedRow.setColumns(new ArrayList<DiffColumn>());
        //
        //            for (int j = 0; j < colSize; j++) {
        //                DiffColumn originalColumn = new DiffColumn();
        //                DiffColumn modifiedColumn = new DiffColumn();
        //
        //                if (i >= original.size() || j >= original.get(i).size()) {
        //                    originalColumn.setValue("");
        //                } else {
        //                    originalColumn.setValue(original.get(i).get(j));
        //                }
        //
        //                if (i >= modified.size() || j >= modified.get(i).size()) {
        //                    modifiedColumn.setValue("");
        //                } else {
        //                    modifiedColumn.setValue(modified.get(i).get(j));
        //                }
        //
        //                if (originalColumn.getValue().equals(modifiedColumn.getValue())) {
        //                    modifiedColumn.setDiff(false);
        //                } else {
        //                    modifiedColumn.setDiff(true);
        //                }
        //
        //                originalRow.getColumns().add(originalColumn);
        //                modifiedRow.getColumns().add(modifiedColumn);
        //            }
        //
        //            diff.getOriginalRows().add(originalRow);
        //            diff.getModifiedRows().add(modifiedRow);
        //        }

        return diff;
    }

    private boolean allColumnDifference(int row) {
        
        return false;
    }

}
