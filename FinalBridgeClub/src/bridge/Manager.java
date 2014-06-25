package bridge;
 
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bridge.User.Status;

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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = GetterFromDB.getUser(username, password);
		if(user != null){
			s.setAttribute("user", user);
			Table t = (Table)request.getServletContext().getAttribute("table");
			user.setTable(t);
			response.sendRedirect("index.jsp");
		}else{
			s.setAttribute("incorrect", "yes");
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		HttpSession s = request.getSession();
		if(GetterFromDB.isExistedUser(username)){
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
			if(number==null) number = "";
			if(birthdate==null) birthdate = "1900-01-01";
			if(sex==null) sex = "unknown";
			SaverInDB.userRegistration(username, password, fname, lname, mail, number, birthdate, sex, Status.USER);
			User curUser = GetterFromDB.getUser(username, password);
			Table t = (Table)request.getServletContext().getAttribute("table");
			curUser.setTable(t);
			s.setAttribute("user", curUser);
			response.sendRedirect("index.jsp");
		}
	}

}
