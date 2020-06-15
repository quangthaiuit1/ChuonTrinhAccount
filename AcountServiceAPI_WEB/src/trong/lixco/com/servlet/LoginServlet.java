package trong.lixco.com.servlet;

import java.io.Closeable;
import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trong.lixco.com.entity.Account;
import trong.lixco.com.entity.KeyLogin;
import trong.lixco.com.impl.ImplAccount;
import trong.lixco.com.service.KeyLoginService;
import trong.lixco.com.util.Encode;
import trong.lixco.com.util.rq.CommonModel;
import trong.lixco.com.util.rq.CommonService;

@WebServlet(name = "loginServlet", urlPatterns = { "/loginServlet/*" })
public class LoginServlet extends HttpServlet {

	@EJB
	private ImplAccount accountService;
	@Inject
	private KeyLoginService keyLoginService;

	protected void processRequestPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String username = (String) Encode.fromString(request.getParameter("user"));
			String password = (String) Encode.fromString(request.getParameter("pass"));
			String codeBranch = request.getParameter("branch");
			String urlct = request.getParameter("urlct");
			
			// kiem tra pass va user
			Account account = accountService.find_User_Pass(username, password);
			if (account != null) {
				long curenttime = System.currentTimeMillis();
				String key = Encode.toString(curenttime);
				KeyLogin kl = new KeyLogin(key, account,urlct);
				kl=keyLoginService.create(kl);

				CommonModel.prepareHeader(response, CommonModel.HEADER_TEXT_PAINT);
				String content =CommonService.FormatResponse(0,"", kl.getId());
				CommonModel.out(content, response);
				return;
			} else {
				CommonModel.prepareHeader(response, CommonModel.HEADER_JS);
				String content = CommonModel.toJson(-2, "Thông tin đăng nhập không đúng.", "");
				CommonModel.out(content, response);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String content = CommonModel.toJson(-2, "Exception throw!", "");
			CommonModel.prepareHeader(response, CommonModel.HEADER_JS);
			CommonModel.out(content, response);
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequestPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		processRequestPost(request, response);
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
			}
		}
	}

}
