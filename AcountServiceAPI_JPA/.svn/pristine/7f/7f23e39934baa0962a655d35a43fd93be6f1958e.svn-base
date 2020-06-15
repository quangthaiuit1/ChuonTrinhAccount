package trong.lixco.com.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class NoticeSystem extends AbstractEntity {
	@ManyToOne
	private Member member;
	@ManyToOne
	private Department department;
	private String title;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;                                                                                                                                                                                                                                                                                                                         
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	
}
