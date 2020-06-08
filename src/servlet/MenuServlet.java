package servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Menu;
import constante.Adressesinternes;
import constante.AttributsServlet;
import dao.MenuDao;

@WebServlet( "/menu" )
public class MenuServlet extends HttpServlet {
    @EJB
    public MenuDao menuDao;

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        List<Menu> menu = menuDao.rechercher();
        ServletContext context = getServletContext();
        context.setAttribute( AttributsServlet.MENU, menu );
        this.getServletContext().getRequestDispatcher( Adressesinternes.MENU ).forward( request,
                response );
    }

}
