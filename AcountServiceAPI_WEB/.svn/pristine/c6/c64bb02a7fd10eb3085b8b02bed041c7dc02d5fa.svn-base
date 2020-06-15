package trong.lixco.com.servlet;

 
import java.io.Closeable;
import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trong.lixco.com.entity.Member;
import trong.lixco.com.impl.ImplMember;
import trong.lixco.com.service.MemberService;
 
@WebServlet(name = "ImageServlet", urlPatterns = {"/ImageServlet/*"})
public class ImageServlet extends HttpServlet {
 
	@EJB
	private ImplMember memberService;
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
     
        String idString = request.getParameter("id");
 
        if (idString == null || idString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
 
        Long id = Long.parseLong(idString.trim());
        
 
        Member entry = null;
        try {
            entry =  memberService.findId(id);
        } catch (Exception ex) {
        }
 
        if (entry == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
 
        ServletOutputStream out = null;
        try {
            response.reset();
            out = response.getOutputStream();
            if (entry.getSign() != null && entry.getSign().length != 0) {
                out.write(entry.getSign());
            }
        } catch (IOException e) {
        } finally {
            close(out);
        }
 
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
