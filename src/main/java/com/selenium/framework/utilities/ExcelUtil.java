package com.selenium.framework.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public FileOutputStream fileOut = null;
	public String path;
	public FileInputStream fis;
	public Workbook workbook;
	public Sheet sheet;
	public Row row;
	public Cell cell;
	
	
	/**
	 * Constructor for initializing and reading the excel file from a given file path
	 * @param path
	 */
	public ExcelUtil(String path)
	{
		this.path = path;
		try
		{
			fis = new FileInputStream(path);
			if(path.toLowerCase().endsWith("xlsx"))
			{
				workbook = new XSSFWorkbook(fis);
			}
			else if(path.toLowerCase().endsWith("xls"))
			{
				workbook = new HSSFWorkbook(fis);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Function to read data from an Excel sheet and return as a 2-D array
	 * @param sheetName STRING name of the sheet to be parsed
	 * @param excelName STRING name of the Excel file to be parsed
	 * @return STRING[][] representing the Excel sheet
	 */
	public String[][] getDataFromSheet(String sheetName, String excelName)
	{
		String[][] dataSets = null;
		try
		{
			Sheet sheet = workbook.getSheet(sheetName);
			
			//count number of active rows, and active columns in row
			int totalRow = sheet.getLastRowNum();
			int totalColumn = sheet.getRow(0).getLastCellNum();
			
			//create 2D array of rows and columns
			dataSets = new String[totalRow+1][totalColumn];
			
			//loop through and store data in 2D array
			for(int i=0; i<totalRow+1; i++)
			{
				Row rows = sheet.getRow(i);
				
				for(int j=0; j<totalColumn; j++)
				{
					Cell cell = rows.getCell(j);
					switch(cell.getCellType())
					{
						case STRING:
						{
							dataSets[i][j] = cell.getStringCellValue();
							break;
						}
						case NUMERIC:
						{
							dataSets[i][j] = String.valueOf(cell.getNumericCellValue());
							break;
						}
						case BOOLEAN:
						{
							dataSets[i][j] = String.valueOf(cell.getBooleanCellValue());
							break;
						}
						case ERROR:
						{
							dataSets[i][j] = "ERROR";
							break;
						}
						default: throw new Exception("ERROR: Invalid cell type identified.");
					}
				}
				return dataSets;
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Exception in reading Excel file: "+e.getMessage());
			e.printStackTrace();
		}		
		return dataSets;
	}
	
	

}
