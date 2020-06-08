package bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "menu" )
public class Menu {

    @Column( name = "id" )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    int    id;

    @Column( name = "item" )
    String item;

    @Column( name = "description" )
    String description;

    @Column( name = "ordre" )
    int    ordre;
    @Column( name = "actif" )
    String actif;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem( String item ) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre( int ordre ) {
        this.ordre = ordre;
    }

    public String getActif() {
        return actif;
    }

    public void setActif( String actif ) {
        this.actif = actif;
    }

}
