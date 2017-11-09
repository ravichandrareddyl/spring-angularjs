package com.ravi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
@Configuration
public class DataRowMapper implements RowMapper<ExcelModel>{
	
	public ExcelModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExcelModel model = new ExcelModel();
		model.setField1(rs.getString("id"));
		model.setField2(rs.getString("name"));
        return model;
    }
}
