package by.htp.ex.util.validation.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.ex.util.validation.IValidationBuilder;
import jakarta.servlet.http.HttpSession;

public class AccessValidator {
	private List<String> errors;

	private AccessValidator(AccessValidationBuilder b) {
		this.errors = b.errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public static class AccessValidationBuilder implements IValidationBuilder<AccessValidator> {
		private List<String> errors = new ArrayList<String>();

		private static final String ROLE_PARAM = "role";
		private static final String ADMIN_ROLE = "admin";
		private static final String EDITOR_ROLE = "editor";

		private static final String USER_PARAM = "user";
		private static final String USER_STATUS_NOT_ACTIVE = "not active";
		private static final String ERROR_ACCESS_DENIED = "local.errorValidation.name.access_denyed";
		private static final String ERROR_NOT_AUTHORIZED = "local.errorValidation.name.not_authorized";

		public AccessValidationBuilder checkPermission(HttpSession session) {
			if (session == null) {
				errors.add(ERROR_ACCESS_DENIED);
				return this;
			}
			String role = (String) session.getAttribute(ROLE_PARAM);

			if (!(ADMIN_ROLE.equals(role) || EDITOR_ROLE.equals(role))) {
				errors.add(ERROR_ACCESS_DENIED);
			}
			return this;
		}

		public AccessValidationBuilder checkAuthorization(HttpSession session) {
			if (session == null) {
				errors.add(ERROR_NOT_AUTHORIZED);
			} else {
				String userStatus = (String) session.getAttribute(USER_PARAM);
				if (USER_STATUS_NOT_ACTIVE.equals(userStatus) || userStatus == null) {
					errors.add(ERROR_NOT_AUTHORIZED);
				}
			}
			return this;
		}

		@Override
		public AccessValidator validate() {
			return new AccessValidator(this);
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
