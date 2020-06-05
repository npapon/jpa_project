package servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Utilisateur;
import constante.Adressesinternes;
import constante.AttributsServlet;
import dao.UtilisateurDao;
import formulaire.InscriptionForm;

@WebServlet( "/inscription" )
public class InscriptionServlet extends HttpServlet {
    // on injecte notre EJB
    @EJB

    /*
     * Mais alors que nous devions initialiser manuellement le contenu de notre
     * objet utilisateurDao // dans notre version précédente, cette fois nous
     * nous reposons entièrement sur le conteneur : // à aucun moment nous ne
     * créons ni n'initialisons une instance d'objet, // nous nous contentons
     * uniquement de sa déclaration et de son annotation !
     */
    private UtilisateurDao utilisateurDao;

    /*
     * On supprime tout ça :) public void init() throws ServletException {
     * 
     * utilisateurDao = ( (DAOFactory) this.getServletContext().getAttribute(
     * AttributsServlet.DAOFACTORY ) ) .getUtilisateurDao();
     * 
     * }
     */

    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( Adressesinternes.INSCRIPTION ).forward( request,
                response );

    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        InscriptionForm inscriptionForm = new InscriptionForm( utilisateurDao );

        Utilisateur utilisateur = inscriptionForm.inscrireUtilisateur( request );
        HttpSession session = request.getSession();

        if ( utilisateur != null ) {
            session.setAttribute( AttributsServlet.UTILISATEUR, utilisateur );

            response.sendRedirect( Adressesinternes.CONNEXION_COURT );
        } else {
            session.setAttribute( AttributsServlet.INSCRIPTIONFORM, inscriptionForm );
            response.sendRedirect( Adressesinternes.INSCRIPTION_COURT );
        }
    }

}
