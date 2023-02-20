package by.htp.ex.service;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.util.validation.ValidationException;

public interface IUserService {
	
	String signIn(String login, String password) throws ServiceException, ValidationException;
	boolean registration(NewUserInfo user) throws ServiceException, ValidationException;

}
