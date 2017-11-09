package com.ravi.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ravi.model.DAMProfile;
import com.ravi.model.DAMProfileMapper;
import com.ravi.model.DataRowMapper;
import com.ravi.model.ExcelModel;

@Transactional(propagation = Propagation.REQUIRED)
@Configuration
public class ExampleDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataRowMapper dataRowMaper;
	@Autowired
	private DAMProfileMapper damProfileMapper;

	public List<ExcelModel> getListOfUsers() {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("DAM_PKGS").withProcedureName("GET_DAM_PROFILES")
				.returningResultSet("P_PROFILES", dataRowMaper);

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		System.out.println(simpleJdbcCallResult);

		return (List<ExcelModel>) simpleJdbcCallResult.get("P_PROFILES");
	}

	public List<DAMProfile> getDamProfiles() {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("DAM_PKGS").withProcedureName("GET_DAM_PROFILES")
				.returningResultSet("P_PROFILES", damProfileMapper);

		SqlParameterSource in = new MapSqlParameterSource();

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		System.out.println(simpleJdbcCallResult);

		return (List<DAMProfile>) simpleJdbcCallResult.get("P_PROFILES");

	}
	
	public int[] batchUpdate(final List<ExcelModel> dataRows, final String fileName,
			final String user) {
		final int id = this.getNewDamId(fileName, user);
		int[] updateCounts = jdbcTemplate
				.batchUpdate(
						"insert into DAM_DATA_SRC(DATA_PK , EVENT_TIME,ALERT_NO,SERVER_GROUP,"
								+ "ALERT_NAME,SRC_IP,"
								+ "DEST_IP, SRC_OS_USER,USERNAME,SOURCE_OF_ACTIVITY,"
								+ "ALERT_DESC,PARSED_QRY,NUMBER_OF_EVENTS,"
								+ "CREATED_BY,MODIFIED_BY,CREATED_DATE,"
								+ "MODIFIED_DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)",
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setInt(1, id);
								ps.setString(2, dataRows.get(i).getField1());
								ps.setString(3, dataRows.get(i).getField2());
								ps.setString(4, dataRows.get(i).getField3());
								ps.setString(5, dataRows.get(i).getField4());
								ps.setString(6, dataRows.get(i).getField5());
								ps.setString(7, dataRows.get(i).getField6());
								ps.setString(8, dataRows.get(i).getField7());
								ps.setString(9, dataRows.get(i).getField8());
								ps.setString(10, dataRows.get(i).getField9());
								ps.setString(11, dataRows.get(i).getField10());
								ps.setString(12, dataRows.get(i).getField11());
								ps.setString(13, dataRows.get(i).getField12());
								ps.setString(14, user);
								ps.setString(15, user);

							}

							public int getBatchSize() {
								return dataRows.size();
							}
						});
		return updateCounts;
	}

	public int getNewDamId(String fileName, String user) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withCatalogName("DAM_PKGS").withProcedureName(
						"GENERATE_DAM_ID");

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_DAM_NAME", fileName);
		inParamMap.put("P_CREATED_BY", user);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map out = simpleJdbcCall.execute(in);
		return ((BigDecimal)out.get("P_DAM_ID")).intValue();
	}

}
