import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class Publicacion {
    //Atributes
    LocalDate data;
    String texto;
    Perfil autor;
    ArrayList <Perfil> likes;
    ArrayList <Comentario> comments;
    
    //Construction methods
    /**
     * 
     * @param autor
     * @param texto 
     * 
     * Constructor, author will be current, date is stored. 
     * Text will be user inputed.
     * 
     * All will be created on call when added to users inbox
     */
    public Publicacion(Perfil autor, String texto){
        this.data = LocalDate.now();
        this.texto = texto;
        this.autor = autor;
        this.likes = new ArrayList();
        this.comments = new ArrayList();
    }
    
    //Getters & Setters
    public LocalDate getData() {
        return data;
    }

    public String getTexto() {
        return texto;
    }

    public Perfil getAutor() {
        return autor;
    }
    
    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setAutor(Perfil autor) {
        this.autor = autor;
    } 
    
    //Utility methods

    

    
    
}
