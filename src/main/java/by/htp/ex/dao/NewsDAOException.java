package by.htp.ex.dao;

public class NewsDAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public NewsDAOException() {
		super();
	}

	public NewsDAOException(String message) {
		super(message);
	}

	public NewsDAOException(String message, Exception exception) {
		super(message, exception);
	}

	public NewsDAOException(Exception e) {
		super(e);
	}

}
