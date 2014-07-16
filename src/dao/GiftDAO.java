package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import model.Gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Statement;


@Repository
public class GiftDAO {

	@Autowired
	private GeneralDAO generalDAO;

	@Transactional(readOnly=true)
	public Gift get(int id) throws DataAccessException{
		String query = "SELECT * FROM gift WHERE id = ? AND endDate IS NULL";
		Object[] params = {id};
		return generalDAO.getJdbcTemplate().queryForObject(query, params, new BeanPropertyRowMapper<Gift>(Gift.class));
	}
	
	@Transactional(readOnly=false)
	public int create(final String name, final String image, final String description, final String urls, final String shop, final Double price,
			final String publicHashtags, final String privateNotes, final String privateHashtags, final boolean gotIt, final boolean delivered,
			final boolean spread, final boolean anonymous, final Timestamp startDate, final Timestamp endDate, final Timestamp spreadDate, final Integer giftId,
			final String username) throws DataAccessException{
		JdbcTemplate jdbcTemplate = generalDAO.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0)
					throws SQLException {
				String query = "INSERT INTO gift (name, image, description, urls, shop, price, publicHashtags, privateNotes, privateHashtags, gotIt,"
						+ " delivered, spread, anonymous, startDate, endDate, spreadDate, giftId, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = arg0.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, name);
				ps.setString(2, image);
				ps.setString(3, description);
				ps.setString(4, urls);
				ps.setString(5, shop);
				if (price != null){
					ps.setDouble(6, price);
				}
				else{
					ps.setNull(6, java.sql.Types.DOUBLE);
				}
				ps.setString(7, publicHashtags);
				ps.setString(8, privateNotes);
				ps.setString(9, privateHashtags);
				ps.setBoolean(10, gotIt);
				ps.setBoolean(11, delivered);
				ps.setBoolean(12, spread);
				ps.setBoolean(13, anonymous);
				ps.setTimestamp(14, startDate);
				ps.setTimestamp(15, endDate);
				ps.setTimestamp(16, spreadDate);
				if (giftId != null){
					ps.setInt(17, giftId);
				}
				else{
					ps.setNull(17, java.sql.Types.INTEGER);
				}
				ps.setString(18, username);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	@Transactional(readOnly=false)
	public void update(int id, String privateNotes, String privateHashtags, boolean gotIt, boolean delivered, boolean spread, boolean anonymous, Timestamp spreadDate, boolean changeSpreadDate) throws DataAccessException{
		if (changeSpreadDate){
			String query = "UPDATE gift SET privateNotes = ?, privateHashtags = ?, gotIt = ?, delivered = ?, spread = ?, anonymous = ?, spreadDate = ?, startDate = startDate, endDate = endDate WHERE id = ?";
			Object[] params = {privateNotes, privateHashtags, gotIt, delivered, spread, anonymous, spreadDate, id};
			generalDAO.getJdbcTemplate().update(query, params);
		}
		else{
			String query = "UPDATE gift SET privateNotes = ?, privateHashtags = ?, gotIt = ?, delivered = ?, spread = ?, anonymous = ?, startDate = startDate, spreadDate = spreadDate, endDate = endDate WHERE id = ?";
			Object[] params = {privateNotes, privateHashtags, gotIt, delivered, spread, anonymous, id};
			generalDAO.getJdbcTemplate().update(query, params);
		}
	}
	
	@Transactional(readOnly=false)
	public void updateEndDate(int id, Timestamp endDate) throws DataAccessException{
		String query = "UPDATE gift SET endDate = ?, startDate = startDate, spreadDate = spreadDate WHERE id = ?";
		Object[] params = {endDate, id};
		generalDAO.getJdbcTemplate().update(query, params);
	}
	
	@Transactional(readOnly=false)
	public void updatePrivateStuff(int id, String privateNotes, String privateHashtags, boolean gotIt, boolean delivered) throws DataAccessException{
		String query = "UPDATE gift SET privateNotes = ?, privateHashtags = ?, gotIt = ?, delivered = ?, startDate = startDate, spreadDate = spreadDate, endDate = endDate WHERE id = ?";
		Object[] params = {privateNotes, privateHashtags, gotIt, delivered, id};
		generalDAO.getJdbcTemplate().update(query, params);
	}
	
	@Transactional(readOnly=true)
	public List<Gift> getAllByUsername(String username) throws DataAccessException{
		JdbcTemplate jdbcTemplate = generalDAO.getJdbcTemplate();
		String query = "SELECT * FROM gift g INNER JOIN user u ON g.username = u.username WHERE g.username = ? COLLATE utf8_bin AND g.endDate IS NULL ORDER BY g.startDate DESC";
		Object[] params = {username};
		return jdbcTemplate.query(query, params, new BeanPropertyRowMapper<Gift>(Gift.class));
	}
	
	@Transactional(readOnly = true)
	public boolean isSpread(int id) throws DataAccessException{
		String query = "SELECT COUNT(*) FROM gift WHERE id = ? AND spreadDate IS NOT NULL";
		Object[] params = new Object[]{id};
		return generalDAO.getJdbcTemplate().queryForObject(query, params, Integer.class) == 1;
	}
	
	@Transactional(readOnly=true)
	public List<Gift> giftsHome() throws DataAccessException{
		JdbcTemplate jdbcTemplate = generalDAO.getJdbcTemplate();
		String query = "SELECT * FROM gift WHERE spreadDate IS NOT NULL AND endDate IS NULL ORDER BY spreadDate DESC";
		return jdbcTemplate.query(query, new BeanPropertyRowMapper<Gift>(Gift.class));
	}
	
	@Transactional(readOnly=true)
	public boolean isCopied(int id, String usermane) throws DataAccessException{
		String query = "SELECT COUNT(*) FROM gift WHERE giftId = ? AND username = ? COLLATE utf8_bin";
		Object[] params = new Object[]{id, usermane};
		return generalDAO.getJdbcTemplate().queryForObject(query, params, Integer.class) == 1;
	}
}