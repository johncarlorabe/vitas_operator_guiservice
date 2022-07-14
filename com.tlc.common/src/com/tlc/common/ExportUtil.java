package com.tlc.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExportUtil {
	public static byte[] exportToExcel(String[][] data){
		SXSSFWorkbook wb = null;
		ByteArrayOutputStream out = null;
		try{
			wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
	        Sheet sh = wb.createSheet();
	        for(int rownum = 0; rownum < data.length; rownum++){
	            Row row = sh.createRow(rownum);
	            for(int cellnum = 0; cellnum < data[rownum].length; cellnum++){
	                Cell cell = row.createCell(cellnum);
	                cell.setCellValue(data[rownum][cellnum]);
	            }
	
	           if(rownum % 100 == 0) {
	                ((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others
	                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
	                // this method flushes all rows
	           }
	        }
	
	        out = new ByteArrayOutputStream();
	        wb.write(out);
	        return out.toByteArray();
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(wb != null) wb.dispose();
			if(out != null)try {out.close();} catch (IOException e) {}
		}
		return null;
	}

	public static byte[] exportToExcel(DataRowCollection data){
		return exportToExcel(data, true);
	}
	
	public static byte[] exportToExcel(DataRowCollection data, boolean withheader){
		SXSSFWorkbook wb = null;
		ByteArrayOutputStream out = null;
		try{
			wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
	        Sheet sh = wb.createSheet();
	        for(int rownum = 0; rownum < data.size(); rownum++){
	            DataRow datarow = data.get(rownum);
	            if(withheader && rownum == 0){
	            	Row row = sh.createRow(rownum);
	            	for(int cellnum = 0; cellnum < datarow.size(); cellnum++){
		                Cell cell = row.createCell(cellnum);
		                cell.setCellValue(datarow.getKey(cellnum));
		            }
	            }
	            Row row = sh.createRow(rownum);
	            for(int cellnum = 0; cellnum < datarow.size(); cellnum++){
	                Cell cell = row.createCell(cellnum);
	                cell.setCellValue(datarow.getString(cellnum));
	            }
				if(rownum % 100 == 0) {
					((SXSSFSheet)sh).flushRows(100);
				}
	        }
	        out = new ByteArrayOutputStream();
	        wb.write(out);
	        return out.toByteArray();
		}catch(Exception e){
			Logger.LogServer(e);
		}finally{
			if(wb != null) wb.dispose();
			if(out != null)try {out.close();} catch (IOException e) {}
		}
		return null;
	}
	
	public static String exportToCsv(DataRowCollection rows){
		return exportToCsv(rows,",","\n\r");
	}
	
	public static String exportToCsv(DataRowCollection rows, String separator, String lineseparator){
		String keys[] = null;
		String values[] = null;
		StringBuilder csv = new StringBuilder();
		for(int i=0,len=rows.size();i<len;i++){
			DataRow row = rows.get(i);
			if(i==0){
				keys = new String[row.size()];
				values = new String[row.size()];
				for(int j=0; j<row.size(); j++){
					keys[j] =  row.getKey(j);
					csv.append(keys[j]);
					if(j<row.size()-1){
						csv.append(separator);
					}
				}
				csv.append(lineseparator);
			}
			for(int j=0; j<row.size(); j++){
				values[j] =  row.getString(j).replace("'", "").replace(lineseparator,"");
				csv.append(values[j]);
				if(j<row.size()-1){
					csv.append(separator);
				}
			}
			csv.append(lineseparator);
		}
		return csv.toString();
	}
}
