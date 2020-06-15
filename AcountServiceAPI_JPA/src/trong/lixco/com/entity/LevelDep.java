package trong.lixco.com.entity;

import javax.persistence.Entity;

/*
 * Danh muc cap bậc/ vi tri
 */
@Entity
public class LevelDep extends AbstractEntity {
	private String code;
	private String name;
	private int level;

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
