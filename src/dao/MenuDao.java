package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bean.Menu;

@Stateless
public class MenuDao {

    private static final String JPQL_SELECT_MENU = "select m from Menu m where actif ='A' order by ordre asc";

    @PersistenceContext( unitName = "bdd_PU" )
    private EntityManager       em;

    public List<Menu> rechercher() throws DAOException {

        List<Menu> menuComplet = new ArrayList<Menu>();
        Query requete = em.createQuery( JPQL_SELECT_MENU );

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            menuComplet = (List<Menu>) requete.getResultList();

        } catch ( NoResultException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );

        }
        return menuComplet;
    }

}
