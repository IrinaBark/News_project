package by.htp.ex.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.dao.connection_pool.ConnectionPool;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;

public class NewsDAO implements INewsDAO {

	private static final String SQL_COLUMN_DATE = "date_of_creation";
	private static final String SQL_COLUMN_ID = "id";
	private static final String SQL_COLUMN_TITLE = "title";
	private static final String SQL_COLUMN_BRIEF = "brief";
	private static final String SQL_COLUMN_CONTENT = "content";
	private static final String SQL_DATE_FORMAT = "yyyy/MM/dd";
	private static final String SQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

	private static final String SQL_QUERY_LATEST_NEWS = "SELECT * FROM news ORDER BY date_of_creation DESC LIMIT ?";

	@Override
	public List<News> getLatestsList(int count) throws NewsDAOException {
		List<News> result = new ArrayList<News>();
		ResultSet resultSet = null;

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_LATEST_NEWS)) {

			statement.setInt(1, count);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Date date = resultSet.getDate(SQL_COLUMN_DATE);
				SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATE_FORMAT);
				String formattedDate = formatter.format(date);

				result.add(new News(resultSet.getInt(SQL_COLUMN_ID), resultSet.getString(SQL_COLUMN_TITLE),
						resultSet.getString(SQL_COLUMN_BRIEF), resultSet.getString(SQL_COLUMN_CONTENT), formattedDate));
			}
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new NewsDAOException("error while working with database in method getLatestList()", e);
		}
		return result;
	}

	private static final String SQL_QUERY_NEWS_LIST = "SELECT * FROM news ORDER BY date_of_creation DESC";

	@Override
	public List<News> getList() throws NewsDAOException {

		List<News> result = new ArrayList<News>();
		ResultSet resultSet = null;

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_NEWS_LIST)) {

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Date date = resultSet.getDate(SQL_COLUMN_DATE);
				SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATE_FORMAT);
				String formattedDate = formatter.format(date);

				result.add(new News(resultSet.getInt(SQL_COLUMN_ID), resultSet.getString(SQL_COLUMN_TITLE),
						resultSet.getString(SQL_COLUMN_BRIEF), resultSet.getString(SQL_COLUMN_CONTENT), formattedDate));
			}
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new NewsDAOException("error while working with database in method getList()", e);
		}
		return result;
	}

	private static final String SQL_QUERY_NEWS_BY_ID = "SELECT * FROM news WHERE id=?";

	@Override
	public News fetchById(int id) throws NewsDAOException {

		News news = null;
		ResultSet resultSet = null;

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_NEWS_BY_ID)) {

			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Date date = resultSet.getDate(SQL_COLUMN_DATE);
				SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATE_FORMAT);
				String formattedDate = formatter.format(date);

				news = new News(id, resultSet.getString(SQL_COLUMN_TITLE), resultSet.getString(SQL_COLUMN_BRIEF),
						resultSet.getString(SQL_COLUMN_CONTENT), formattedDate);
			}
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new NewsDAOException("error while working with database in method fetchById()", e);
		}
		return news;
	}

	private final static String SQL_QUERY_ADD_NEWS = "INSERT INTO news(title,date_of_creation,brief,content,users_id,news_status_id_news_status) VALUES(?,?,?,?,?,?)";
	private final static String SQL_QUERY_SELECT_ID = "SELECT * FROM news WHERE title=? AND brief=? AND content=?";

	@Override
	public int addNews(News news) throws NewsDAOException {

		int id = 0;

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ADD_NEWS);
				PreparedStatement statementFindId = connection.prepareStatement(SQL_QUERY_SELECT_ID)) {

			SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATETIME_FORMAT);
			Date date = new Date();
			String dateTime = formatter.format(date);

			statement.setString(1, news.getTitle());
			statement.setString(2, dateTime);
			statement.setString(3, news.getBriefNews());
			statement.setString(4, news.getContent());
			statement.setInt(5, 2);
			statement.setInt(6, 3);
			statement.executeUpdate();

			statementFindId.setString(1, news.getTitle());
			statementFindId.setString(2, news.getBriefNews());
			statementFindId.setString(3, news.getContent());

			ResultSet resultSet = statementFindId.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt(SQL_COLUMN_ID);
			}
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new NewsDAOException("error while working with database in method addNews()", e);
		}
		return id;
	}

	private final static String SQL_QUERY_UPDATE_NEWS = "UPDATE news SET title=?,date_of_creation=?,brief=?,content=? WHERE id=?";

	@Override
	public void updateNews(News news) throws NewsDAOException {

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_UPDATE_NEWS)) {

			SimpleDateFormat formatter = new SimpleDateFormat(SQL_DATETIME_FORMAT);
			Date date = new Date();
			String dateTime = formatter.format(date);

			statement.setString(1, news.getTitle());
			statement.setString(2, dateTime);
			statement.setString(3, news.getBriefNews());
			statement.setString(4, news.getContent());
			statement.setInt(5, news.getIdNews());
			statement.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new NewsDAOException("error while working with database in method updateNews()", e);
		}
	}

	private static final String SQL_QUERY_DELETE_NEWS = "DELETE FROM news WHERE id=?";

	@Override
	public void deleteNewses(List<String> idNewses) throws NewsDAOException {

		try (Connection connection = CONNECTION_POOL.takeConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_QUERY_DELETE_NEWS)) {

			for (String s : idNewses) {
				try {
					statement.setInt(1, Integer.parseInt(s));
					statement.executeUpdate();
				} catch (NumberFormatException e) {
					throw new NewsDAOException("error during parseInt in dao", e);
				}
			}
		} catch (ConnectionPoolException e) {
			throw new NewsDAOException("error trying to take connection", e);
		} catch (SQLException e) {
			throw new NewsDAOException("error while working with database in method deleteNewses()", e);
		}
	}
}
