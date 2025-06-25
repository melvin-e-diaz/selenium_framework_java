package com.selenium.framework.utilities;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.datatable.DataTable;

/**
 * Created by Karthik-PC on 21/04/2018.
 */
public class CucumberUtil {


    private static List<DataCollection> _dataCollection = new ArrayList<>();


    public static List<DataCollection> ConvertDataTableToDict(DataTable table) {
        _dataCollection.clear();

        List<List<String>> data = table.asLists();

        int rowNumber = 0;

        for (List<String> col : data) {
            for (int colIndex = 0; colIndex < col.size(); colIndex++) {
                _dataCollection.add(rowNumber, new DataCollection(data.get(0).get(colIndex), col.get(colIndex), rowNumber));
            }

            rowNumber++;
        }

        return _dataCollection;

    }

    public static String GetCellValueWithRowIndex(String columnName, int rowNumber) {
        final String[] columnValue = {null};
        _dataCollection.forEach(x -> {
            if(x.ColumnName.equals(columnName) && x.RowNumber == rowNumber){
                columnValue[0] = x.ColumnValue;
            }
        });
        return columnValue[0];
    }

    private static class DataCollection {
        private String ColumnName;
        private String ColumnValue;
        private int RowNumber;

        public DataCollection(String columnName, String columnValue, int rowNumber) {
            ColumnName = columnName;
            ColumnValue = columnValue;
            RowNumber = rowNumber;
        }
    }

}



