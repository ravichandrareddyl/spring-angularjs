package com.ravi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
@Configuration
public class DAMProfileMapper implements RowMapper<DAMProfile> {
	
	public DAMProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
		DAMProfile model = new DAMProfile();
		model.setDamName(rs.getString("dam_name"));
		model.setDamVersion(rs.getString("dam_version"));
		model.setDamId(rs.getInt("dam_id"));
		model.setCreatedBy(rs.getString("created_by"));
		model.setModifiedBy(rs.getString("modified_by"));
		model.setCreatedDate(rs.getDate("created_date").toString());
		model.setModifiedDate(rs.getDate("modified_date").toString());
        return model;
    }

}
