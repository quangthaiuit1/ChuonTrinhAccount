package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserRight extends AbstractEntity {

	@ManyToOne
	private FormList formList;
	@ManyToOne
	private Role role;
	private boolean isAllow;
	private boolean isInsert;
	private boolean isUpdate;
	private boolean isDelete;
	public FormList getFormList() {
		return formList;
	}
	public void setFormList(FormList formList) {
		this.formList = formList;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public boolean isAllow() {
		return isAllow;
	}
	public void setAllow(boolean isAllow) {
		this.isAllow = isAllow;
	}
	public boolean isInsert() {
		return isInsert;
	}
	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}
	public boolean isUpdate() {
		return isUpdate;
	}
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	
}
