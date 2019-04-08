package com;

/**
 * @author zn
 */
public class JiraSettingState {

	private String host;
	private String user;
	private String password;
	private int titleSize;
	private int contentSize;
	private String titleColor;
	private String contentColor;

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public String getContentColor() {
		return contentColor;
	}

	public void setContentColor(String contentColor) {
		this.contentColor = contentColor;
	}

	public int getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	public int getContentSize() {
		return contentSize;
	}

	public void setContentSize(int contentSize) {
		this.contentSize = contentSize;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public JiraSettingState(String host, String user, String password, int titleSize, int contentSize, String titleColor, String contentColor) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.titleSize = titleSize;
		this.contentSize = contentSize;
		this.titleColor = titleColor;
		this.contentColor = contentColor;
	}

	public JiraSettingState() {
	}
}
