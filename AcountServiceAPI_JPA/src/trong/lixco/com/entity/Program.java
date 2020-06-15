package trong.lixco.com.entity;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Program extends AbstractEntity {
	@Column(unique = true, nullable = false)
	private String fullname;
	private String name;
	private String description;
	private String uRL;
	private String serveraddress;
	private String serveraddressPublic;
	private byte[] logo;
	private short indexProgram;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getuRL() {
		return uRL;
	}

	public void setuRL(String uRL) {
		this.uRL = uRL;
	}

	public String getServeraddress() {
		return serveraddress;
	}

	public void setServeraddress(String serveraddress) {
		this.serveraddress = serveraddress;
	}

	public String getServeraddressPublic() {
		return serveraddressPublic;
	}

	public void setServeraddressPublic(String serveraddressPublic) {
		this.serveraddressPublic = serveraddressPublic;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public short getIndexProgram() {
		return indexProgram;
	}

	public void setIndexProgram(short indexProgram) {
		this.indexProgram = indexProgram;
	}
	public String logoString() {
		try {
			return new String(Base64.getEncoder().encodeToString(logo));
		} catch (Exception e) {
			return "";
		}
		
	}
	
}
