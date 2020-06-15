package trong.lixco.com.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Account extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private boolean isOnline = false;
	private boolean notice=false;
	private boolean isAdmin=false;

	@OneToOne
	private Member member;
	
	@OneToOne
	private PrivateConfig privateConfig;

	public Account (){
		this.privateConfig=new PrivateConfig();
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public PrivateConfig getPrivateConfig() {
		return privateConfig;
	}
	public void setPrivateConfig(PrivateConfig privateConfig) {
		this.privateConfig = privateConfig;
	}
	public boolean isNotice() {
		return notice;
	}
	public void setNotice(boolean notice) {
		this.notice = notice;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	


}
