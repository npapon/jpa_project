package formulaire;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import bean.Utilisateur;
import constante.Cryptage;
import constante.MessagesErreur;
import constante.MessagesSucces;
import constante.ParametresFormulaire;
import constante.Regex;
import dao.DAOException;
import dao.UtilisateurDao;
import exception.FormValidationException;

public class InscriptionForm {

    private UtilisateurDao utilisateurDao;
    private List<String>   erreurs = new ArrayList<String>();
    private String         creationAutorisee;

    public InscriptionForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public Utilisateur inscrireUtilisateur( HttpServletRequest request ) {
        String login = request.getParameter( ParametresFormulaire.LOGIN );
        String nom = request.getParameter( ParametresFormulaire.NOM );
        String email = request.getParameter( ParametresFormulaire.EMAIL );
        String motdepasse = request.getParameter( ParametresFormulaire.MOTDEPASSE );
        String confirmationmotdepasse = request.getParameter( ParametresFormulaire.CONFIRMATIONMOTDEPASSE );
        Timestamp dateCreation = new Timestamp( System.currentTimeMillis() );
        Utilisateur utilisateur = new Utilisateur();
        try {

            affecterLogin( login, utilisateur );
            affecterNom( nom, utilisateur );

            affecterEmail( email, utilisateur );

            verifierMotDePasse( motdepasse );
            affecterConfirmationMotDePasse( motdepasse, confirmationmotdepasse, utilisateur );
            affecterDate( dateCreation, utilisateur );

            if ( erreurs.isEmpty() ) {
                creationAutorisee = MessagesSucces.UTILISATEUR_CREE;
                utilisateurDao.creer( utilisateur );
                System.out.println( creationAutorisee );
            } else {
                creationAutorisee = MessagesErreur.CREATION_UTILISATEUR_KO;
                System.out.println( creationAutorisee );
                return null;

            }

        } catch ( DAOException e ) {
            creationAutorisee = MessagesErreur.CREATION_UTILISATEUR_KO_RAISON_BDD;
            System.out.println( creationAutorisee );
            e.printStackTrace();

        }

        return utilisateur;
    }

    private void affecterDate( Timestamp dateCreation, Utilisateur utilisateur ) {
        utilisateur.setDate_creation( dateCreation );

    }

    public void affecterLogin( String login, Utilisateur utilisateur ) {
        if ( login.length() <= 3 ) {
            try {
                throw new FormValidationException( MessagesErreur.LOGIN_TROP_COURT );
            } catch ( FormValidationException e ) {
                System.out.println( e.getMessage() );

                erreurs.add(
                        e.getMessage() );
            }
        } else {
            utilisateur.setLogin( login );
        }

    }

    public void affecterNom( String nom, Utilisateur utilisateur ) {
        if ( nom.length() <= 3 ) {
            try {
                throw new FormValidationException( MessagesErreur.NOM_TROP_COURT );
            } catch ( FormValidationException e ) {
                System.out.println( e.getMessage() );

                erreurs.add(
                        e.getMessage() );
            }
        } else {
            utilisateur.setNom( nom );
        }

    }

    public void affecterEmail( String email, Utilisateur utilisateur ) {
        if ( !email.matches( Regex.MAIL ) ) {
            try {
                throw ( new FormValidationException( MessagesErreur.EMAIL_FORMAT_INVALIDE ) );
            } catch ( FormValidationException e ) {
                {
                    System.out.println( e.getMessage() );
                    erreurs.add(
                            e.getMessage() );
                }
            }
        } else if ( utilisateurDao.trouver( email ) != null ) {
            try {
                throw ( new FormValidationException( MessagesErreur.EMAIL_DEJA_PRIS ) );
            } catch ( FormValidationException e ) {
                System.out.println( e.getMessage() );
                erreurs.add(
                        e.getMessage() );

            }

        } else {
            utilisateur.setEmail( email );
        }
    }

    public void verifierMotDePasse( String motdepasse ) {
        if ( motdepasse.length() <= 3 ) {
            try {
                throw new FormValidationException( MessagesErreur.MOTDEPASSE_TROP_COURT );
            } catch ( FormValidationException e ) {
                System.out.println( e.getMessage() );

                erreurs.add(
                        e.getMessage() );
            }
        }

    }

    public void affecterConfirmationMotDePasse( String motdepasse, String confirmationMotdepasse, Utilisateur utilisateur ) {

        if ( !confirmationMotdepasse.equals( motdepasse ) ) {
            try {
                throw ( new FormValidationException( MessagesErreur.CONFIRMATION_MOTPASSE_INCORRECTE ) );
            } catch ( FormValidationException e ) {
                System.out.println( e.getMessage() );
                erreurs.add( e.getMessage() );
            }
        } else {
            ConfigurablePasswordEncryptor configurablePasswordEncryptor = new ConfigurablePasswordEncryptor();
            configurablePasswordEncryptor.setAlgorithm( Cryptage.ALGO_CHIFFREMENT_SHA_256 );
            configurablePasswordEncryptor.setPlainDigest( false );
            String motdepassecrypte = configurablePasswordEncryptor.encryptPassword( motdepasse );
            utilisateur.setMot_de_passe( motdepassecrypte );
        }

    }

    public String getCreationAutorisee() {
        return creationAutorisee;
    }

    public void setCreationAutorisee( String creationAutorisee ) {
        this.creationAutorisee = creationAutorisee;
    }

    public List<String> getErreurs() {
        return erreurs;
    }

    public void setErreurs( List<String> erreurs ) {
        this.erreurs = erreurs;
    }

}
