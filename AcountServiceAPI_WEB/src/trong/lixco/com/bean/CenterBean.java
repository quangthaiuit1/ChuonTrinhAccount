package trong.lixco.com.bean;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.DatabaseBranch;
import trong.lixco.com.entity.Program;
import trong.lixco.com.entity.SingleSignOn;
import trong.lixco.com.impl.ImplAccount;
import trong.lixco.com.impl.ImplMember;
import trong.lixco.com.impl.ImplProgram;
import trong.lixco.com.service.DatabaseBranchService;
import trong.lixco.com.service.DepartmentService;
import trong.lixco.com.util.Notify;

@ManagedBean
@ViewScoped
public class CenterBean {
	private Notify notify;
	private Account account;
	private String username, password;
	private String nameDB;
	private String nameDBFull;
	private Set<Program> programs;
	private String path;
	private boolean addressLocal = false;

	private List<Program> pros1;
	private List<Program> pros2;
	private List<Program> pros3;
	private List<Program> pros4;
	private List<Program> pros5;

	@Inject
	DatabaseBranchService branchService;

	@PostConstruct
	private void init() {
		checkAddressLocal();
		pros1 = programService.findIndex(1);
		pros2 = programService.findIndex(2);
		pros3 = programService.findIndex(3);
		pros4 = programService.findIndex(4);
		pros5 = programService.findIndex(5);
		try {
			DatabaseBranch dbr = branchService.findById(1l);
			nameDB = dbr.getName();
			if (nameDB.equals("HO CHI MINH")) {
				nameDBFull = "HỒ CHÍ MINH";
			} else if (nameDB.equals("BAC NINH")) {
				nameDBFull = "BẮC NINH";
			} else {
				nameDBFull = "BÌNH DƯƠNG";
			}
		} catch (Exception e) {
		}
	}

	@EJB
	private ImplAccount accountService;
	@Inject
	private DepartmentService departmentService;
	@EJB
	private ImplMember memberService;
	@EJB
	private ImplProgram programService;

	public boolean checkAddressLocal() {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			String ipAddress = request.getHeader("X-FORWARDED-FOR");// ip
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
				boolean temp = ipAddress.contains("192.168.");
				if (temp == false) {
					temp = ipAddress.contains("127.0.0.1");
				}
				addressLocal = temp;
				return temp;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}


	public String getNameDB() {
		return nameDB;
	}

	public void setNameDB(String nameDB) {
		this.nameDB = nameDB;
	}

	/**
	 * Compute the hash value to check for "real person" submission.
	 * 
	 * @param value
	 *            the entered value
	 * @return its hash value
	 */
	private String rpHash(String value) {
		int hash = 5381;
		if (value != null) {
			value = value.toUpperCase();
			for (int i = 0; i < value.length(); i++) {
				hash = ((hash << 5) + hash) + value.charAt(i);
			}
		}
		return String.valueOf(hash);
	}

	public void login() {
		notify = new Notify(FacesContext.getCurrentInstance());
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		// Kiem tra ma captcha
		if (addressLocal || rpHash(req.getParameter("securityReal")).equals(req.getParameter("securityRealHash"))) {
			account = accountService.find_User_Pass(username, password);
			FacesContext context = FacesContext.getCurrentInstance();
			path = context.getExternalContext().getRequestContextPath();
			try {
				if (account != null) {
					// Lay danh sach chuong trinh user duoc phep truy cap
					programs = accountService.findProgramByAccount(account);
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
					account.setNote("Đăng nhập lúc: " + sf.format(new Date()));
					account.setOnline(true);
					context.getExternalContext().getSessionMap().put("account", account);
					context.getExternalContext().getSessionMap().put("database", nameDB);
					context.getExternalContext().getSessionMap().put("urlct", null);
					accountService.update(account);
					System.out.println("[" + account.getUserName() + "] đăng nhập.");
					context.getExternalContext().redirect(path + "/pages/Start.jsf");
				} else {
					notify.error("Sai tên đăng nhập hoặc mật khẩu!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			notify.error("Mã bảo mật không đúng!");
		}

	}

	public void redirect(String value) {
		// Xac dinh tai khoan co duoc phep truy cap vao chuong trinh nay hay
		// khong
		FacesContext context = FacesContext.getCurrentInstance();
		account = (Account) context.getExternalContext().getSessionMap().get("account");
		programs = accountService.findProgramByAccount(account);
		Iterator<Program> iterator = programs.iterator();

		while (iterator.hasNext()) {
			Program pr = iterator.next();
			if (value.equals(pr.getName())) {
				try {
					if (!value.equals("hethong")) {
						// Kiem tra server da duoc bat hay chua
						String url = pr.getuRL();
						if (addressLocal) {
							url = pr.getServeraddress() + url;
						} else {
							url = pr.getServeraddressPublic() + url;
						}
						if (checkServer(url)) {
							SingleSignOn sg = accountService.createSSO(new SingleSignOn(account.getId() + ""));
							String nameDB = (String) context.getExternalContext().getSessionMap().get("database");
							String path = url + "?id=" + sg.getId() + "&database=" + nameDB;
							context.getExternalContext().redirect(path);
						} else {
							context.getExternalContext().redirect("../errorpage.jsf");
						}
					} else {

						String url = pr.getuRL();
						if (addressLocal) {
							url = pr.getServeraddress() + url;
						} else {
							url = pr.getServeraddressPublic() + url;
						}

						context.getExternalContext().redirect(url);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public boolean checkServer(String urlserver) {
		boolean status = false;
		try {
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(urlserver).openConnection();
			con.setRequestMethod("HEAD");
			int responseCode = con.getResponseCode();
			status = (200 <= responseCode && responseCode <= 399);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	public String redirect() {
		return "success";

	}

	public static void main(String[] args) {
		String urlserver = "http://erpbd.lixco.com:9190/kpi/authorServlet";
		boolean status = false;
		try {
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(urlserver).openConnection();
			con.setRequestMethod("HEAD");
			int responseCode = con.getResponseCode();
			status = (200 <= responseCode && responseCode <= 399);
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
	}

	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			String urlct = (String) context.getExternalContext().getSessionMap().get("urlct");
			context.getExternalContext().invalidateSession();
			String url = context.getExternalContext().getRequestContextPath() + "/index.jsf";
			try {
				if (account != null) {
					Account acc = accountService.findById(account.getId());
					acc.setOnline(false);
					accountService.update(acc);
					account = null;
				}
				if (urlct != null) {
					urlct=urlct.replace("index.htm", "");
					context.getExternalContext().redirect(urlct + "logout/");
				} else {
					context.getExternalContext().redirect(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Program> getPrograms() {
		return programs;
	}

	public void setPrograms(Set<Program> programs) {
		this.programs = programs;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isAddressLocal() {
		return addressLocal;
	}

	public void setAddressLocal(boolean addressLocal) {
		this.addressLocal = addressLocal;
	}

	public List<Program> getPros1() {
		return pros1;
	}

	public void setPros1(List<Program> pros1) {
		this.pros1 = pros1;
	}

	public List<Program> getPros2() {
		return pros2;
	}

	public void setPros2(List<Program> pros2) {
		this.pros2 = pros2;
	}

	public List<Program> getPros3() {
		return pros3;
	}

	public void setPros3(List<Program> pros3) {
		this.pros3 = pros3;
	}

	public List<Program> getPros4() {
		return pros4;
	}

	public void setPros4(List<Program> pros4) {
		this.pros4 = pros4;
	}

	public List<Program> getPros5() {
		return pros5;
	}

	public void setPros5(List<Program> pros5) {
		this.pros5 = pros5;
	}

	public String getNameDBFull() {
		return nameDBFull;
	}

	public void setNameDBFull(String nameDBFull) {
		this.nameDBFull = nameDBFull;
	}

}
