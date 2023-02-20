package by.htp.ex.service.impl;

import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.NewsDAOException;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.ValidationException;
import by.htp.ex.util.validation.impl.NewsValidator;
import by.htp.ex.util.validation.impl.NewsValidator.NewsValidationBuilder;

public class NewsServiceImpl implements INewsService {

	private final INewsDAO newsDAO = DaoProvider.getInstance().getNewsDAO();

	@Override
	public int save(String title, String date, String brief, String content) throws ServiceException, ValidationException {
		
		int id = 0;

		NewsValidationBuilder newsValidationBuilder = new NewsValidator.NewsValidationBuilder();
		NewsValidator newsValidator = newsValidationBuilder.checkTitle(title).checkDate(date).checkBrief(brief)
				.checkContent(content).validate();

		if (!newsValidator.getErrors().isEmpty()) {
			throw new ValidationException(newsValidator.buildErrorMessage());
		}
		News news = new News(title, date, brief, content);
		try {
			id = newsDAO.addNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
		return id;
	}

	@Override
	public void find(News news) throws ServiceException {
		// TODO Auto-generated method stub

	}
	@Override
	public void update(News news) throws ServiceException, ValidationException {

		String title = news.getTitle();
		String date = news.getNewsDate();
		String brief = news.getBriefNews();
		String content = news.getContent();

		NewsValidationBuilder newsValidationBuilder = new NewsValidator.NewsValidationBuilder();
		NewsValidator newsValidator = newsValidationBuilder.checkTitle(title).checkDate(date).checkBrief(brief)
				.checkContent(content).validate();

		if (!newsValidator.getErrors().isEmpty()) {
			throw new ValidationException(newsValidator.buildErrorMessage());
		}
		try {
			newsDAO.updateNews(news);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> latestList(int count) throws ServiceException {

		try {
			return newsDAO.getLatestsList(5);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<News> list() throws ServiceException {
		try {
			return newsDAO.getList();
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public News findById(int id) throws ServiceException {
		try {
			return newsDAO.fetchById(id);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(List<String> idNewses) throws ServiceException {
		try {
			newsDAO.deleteNewses(idNewses);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}
}
