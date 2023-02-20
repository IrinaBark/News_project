package by.htp.ex.util.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.htp.ex.util.validation.IValidationBuilder;

public class NewsValidator {

	private List<String> errors;

	private NewsValidator(NewsValidationBuilder b) {
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
		NewsValidator other = (NewsValidator) obj;
		return Objects.equals(errors, other.errors);
	}

	public static class NewsValidationBuilder implements IValidationBuilder<NewsValidator> {
		private List<String> errors = new ArrayList<String>();

		private static final String REGEX_FOR_DATE = "(0?[1-9]|[12]\\d|30|31)[^\\w\\d\\r\\n:](0?[1-9]|1[0-2])[^\\w\\d\\r\\n:](\\d{4}|\\d{2})";
		private static final String REGEX_FOR_TITLE = "^[a-zA-Z0-9-]{5,25}$";
		private static final String REGEX_FOR_BRIEF = "^[a-zA-Z0-9-]{10,80}$";
		private static final String REGEX_FOR_CONTENT = "^[a-zA-Z0-9-]{10,}$";

		private static final Pattern DATE_PATTERN = Pattern.compile(REGEX_FOR_DATE);
		private static final Pattern TITLE_PATTERN = Pattern.compile(REGEX_FOR_TITLE);
		private static final Pattern BRIEF_PATTERN = Pattern.compile(REGEX_FOR_BRIEF);
		private static final Pattern CONTENT_PATTERN = Pattern.compile(REGEX_FOR_CONTENT);

		private static final String INVALID_TITLE_ERROR = "local.errorValidation.name.invalid_title";
		private static final String INVALID_DATE_ERROR = "local.errorValidation.name.invalid_date";
		private static final String INVALID_BRIEF_ERROR = "local.errorValidation.name.invalid_brief";
		private static final String INVALID_CONTENT_ERROR = "local.errorValidation.name.invalid_content";

		public NewsValidationBuilder checkTitle(String title) {
			Matcher titleMatcher = TITLE_PATTERN.matcher(title);
			if (!titleMatcher.matches()) {
				errors.add(INVALID_TITLE_ERROR);
			}
			return this;
		}

		public NewsValidationBuilder checkDate(String date) {
			Matcher dateMatcher = DATE_PATTERN.matcher(date);
			if (!dateMatcher.matches()) {
				errors.add(INVALID_DATE_ERROR);
			}
			return this;
		}

		public NewsValidationBuilder checkBrief(String brief) {
			Matcher briefMatcher = BRIEF_PATTERN.matcher(brief);
			if (!briefMatcher.matches()) {
				errors.add(INVALID_BRIEF_ERROR);
			}
			return this;
		}

		public NewsValidationBuilder checkContent(String content) {
			Matcher contentMatcher = CONTENT_PATTERN.matcher(content);
			if (!contentMatcher.matches()) {
				errors.add(INVALID_CONTENT_ERROR);
			}
			return this;
		}

		@Override
		public NewsValidator validate() {
			return new NewsValidator(this);
		}
	}

	private final static String DELIMITER = ";";

	public String buildErrorMessage() {
		StringBuilder errorMessage = new StringBuilder();
		for (String error : this.errors) {
			errorMessage.append(error + DELIMITER);
		}
		errorMessage.deleteCharAt(errorMessage.lastIndexOf(DELIMITER));
		return errorMessage.toString();
	}
}
