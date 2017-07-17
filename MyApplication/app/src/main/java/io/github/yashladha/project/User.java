package io.github.yashladha.project;

import java.io.Serializable;

public class User implements Serializable {
	private String email;
	private String uid;
	private String option;

	public User(String email, String uid, String option) {
		this.email = email;
		this.uid = uid;
		this.option = option;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public User() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}
}
