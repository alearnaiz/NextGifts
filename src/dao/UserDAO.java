package dao;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO {
	
	@Autowired
	private GeneralDAO generalDAO;

	@Transactional(readOnly=false)
	public void create(String firstName, String lastNames, String username, String password, Timestamp startDate) throws DataAccessException{
		JdbcTemplate jdbcTemplate = generalDAO.getJdbcTemplate();
		String query = "INSERT INTO user (firstName, lastNames, username, password, startDate) VALUES (?, ?, ?, ?, ?)";
		Object[] params = {firstName, lastNames, username, password, startDate};
		jdbcTemplate.update(query, params);
	}
	
	@Transactional(readOnly = true)
	public boolean checkUsernameAndPassword(String username, String password) throws DataAccessException{
		String query = "SELECT COUNT(*) FROM user WHERE username = ? COLLATE utf8_bin AND password = ? COLLATE utf8_bin";
		Object[] params = new Object[]{username, password};
		return generalDAO.getJdbcTemplate().queryForObject(query, params, Integer.class) == 1;
	}
	
	@Transactional(readOnly = true)
	public boolean checkUsername(String username) throws DataAccessException{
		String query = "SELECT COUNT(*) FROM user WHERE username = ?";
		Object[] params = new Object[]{username};
		return generalDAO.getJdbcTemplate().queryForObject(query, params, Integer.class) == 1;
	}
}
