
import java.time.LocalDate;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class Mensaxe {

    //Atributes
   private LocalDate data;
    private String texto;
    private boolean lido;
    private Perfil remitente;

    //Construction methods
    public Mensaxe(String texto, Perfil remitente) {
        this.data = LocalDate.now();
        this.lido = false;
        this.texto = texto;
        this.remitente = remitente;
    }

    //Getters & Setters
    public LocalDate getData() {
        return data;
    }

    public String getTexto() {
        return texto;
    }

    public Perfil getRemitente() {
        return remitente;
    }

    public boolean isLido() {
        return lido;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }
    //Utility methods
}
