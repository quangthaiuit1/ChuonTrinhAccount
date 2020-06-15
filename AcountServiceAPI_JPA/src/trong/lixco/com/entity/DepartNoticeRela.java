package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DepartNoticeRela extends AbstractEntity {
	@ManyToOne
	private Department department;
	@ManyToOne
	private NoticeSystem noticeSystem;


	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public NoticeSystem getNoticeSystem() {
		return noticeSystem;
	}

	public void setNoticeSystem(NoticeSystem noticeSystem) {
		this.noticeSystem = noticeSystem;
	}

}
