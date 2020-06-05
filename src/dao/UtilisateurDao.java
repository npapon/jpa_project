package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bean.Utilisateur;

/* On est ici dans un EBJ session (les EJB entity ce sont les anciens java Bean, ex utilisateur)
 * et effectivement, un EJB session ne dispose que des méthodes qu'ils offrent
 * La structure globale n'a pas changé, 
 * on a les méthodes qui permettent de créer ou rechercher un utilisateur
 * on mettra supprimer plus tard*/

//nous précisons à notre conteneur que l'objet est un EJB de type Stateless. 
//Il suffit pour cela d'une annotation @Stateless
@Stateless
public class UtilisateurDao {
    private static final String JPQL_SELECT_PAR_EMAIL = "SELECT u FROM Utilisateur u WHERE u.email=:email";
    private static final String PARAM_EMAIL           = "email";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    // L'annotation ci-dessous permet d'injecter dans notre EJB une instance
    // d'EntityManager
    // (qui gère les entity, bean)
    // dépendant d'une unité de persistance "bdd_PU" défini dans le fichier
    // persistence.xml
    // injecter est le terme qui décrit le fait que le cycle de vie de l’objet
    // annoté est géré par le conteneur
    // cela signifie que nous n’avons plus besoin de nous occuper de la création
    // ni de l’initialisation de l’objet,
    // c’est le conteneur qui va le faire pour nous
    // en effet, ous faisons appel à des méthodes de l'objet em mais à aucun
    // moment
    // nous ne créons ni n'initialisons une instance d'objet
    @PersistenceContext( unitName = "bdd_PU" )
    private EntityManager       em;

    // Enregistrement d'un nouvel utilisateur
    // avant elle faisait appel à des méthodes utilitaires pour la création
    // d'une requête
    // préparée
    // et la libération des ressources utilisées
    // => elle est réduite à une seule et unique méthode : persist()
    // C'est cette méthode de l'EntityManager qui va se charger de tout
    // nous, nous avons uniquement besoin de lui transmettre notre entité
    // Utilisateur
    public void creer( Utilisateur utilisateur ) throws DAOException {
        try {
            em.persist( utilisateur );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    // Recherche d'un utilisateur à partir de son adresse email
    // notre EntityManager ne peut deviner le paramètre appliqué dans la clause
    // where d'utilisateur
    // (La seule requête de lecture qu'il est possible de faire par défaut,
    // c'est la plus basique, à savoir la recherche d’un élément en se basant
    // sur sa clé primaire)
    // nous devons écrire un minimum de SQL pour parvenir à nos fins
    // Toutefois, il n’est plus nécessaire d’écrire du SQL directement via JDBC
    // comme nous le faisions dans nos DAO
    // JPA nous facilite la tâche en encadrant notre travail, et nous propose
    // pour cela deux méthodes différentes :
    // le langage JPQL, et le système Criteria.
    // Le plus simple à prendre en mains est le JPQL car il ressemble à SQL
    // il présente une différence majeure : il n'est pas utilisé pour interagir
    // avec la base de données,
    // mais avec les entités de notre application.
    // Et c'est bien là l'objectif de JPA : découpler l'application du système
    // de stockage final
    // Rappel des constantes :
    // private static final String JPQL_SELECT_PAR_EMAIL =
    // "SELECT u FROM Utilisateur u WHERE u.email=:email";
    // on désigne dans la requête l'objet ciblé par la requête : le bean
    // Utilisateur
    // on lui met un alias u
    // dans le where, on cible l'attribut email de l'objet Utilisateur
    // et on va lui affecter un paramètre avec :email
    // la méthode setParameter permet de dire que dans la requete que le
    // paramètre email sera rempli avec
    // l'argument de la méthode trouver email
    // private static final String PARAM_EMAIL = "email";
    // la méthode getSingleResult() déclenche un appel de la requête JPQL
    // et retourne l'objet utilisateur ce qui permet d'instancier utilisateur
    // cette méthode indique aussi que la requête ne retourne qu'un seul
    // résultat
    // getResultList() est utilisé quand il y a plusieurs resultats
    // la méthode renvoie une exception de type NoResultException qui doit être
    // catché quand il n'y a pas de résultat
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