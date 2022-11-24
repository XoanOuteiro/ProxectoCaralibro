/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class Perfil {
    //Atributes
    String nome;
    String contrasinal;
    String estado;
    
    //Construction methods
    public Perfil(String nome, String contrasinal){
        this.nome = nome;
        this.contrasinal = contrasinal;
    }
    
    //Getters & Setters
    
    public String getNome() {
        return nome;
    }

    public String getContrasinal() {
        return contrasinal;
    }

    public String getEstado() {
        return estado;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setContrasinal(String contrasinal) {
        this.contrasinal = contrasinal;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    //Utility methods
  
}
