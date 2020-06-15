package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/*
 * Danh muc chi nhanh
 */
@Entity
public class Branch extends AbstractEntity {
	private String code;
	private String name;
	private String urlLogin;
	private String nameConnect;
	@ManyToOne
	private Member member;//truong chi nhanh
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getUrlLogin() {
		return urlLogin;
	}
	public void setUrlLogin(String urlLogin) {
		this.urlLogin = urlLogin;
	}
	public String getNameConnect() {
		return nameConnect;
	}
	public void setNameConnect(String nameConnect) {
		this.nameConnect = nameConnect;
	}
}
