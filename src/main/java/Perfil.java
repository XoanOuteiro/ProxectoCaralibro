
import java.util.ArrayList;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class Perfil {
    //Atributes
    String nome;
    String contrasinal;
    String estado;
    
    //Non-necessary atributes
    String biography;
    ArrayList <Perfil> friendList;
    ArrayList <String> friendRequest;
    
    //Data
    CaraLibroBD data;
    
    //Construction methods
    public Perfil(String nome, String contrasinal){
        this.nome = nome;
        this.contrasinal = contrasinal;
        this.friendList = new ArrayList<>();
        this.friendRequest = new ArrayList<>();
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

    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    
    //Utility methods

    public String getBiography() {
        return biography;
    }
    
    public void engadirAmigo(Perfil perfil){
        
    }
    
    public void engadirSolicitudeDeAmistade(Perfil perfilSolicitante){
        perfilSolicitante.friendRequest.add(getNome());
    }
    
    public void aceptarSolicitudeDeAmistade(Perfil perfilSolicitante){
        this.friendList.add(perfilSolicitante);                               //Add to friendlist
        perfilSolicitante.friendList.add(this);                               //Add self to opposite friendList
        this.friendRequest.remove(perfilSolicitante.getNome());               //Remove from friendRequestList
    }
    
    public void rexeitarSolicitudeDeAmistade(Perfil perfilSolicitante){
        this.friendRequest.remove(perfilSolicitante.getNome());               //Remove from friendRequestList
    }
    
    public Perfil retrieveUser(String name) {
        return this.data.buscarPerfil(name);
    }
  
}
