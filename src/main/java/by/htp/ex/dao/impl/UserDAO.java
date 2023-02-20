package by.htp.ex.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReentrantLock;

import org.mindrot.jbcrypt.BCrypt;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.dao.connection_pool.ConnectionPool;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;

public class UserDAO implements IUserDAO {

	private static final ReentrantLock LOCKER = new ReentrantLock();

	private static final String QUEST_ROLE = "quest";
	private static final String SQL_ROLE_PARAM = "title";
	private static final String PASSWORD_PARAM = "password";
	private static final String SQL_COLUMN_ID = "id";
	private static final String SQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

	private static final String SQL_QUERY_FOR_LOGINATION = "SELECT * FROM users WHERE login=?";

	@Override
	public boolean logination(String login, String password) throws DaoException {

		boolean isLoggedIn = false;
		ResultSet resultSet = null;

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FOR_LOGINATION)) {

			statement.setString(1, login);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				isLoggedIn = BCrypt.checkpw(password, resultSet.getString(PASSWORD_PARAM));
			}
			return isLoggedIn;

		} catch (ConnectionPoolException e) {
			throw new DaoException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new DaoException("error while working with database", e);
		}
	}

	private static final String SQL_QUERY_FIND_ROLE = "SELECT * FROM users INNER JOIN roles ON users.roles_id=roles.id WHERE login=?";

	public String getRole(String login, String password) throws DaoException {

		String role = QUEST_ROLE;
		ResultSet resultSet = null;

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_FIND_ROLE)) {

			statement.setString(1, login);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (BCrypt.checkpw(password, resultSet.getString(PASSWORD_PARAM))) {
					role = resultSet.getString(SQL_ROLE_PARAM);
				}
			}
			return role;
		} catch (ConnectionPoolException e) {
			throw new DaoException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new DaoException("error while working with database", e);
		}
	}

	private static final String SQL_QUERY_ADD_NEW_USER = "INSERT INTO users(login,password,roles_id,user_status_id,date_of_creation) VALUES(?,?,?,?,?)";
	private static final String SQL_QUERY_ADD_NEW_USER_DETAILS = "INSERT INTO user_details(users_id,name,surname,email,birhday) VALUES(?,?,?,?,?)";
	private static final String SQL_QUERY_FIND_ID_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	@Override
	public boolean registration(NewUserInfo user) throws DaoException {

		boolean isNotRegistered = true;
		ResultSet resultSet = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SQL_DATETIME_FORMAT);
		LocalDateTime date = LocalDateTime.now();
		String dateTime = formatter.format(date);

		String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

		try (Connection connection = CONNECTION_POOL.takeConnection()) {
			connection.setAutoCommit(false);
			LOCKER.lock();
			try {
				PreparedStatement statementIdByLogin = connection.prepareStatement(SQL_QUERY_FIND_ID_BY_LOGIN);
				PreparedStatement statementAddUser = connection.prepareStatement(SQL_QUERY_ADD_NEW_USER);
				PreparedStatement statementAddUserDetails = connection.prepareStatement(SQL_QUERY_ADD_NEW_USER_DETAILS);

				statementIdByLogin.setString(1, user.getLogin());
				resultSet = statementIdByLogin.executeQuery();

				while (resultSet.next()) {
					isNotRegistered = false;
					return isNotRegistered;
				}
				statementAddUser.setString(1, user.getLogin());
				statementAddUser.setString(2, hashpw);
				statementAddUser.setInt(3, 1);
				statementAddUser.setInt(4, 1);
				statementAddUser.setString(5, dateTime);
				statementAddUser.executeUpdate();

				resultSet = statementIdByLogin.executeQuery();
				int id = 0;

				while (resultSet.next()) {
					id = resultSet.getInt(SQL_COLUMN_ID);
				}
				DateTimeFormatter formatterBirthday = DateTimeFormatter.ofPattern(SQL_DATE_FORMAT);
				LocalDate birthdayTime = LocalDate.parse(user.getBirthday());
				String birthday = formatterBirthday.format(birthdayTime);

				statementAddUserDetails.setInt(1, id);
				statementAddUserDetails.setString(2, user.getName());
				statementAddUserDetails.setString(3, user.getSurname());
				statementAddUserDetails.setString(4, user.getEmail());
				statementAddUserDetails.setString(5, birthday);
				statementAddUserDetails.executeUpdate();

				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("error while working with database", e);
			}
		} catch (SQLException e) {
			throw new DaoException("error while working with database", e);
		} catch (ConnectionPoolException e) {
			throw new DaoException("error trying to take connection", e);
		} finally {
			LOCKER.unlock();
		}
		return isNotRegistered;
	}
}
