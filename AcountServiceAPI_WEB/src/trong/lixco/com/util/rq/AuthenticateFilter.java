package trong.lixco.com.util.rq;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import trong.lixco.com.entity.Account;
import trong.lixco.com.impl.ImplAccount;
import trong.lixco.com.util.Encode;

import com.google.gson.JsonObject;

@WebFilter("/api/login/*")
public class AuthenticateFilter implements Filter {

	@EJB
	private ImplAccount accountService;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// kiem tra co phai post k
		if (request.getMethod().equalsIgnoreCase("POST")) {
			try {
				HttpSession session = request.getSession();
				String userName = (String) Encode.fromString(request.getParameter("user"));
				String password = (String) Encode.fromString(request.getParameter("pass"));
				String brand = request.getParameter("brand");
				// kiem tra pass va user
				Account account = accountService.find_User_Pass(userName, password);
				if (account != null) {
					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
					account.setNote("Đăng nhập lúc: "+sf.format(new java.util.Date()));
					account.setOnline(true);
					session.setAttribute("account", account);
					session.setAttribute("database", brand);
					accountService.update(account);
					System.out.println("["+account.getUserName()+"] đăng nhập.");
					JsonObject j=new JsonObject();
					j.addProperty("account_id",account.getId());
					j.addProperty("brand",brand);
					String content =CommonService.FormatResponse(0,"", j);
					CommonModel.out(content, response);
					return;
				} else {
					CommonModel.prepareHeader(response, CommonModel.HEADER_JS);
					String content = CommonModel.toJson(-2, "Thông tin đăng nhập không đúng.",
							"");
					CommonModel.out(content, response);
					return;
				}
			} catch (Exception e) {
				String content = CommonModel.toJson(-2, "Exception throw!", "");
				CommonModel.prepareHeader(response, CommonModel.HEADER_JS);
				CommonModel.out(content, response);
			}
		} else {
			String content = CommonModel.toJson(-2, "not support http method", "");
			CommonModel.prepareHeader(response, CommonModel.HEADER_JS);
			CommonModel.out(content, response);
			return;
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
