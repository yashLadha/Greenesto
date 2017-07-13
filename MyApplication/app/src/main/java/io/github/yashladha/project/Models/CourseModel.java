package io.github.yashladha.project.Models;

/**
 * Created by MyApplication on 7/13/17.
 */

public class CourseModel {
	private String courseName;
	private String domain;

	public CourseModel(String courseName, String domain) {
		this.courseName = courseName;
		this.domain = domain;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
