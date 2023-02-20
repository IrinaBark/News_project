package by.htp.ex.util.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.ex.util.validation.IValidationBuilder;

public class UserValidator {

	private List<String> errors;
	
	private UserValidator(UserValidationBuilder b) {
		this.errors = b.errors;
	}
	
	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(errors);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserValidator other = (UserValidator) obj;
		return Objects.equals(errors, other.errors);
	}

	public static class UserValidationBuilder implements IValidationBuilder<UserValidator>{
		
		private List<String> errors = new ArrayList<String>();
		
		private static final String REGEX_FOR_LOGIN = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
		private static final String REGEX_FOR_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
		private static final String REGEX_FOR_EMAIL = "^[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$";
		private static final String REGEX_FOR_NAME = "^[a-zA-Z]{1,10}$";	
		private static final String REGEX_FOR_SURMANE = "^[a-zA-Z]{1,15}$";
		private static final String REGEX_FOR_BIRTHDAY = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
		
		private static final Pattern LOGIN_PATTERN = Pattern.compile(REGEX_FOR_LOGIN);
		private static final Pattern PASSWORD_PATTERN = Pattern.compile(REGEX_FOR_PASSWORD);
		private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX_FOR_EMAIL);
		private static final Pattern NAME_PATTERN = Pattern.compile(REGEX_FOR_NAME);
		private static final Pattern SURNAME_PATTERN = Pattern.compile(REGEX_FOR_SURMANE);
		private static final Pattern BIRTHDAY_PATTERN = Pattern.compile(REGEX_FOR_BIRTHDAY);
		
		private static final String INVALID_LOGIN_ERROR = "local.errorValidation.name.invalid_login";
		private static final String INVALID_PASSWORD_ERROR = "local.errorValidation.name.invalid_password";
		private static final String INVALID_EMAIL_ERROR = "local.errorValidation.name.invalid_email";
		private static final String INVALID_NAME_ERROR = "local.errorValidation.name.invalid_name";
		private static final String INVALID_SURNAME_ERROR = "local.errorValidation.name.invalid_surname";
		private static final String INVALID_BIRTHDAY_ERROR = "local.errorValidation.name.invalid_birthday";
		
		public UserValidationBuilder checkLogin(String login) {
			Matcher loginMatcher = LOGIN_PATTERN.matcher(login);
			if (!loginMatcher.matches()) {
				errors.add(INVALID_LOGIN_ERROR);
			}
			return this;
		}
		
		public UserValidationBuilder checkPassword(String password) {
			Matcher passwordMatcher = PASSWORD_PATTERN.matcher(password);
			if(!passwordMatcher.matches()) {
				errors.add(INVALID_PASSWORD_ERROR);
			}
			return this;
		}
		
		public UserValidationBuilder checkEmail(String email) {
			Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
			if(!emailMatcher.matches()) {
				errors.add(INVALID_EMAIL_ERROR);
			}
			return this;
		}
		
		public UserValidationBuilder checkName(String name) {
			Matcher nameMatcher = NAME_PATTERN.matcher(name);
			if (!nameMatcher.matches()) {
				errors.add(INVALID_NAME_ERROR);
			}
			return this;
		}
		
		public UserValidationBuilder checkSurname(String surname) {
			Matcher surnameMatcher = SURNAME_PATTERN.matcher(surname);
			if (!surnameMatcher.matches()) {
				errors.add(INVALID_SURNAME_ERROR);
			}
			return this;
		}
		
		public UserValidationBuilder checkBirthday(String birthday) {
			Matcher birthdayMatcher = BIRTHDAY_PATTERN.matcher(birthday);
			if (!birthdayMatcher.matches()) {
				errors.add(INVALID_BIRTHDAY_ERROR);
			}
			return this;
		}

		@Override
		public UserValidator validate() {
			return new UserValidator(this);
		}
		
	}
	private final static String DELIMITER = ";";
	
	public String buildErrorMessage() {
		StringBuilder errorMessage = new StringBuilder();
		for (String error : errors) {
			errorMessage.append(error + DELIMITER);
		}
		errorMessage.deleteCharAt(errorMessage.lastIndexOf(DELIMITER));
		return errorMessage.toString();
	}

}
