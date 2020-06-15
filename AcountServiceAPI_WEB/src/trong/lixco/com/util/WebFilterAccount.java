package trong.lixco.com.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.KeyLogin;
import trong.lixco.com.entity.URLServerCenter;
import trong.lixco.com.impl.ImplAccount;
import trong.lixco.com.service.KeyLoginService;
import trong.lixco.com.service.URLServerCenterService;

@javax.servlet.annotation.WebFilter("/pages/*")
public class WebFilterAccount implements Filter {
	@EJB
	private ImplAccount accountService;
	@Inject
	KeyLoginService keyLoginService;
	@Inject
	URLServerCenterService urlServerCenterService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException,
			IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String loginURL = request.getContextPath() + "/index.jsf";
		String urlcenter = null;
		if (session != null) {
			setPathLink();
			Account account = null;
			String database = null;
			String key = request.getParameter("key");
			String codeBranch = request.getParameter("branch");
			if (key != null) {
				KeyLogin keyLogin = keyLoginService.findById(Long.parseLong(key));
				if (keyLogin != null) {
					urlcenter=keyLogin.getUrlct();
					account = keyLogin.getAccount();
					keyLoginService.delete(keyLogin);
				}
				if (account != null) {
					try {
						// Lay danh sach chuong trinh user duoc phep truy
						// cap
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
						account.setNote("Đăng nhập lúc: " + sf.format(new Date()));
						account.setOnline(true);
						session.setAttribute("account", account);
						session.setAttribute("database", codeBranch);
						session.setAttribute("urlct", urlcenter);
						accountService.update(account);
						response.sendRedirect(request.getRequestURL().toString());
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
				if (session.getAttribute("account") != null)
					account = (Account) session.getAttribute("account");
				if (session.getAttribute("database") != null)
					database = (String) session.getAttribute("database");
				boolean loggedIn = (account != null) && (database != null);
				boolean loginRequest = request.getRequestURI().equals(loginURL);
				if (loggedIn || loginRequest) {
					chain.doFilter(req, res);
				} else {
					response.sendRedirect(loginURL);
				}
			}

		} else {
			response.sendRedirect(loginURL);
		}
	}

	public void setPathLink() {
		URLServerCenter urlServerCenter = urlServerCenterService.findById(1l);
		if (urlServerCenter != null) {
			StaticPath.setPath(urlServerCenter.getPath());
		}
	}

	@Override
	public void destroy() {
		System.out.println("destroy");

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}