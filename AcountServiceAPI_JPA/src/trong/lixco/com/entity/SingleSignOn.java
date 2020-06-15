package trong.lixco.com.entity;

import javax.persistence.Entity;

@Entity
public class SingleSignOn extends AbstractEntity {
	private String value;

	public SingleSignOn(String value) {
		super();
		this.value = value;
	}
	public SingleSignOn() {
		super();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
