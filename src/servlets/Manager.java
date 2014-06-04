package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Manager
 */
@WebServlet("/Manager")
public class Manager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Manager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession s = request.getSession();
		String logout = request.getParameter("logout");
		if(logout!=null){
			s.removeAttribute("username");
			s.removeAttribute("incorrect");
			s.removeAttribute("exists");
			s.invalidate();
			response.sendRedirect("login.jsp");
		}else{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if(AccountManager.accountExists(username, password)){
				s.setAttribute("username", username);
				response.sendRedirect("table.jsp");
			}else{
				s.setAttribute("incorrect", "yes");
				response.sendRedirect("login.jsp");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		HttpSession s = request.getSession();
		if(AccountManager.nameExists(username)){
			s.setAttribute("exists", "yes");
			response.sendRedirect("register.jsp");
		}else{
			String password = request.getParameter("password");
			String fname = request.getParameter("firstname");
			String lname = request.getParameter("lastname");
			String mail = request.getParameter("mail");
			String number = request.getParameter("number");
			String birthdate = request.getParameter("bdate");
			String sex = request.getParameter("sex");
			AccountManager.createAccount(username, password);
			s.setAttribute("username", username);
			response.sendRedirect("table.jsp");
		}
	}

}
