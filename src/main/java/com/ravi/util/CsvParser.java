package com.ravi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.ravi.model.ExcelModel;

@Configuration
public class CsvParser {
	
	
	
	public List<ExcelModel> openCSVParser(InputStream csvfile) throws IOException {
		CSVReader reader = new CSVReader(new InputStreamReader(csvfile, "UTF-8"), 
				CSVParser.DEFAULT_SEPARATOR,CSVParser.DEFAULT_QUOTE_CHARACTER, 1);

		
		List <ExcelModel> list = new ArrayList<ExcelModel>();

		// read line by line
		String[] row = null;

		while ((row = reader.readNext()) != null) {
			ExcelModel model = new ExcelModel();
            model.setField1(row[0]);
            model.setField2(row[1]);
            model.setField3(row[2]);
            model.setField4(row[3]);
            model.setField5(row[4]);
            model.setField6(row[5]);
            model.setField7(row[6]);
            model.setField8(row[7]);
            model.setField9(row[8]);
            model.setField10(row[9]);
            model.setField11(row[10]);
            list.add(model);
		}
		reader.close();
		return list;
	}

	public List<ExcelModel> parseCSV(InputStream csvfile) {
		 String line = "";
	     String cvsSplitBy = ",";
	     List <ExcelModel> list = new ArrayList<ExcelModel>();
	     try (BufferedReader br = new BufferedReader(new InputStreamReader(csvfile, "UTF-8"))) {

	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] row = line.split(cvsSplitBy);
	                ExcelModel model = new ExcelModel();
	                model.setField1(row[0]);
	                model.setField2(row[1]);
	                model.setField3(row[2]);
	                model.setField4(row[3]);
	                model.setField5(row[4]);
	                model.setField6(row[5]);
	                model.setField7(row[6]);
	                model.setField8(row[7]);
	                model.setField9(row[8]);
	                model.setField10(row[9]);
	                model.setField11(row[10]);
	                list.add(model);
	                
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		return list;
	}
}
