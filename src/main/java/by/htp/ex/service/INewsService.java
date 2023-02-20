package by.htp.ex.service;

import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.util.validation.ValidationException;

public interface INewsService {
  int save(String title, String date, String brief, String content) throws ServiceException, ValidationException;
  void find(News news) throws ServiceException;
  void update(News news) throws ServiceException, ValidationException;
  void delete(List<String> idNews)  throws ServiceException;
  
  List<News> latestList(int count)  throws ServiceException;
  List<News> list()  throws ServiceException;
  News findById(int id) throws ServiceException;
}
