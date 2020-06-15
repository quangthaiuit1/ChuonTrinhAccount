package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class MemNoticeRela extends AbstractEntity {
	@ManyToOne
	private Member member;
	@ManyToOne
	private NoticeSystem noticeSystem;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public NoticeSystem getNoticeSystem() {
		return noticeSystem;
	}

	public void setNoticeSystem(NoticeSystem noticeSystem) {
		this.noticeSystem = noticeSystem;
	}

}
