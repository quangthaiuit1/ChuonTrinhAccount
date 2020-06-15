package trong.lixco.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class KeyLogin {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	protected Long id;
	private String keyAuth;
	private String urlct;
	@ManyToOne
	private Account account;
	public KeyLogin(){
	}
	public KeyLogin(String keyAuth, Account account,String urlct){
		this.keyAuth=keyAuth;
		this.account=account;
		this.urlct=urlct;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKeyAuth() {
		return keyAuth;
	}
	public void setKeyAuth(String keyAuth) {
		this.keyAuth = keyAuth;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String getUrlct() {
		return urlct;
	}
	public void setUrlct(String urlct) {
		this.urlct = urlct;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof KeyLogin)) {
			return false;
		}
		KeyLogin other = (KeyLogin) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
