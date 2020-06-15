package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RoleAcc extends AbstractEntity {
	@ManyToOne
	private Account account;
	@ManyToOne
	private Role role;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
