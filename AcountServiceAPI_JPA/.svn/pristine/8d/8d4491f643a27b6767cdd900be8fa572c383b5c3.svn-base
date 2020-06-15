package trong.lixco.com.util;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DatabaseName {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String note;
	@ManyToOne
	private Systemconfig systemconfig;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Systemconfig getSystemconfig() {
		return systemconfig;
	}
	public void setSystemconfig(Systemconfig systemconfig) {
		this.systemconfig = systemconfig;
	}
	
	

}
