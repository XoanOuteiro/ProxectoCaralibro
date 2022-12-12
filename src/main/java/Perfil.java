
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
    
    //Non-essential atributes
    String biography;
    ArrayList <Perfil> friendList;
    ArrayList <String> friendRequest;
    ArrayList <Publicacion> inbox;
    
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

    public String getBiography() {
        return biography;
    }
    
    //Utility methods
    
    /**
     * 
     * @param perfil 
     * 
     * Copies given profile onto selfs friendList
     */
    public void engadirAmigo(Perfil perfil){
        this.friendList.add(perfil);
    }
    
    /**
     * 
     * @param perfilSolicitante 
     * 
     * Adds self name to users friendRequestList
     */
    public void engadirSolicitudeDeAmistade(Perfil perfilSolicitante){
        perfilSolicitante.friendRequest.add(nome);
    }
    
    /**
     * 
     * @param perfilSolicitante
     * 
     * Adds user with name on friendRequestList to friendList, adds
     * self to users friendList and deletes friendRequest.
     */
    public void aceptarSolicitudeDeAmistade(Perfil perfilSolicitante){
        this.friendList.add(perfilSolicitante);                               //Add to friendlist
        perfilSolicitante.engadirAmigo(this);                                 //Add self to opposite friendList
        this.friendRequest.remove(perfilSolicitante.getNome());               //Remove from friendRequestList
    }
    
    /**
     * 
     * @param perfilSolicitante 
     * 
     * Deletes username String from current friendRequestList
     */
    public void rexeitarSolicitudeDeAmistade(Perfil perfilSolicitante){
        this.friendRequest.remove(perfilSolicitante.getNome());               //Remove from friendRequestList
    }
    
    /**
     * 
     * @param name
     * @return boolean
     * 
     * Searches in current friendlist if a contained user has given name,
     * returns true if it does, false if it doesnt.
     */
    public boolean friendListContainsName(String name) {
        for (int cycle = 0; cycle < this.friendList.size(); cycle++){
            if (this.friendList.get(cycle).getNome().equals(name)){
                return true;
            }
        }
        return false;
    }
  
}
