package trong.lixco.com.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/*
 * Danh muc to/bo phan
 */
@Entity
public class Department extends AbstractEntity {
	private String code;
	private String name;
	@ManyToOne
	private Member member;// nhan vien phu trach
	@Transient
	private String codeMem;
	@ManyToOne
	private Department department;
	
	@ManyToOne
	private LevelDep levelDep;// cap phong ban

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String showAllNameDepart() {
		try {
			if (levelDep.getLevel() == 1) {
				return name;
			} else {
				String lt = "";
				for (int i = 0; i < levelDep.getLevel(); i++) {
					lt = lt + "&emsp;";
				}
				if (department != null)
					return lt + name;
				return name;
			}
		} catch (Exception e) {
			return name;
		}
	}

	public String showAllNameDepartFull() {
		try {
			if (levelDep.getLevel() == 1) {
				return name;
			} else {
				String lt = "";
				for (int i = 0; i < levelDep.getLevel(); i++) {
					lt = lt + "&emsp;";
				}
				if (department != null)
					return department.showAllNameDepartFull() + lt + name;
				return name;
			}
		} catch (Exception e) {
			return name;
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlTransient
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public LevelDep getLevelDep() {
		return levelDep;
	}

	public void setLevelDep(LevelDep levelDep) {
		this.levelDep = levelDep;
	}

	public String getCodeMem() {
		try {
			return member.getCode();
		} catch (Exception e) {
			return null;
		}
	}

	public void setCodeMem(String codeMem) {
		this.codeMem = codeMem;
	}
	
}
