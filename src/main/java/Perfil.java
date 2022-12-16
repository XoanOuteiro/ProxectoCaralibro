
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
    ArrayList<Perfil> friendList;
    ArrayList<String> friendRequest;
    ArrayList<Publicacion> inbox;
    ArrayList<Mensaxe> msgbox;

    //Data
    CaraLibroBD data;

    //Construction methods
    public Perfil(String nome, String contrasinal) {
        this.nome = nome;
        this.contrasinal = contrasinal;
        this.friendList = new ArrayList<>();
        this.friendRequest = new ArrayList<>();
        this.inbox = new ArrayList();
        this.msgbox = new ArrayList();
    }

    /**
     *
     * @param nome
     * @param contrasinal
     * @param estado
     * @param biografia
     *
     * CT users construction method.
     */
    public Perfil(String nome, String contrasinal, String estado, String biografia) {
        this.nome = nome;
        this.contrasinal = contrasinal;
        this.estado = estado;
        this.biography = biografia;
        this.friendList = new ArrayList<>();
        this.friendRequest = new ArrayList<>();
        this.inbox = new ArrayList();
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
    public void engadirAmigo(Perfil perfil) {
        this.friendList.add(perfil);
    }

    /**
     *
     * @param perfilSolicitante
     *
     * Adds self name to users friendRequestList
     */
    public void engadirSolicitudeDeAmistade(Perfil perfilSolicitante) {
        perfilSolicitante.friendRequest.add(nome);
    }

    /**
     *
     * @param perfilSolicitante
     *
     * Adds user with name on friendRequestList to friendList, adds self to
     * users friendList and deletes friendRequest.
     */
    public void aceptarSolicitudeDeAmistade(Perfil perfilSolicitante) {
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
    public void rexeitarSolicitudeDeAmistade(Perfil perfilSolicitante) {
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
        for (int cycle = 0; cycle < this.friendList.size(); cycle++) {
            if (this.friendList.get(cycle).getNome().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public void eliminarAmigo(Perfil user){
        this.friendList.remove(user);
        user.friendList.remove(this);
    }

    /**
     *
     * @param pub
     *
     * Adds given post to selfs inbox
     */
    public void engadirPublicacion(Publicacion pub) {
        this.inbox.add(pub);
    }

    /**
     *
     *
     *
     */
    public void engadirMensaxePrivada(Mensaxe m) {
        this.msgbox.add(m);
    }

    public void sendMssg(Perfil reciever, Mensaxe m) {
        reciever.msgbox.add(m);
        this.engadirMensaxePrivada(m);
    }

    /**
     *
     *
     *
     */
    public void eliminarMensaxe(Mensaxe m) {
        this.msgbox.remove(m);
    }

    public void deleteBoth(Mensaxe m, Perfil p) {
        eliminarMensaxe(m);
        p.eliminarMensaxe(m);
    }

    /**
     *
     *
     *
     */
    public Mensaxe getMssg(int post) {
        return this.msgbox.get(post);
    }

    /**
     *
     */
    public int getChatSizeOf(Perfil target) {
        int mssgAmm = 0;
        System.out.println("pepe1");
        for (int i = 0; i < this.msgbox.size() && i < target.msgbox.size(); i++) {
            if (this.msgbox.get(i).getRemitente().getNome().equals(target.getNome()) || target.msgbox.get(i).getRemitente().getNome().equals(this.nome)) {
                mssgAmm++;
            }
        }

        return mssgAmm;

    }
}
