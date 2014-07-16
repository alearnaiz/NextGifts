package dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GeneralDAO {
	
	@Autowired
	private DataSource dataSource;
	
	public JdbcTemplate getJdbcTemplate(){
		return new JdbcTemplate(this.dataSource);
	}
}
