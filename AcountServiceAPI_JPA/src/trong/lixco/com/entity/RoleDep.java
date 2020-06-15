package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RoleDep extends AbstractEntity {
	@ManyToOne
	private Department department;
	@ManyToOne
	private Role role;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
