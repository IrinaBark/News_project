package by.htp.ex.bean;

import java.io.Serializable;
import java.util.Objects;

public class NewUserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String surname;
	private String email;
	private String login;
	private String password;
	private String birthday;

	public NewUserInfo(String name, String surname, String email, String login, String password, String birthday) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.login = login;
		this.password = password;
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthday, email, login, name, password, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewUserInfo other = (NewUserInfo) obj;
		return Objects.equals(birthday, other.birthday) && Objects.equals(email, other.email)
				&& Objects.equals(login, other.login) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(surname, other.surname);
	}



}
