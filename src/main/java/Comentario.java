
import java.time.LocalDate;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class Comentario {
    //Atributes
    private String texto;
    private LocalDate data;
    private Perfil autor;
    
    //Construction methods
    public Comentario(Perfil autor,String texto){
        this.texto = texto;
        this.autor = autor;
        this.data = LocalDate.now();
    }
    
    //Getters & Setters
    
    public String getTexto() {
        return texto;
    }

    public LocalDate getData() {
        return data;
    }

    public Perfil getAutor() {
        return autor;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setAutor(Perfil autor) {
        this.autor = autor;
    }
    
    //Utility methods
   
}
