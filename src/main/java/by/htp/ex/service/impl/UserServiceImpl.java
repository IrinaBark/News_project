package by.htp.ex.service.impl;

import by.htp.ex.bean.NewUserInfo;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.DaoProvider;
import by.htp.ex.dao.IUserDAO;
import by.htp.ex.service.ServiceException;
import by.htp.ex.util.validation.ValidationException;
import by.htp.ex.util.validation.impl.UserValidator;
import by.htp.ex.util.validation.impl.UserValidator.UserValidationBuilder;
import by.htp.ex.service.IUserService;

public class UserServiceImpl implements IUserService {

	private final IUserDAO userDAO = DaoProvider.getInstance().getUserDao();

	private static final String QUEST_ROLE = "guest";

	@Override
	public String signIn(String login, String password) throws ServiceException, ValidationException {
		
		UserValidationBuilder userValidationBuilder = new UserValidator.UserValidationBuilder();
		UserValidator validator = userValidationBuilder.checkLogin(login).checkPassword(password).validate();

		if (!validator.getErrors().isEmpty()) {
			throw new ValidationException(validator.buildErrorMessage());
		}

		try {
			if (userDAO.logination(login, password)) {
				return userDAO.getRole(login, password);
			} else {
				return QUEST_ROLE;
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean registration(NewUserInfo user) throws ServiceException, ValidationException {

		String login = user.getLogin();
		String password = user.getPassword();
		String email = user.getEmail();
		String name = user.getName();
		String surname = user.getSurname();
		String birthday = user.getBirthday();
		
		UserValidationBuilder userValidationBuilder = new UserValidator.UserValidationBuilder();
		UserValidator validator = userValidationBuilder.checkLogin(login).checkPassword(password).checkEmail(email)
				.checkName(name).checkSurname(surname).checkBirthday(birthday).validate();

		if (!validator.getErrors().isEmpty()) {
			throw new ValidationException(validator.buildErrorMessage());
		}

		try {
			userDAO.registration(user);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return true;
	}
}
