package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bean.Utilisateur;

/* On est ici dans un EBJ session (les EJB entity ce sont les anciens java Bean, ex utilisateur)
 * et effectivement, un EJB session ne dispose que des m�thodes qu'ils offrent
 * La structure globale n'a pas chang�, 
 * on a les m�thodes qui permettent de cr�er ou rechercher un utilisateur
 * on mettra supprimer plus tard*/

//nous pr�cisons � notre conteneur que l'objet est un EJB de type Stateless. 
//Il suffit pour cela d'une annotation @Stateless
@Stateless
public class UtilisateurDao {
    private static final String JPQL_SELECT_PAR_EMAIL = "SELECT u FROM Utilisateur u WHERE u.email=:email";
    private static final String PARAM_EMAIL           = "email";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    // L'annotation ci-dessous permet d'injecter dans notre EJB une instance
    // d'EntityManager
    // (qui g�re les entity, bean)
    // d�pendant d'une unit� de persistance "bdd_PU" d�fini dans le fichier
    // persistence.xml
    // injecter est le terme qui d�crit le fait que le cycle de vie de l�objet
    // annot� est g�r� par le conteneur
    // cela signifie que nous n�avons plus besoin de nous occuper de la cr�ation
    // ni de l�initialisation de l�objet,
    // c�est le conteneur qui va le faire pour nous
    // en effet, ous faisons appel � des m�thodes de l'objet em mais � aucun
    // moment
    // nous ne cr�ons ni n'initialisons une instance d'objet
    @PersistenceContext( unitName = "bdd_PU" )
    private EntityManager       em;

    // Enregistrement d'un nouvel utilisateur
    // avant elle faisait appel � des m�thodes utilitaires pour la cr�ation
    // d'une requ�te
    // pr�par�e
    // et la lib�ration des ressources utilis�es
    // => elle est r�duite � une seule et unique m�thode : persist()
    // C'est cette m�thode de l'EntityManager qui va se charger de tout
    // nous, nous avons uniquement besoin de lui transmettre notre entit�
    // Utilisateur
    public void creer( Utilisateur utilisateur ) throws DAOException {
        try {
            em.persist( utilisateur );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    // Recherche d'un utilisateur � partir de son adresse email
    // notre EntityManager ne peut deviner le param�tre appliqu� dans la clause
    // where d'utilisateur
    // (La seule requ�te de lecture qu'il est possible de faire par d�faut,
    // c'est la plus basique, � savoir la recherche d�un �l�ment en se basant
    // sur sa cl� primaire)
    // nous devons �crire un minimum de SQL pour parvenir � nos fins
    // Toutefois, il n�est plus n�cessaire d��crire du SQL directement via JDBC
    // comme nous le faisions dans nos DAO
    // JPA nous facilite la t�che en encadrant notre travail, et nous propose
    // pour cela deux m�thodes diff�rentes :
    // le langage JPQL, et le syst�me Criteria.
    // Le plus simple � prendre en mains est le JPQL car il ressemble � SQL
    // il pr�sente une diff�rence majeure : il n'est pas utilis� pour interagir
    // avec la base de donn�es,
    // mais avec les entit�s de notre application.
    // Et c'est bien l� l'objectif de JPA : d�coupler l'application du syst�me
    // de stockage final
    // Rappel des constantes :
    // private static final String JPQL_SELECT_PAR_EMAIL =
    // "SELECT u FROM Utilisateur u WHERE u.email=:email";
    // on d�signe dans la requ�te l'objet cibl� par la requ�te : le bean
    // Utilisateur
    // on lui met un alias u
    // dans le where, on cible l'attribut email de l'objet Utilisateur
    // et on va lui affecter un param�tre avec :email
    // la m�thode setParameter permet de dire que dans la requete que le
    // param�tre email sera rempli avec
    // l'argument de la m�thode trouver email
    // private static final String PARAM_EMAIL = "email";
    // la m�thode getSingleResult() d�clenche un appel de la requ�te JPQL
    // et retourne l'objet utilisateur ce qui permet d'instancier utilisateur
    // cette m�thode indique aussi que la requ�te ne retourne qu'un seul
    // r�sultat
    // getResultList() est utilis� quand il y a plusieurs resultats
    // la m�thode renvoie une exception de type NoResultException qui doit �tre
    // catch� quand il n'y a pas de r�sultat
    public Utilisateur trouver( String email ) throws DAOException {
        Utilisateur utilisateur = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_EMAIL );
        requete.setParameter( PARAM_EMAIL, email );
        try {
            utilisateur = (Utilisateur) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return utilisateur;
    }
}