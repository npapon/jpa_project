package constante;

public class RequetesSql {

    public final static String UTILISATEUR_SELECT_PAR_MAIL = "SELECT * FROM utilisateur WHERE email = ?";
    public final static String SESSION_SELECT_PAR_LOGIN    = "select * from utilisateur where login = ?";
    public final static String IMAGEPROFIL_SELECT_PAR_MAIL = "SELECT * FROM imageprofil WHERE email = ?";
    public final static String UTILISATEUR_INSERT          = "insert into utilisateur (login, email, mot_de_passe, nom, date_creation) values (?,?,?,?,now())";
    public final static String IMAGEPROFIL_INSERT          = "insert into imageprofil(libelle,email,date_creation,emplacement) values(?,?,now(),?)";
    public final static String IMAGEPROFIL_UPDATE          = "update imageprofil set libelle = ?,emplacement = ?, date_modification = now() where email = ?";
    public final static String IMAGEPROFIL_DELETE          = "delete from imageprofil where email = ?";
    public final static String UTILISATEUR_DELETE          = "delete from utilisateur where email = ?";
    public final static String MENU_SELECT                 = "select * from menu where actif ='A' order by ordre asc";
    public final static String ACHATBLOQUESV2_SELECT       = "select * from baact where INDACHMDD in ('O','N')";
    public final static String ACHATBLOQUEV2_UPDATE        = "update baact set INDACHMDD = null, USERID= ? ,DTEMOD = now() where IDEACH = ?";

}
