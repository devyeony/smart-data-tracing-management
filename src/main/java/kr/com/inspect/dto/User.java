package kr.com.inspect.dto;

public class User {

	private String userid;
	private String pwd;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User(String userid, String pwd) {
		super();
		this.userid = userid;
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", pwd=" + pwd + "]";
	}

}
