package bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "Utilisateur" )
public class Utilisateur {

    @Column( name = "id" )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int       id;
    @Column( name = "login" )
    private String    login;
    @Column( name = "email" )
    private String    email;
    @Column( name = "mot_de_passe" )
    private String    mot_de_passe;
    @Column( name = "nom" )
    private String    nom;
    @Column( name = "date_creation" )
    private Timestamp date_creation;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin( String login ) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe( String mot_de_passe ) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation( Timestamp date_creation ) {
        this.date_creation = date_creation;
    }

    public String toString() {
        return "utilisateur id " + this.getId() + " utilisateur login " + this.getLogin();
    }

}
