/**
 * 
 */
package trong.lixco.com.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Notify {
	FacesContext context;

	public Notify(FacesContext context) {
		this.context = context;
	}

	// Lock
	public void lock() {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "�?ã khoá!", ""));
	}

	// Lock
	public void lock(String notice) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "�?ã khoá!", notice));
	}

	// Thanh cong
	public void success(String notice) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công!", notice));
	}

	public void success() {

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công!", ""));
	}

	// Canh bao
	public void warning(String notice) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cảnh báo!", notice));
	}

	// Canh bao
	public void warning(String title, String notice) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, title, notice));
	}

	// Xay ra loi khong xac dinh
	public void error(String notice) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Xảy ra lỗi!", notice));
	}

	public void error() {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Xảy ra lỗi", ""));
	}

	public void message(String notice1, String notice2) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notice1, notice2));
	}

	public void message(String notice1) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, notice1, ""));
	}
}
