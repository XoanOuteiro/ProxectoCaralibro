
import java.util.ArrayList;
/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public final class CaraLibroBD {

    //Atributes
    static ArrayList<Perfil> base = new ArrayList();

    //Utility methods
    /**
     * 
     * @param novoPerfil 
     * 
     * Adds a new profile object to the static database
     */
    public static void engadirPerfil(Perfil novoPerfil) {                              //Adding requires a Perfil obj which we will -ALWAYS- create on call
        base.add(novoPerfil);
    }

    /**
     * 
     * @param nome
     * @param contrasinal
     * @return Perfil
     * 
     * This method looks for a user that matches both username and
     * password, then returns it. Returns null otherwise.
     */
    public static Perfil obterPerfil(String nome, String contrasinal) {                //Not a getter (use this to swap profiles as currentProfile = obterPerfil();)
        //We will search in our list for a coincidence index of 2 (2 trues)
        for (Perfil base : base) {
            if (base.getNome().equals(nome) && base.getContrasinal().equals(contrasinal)) {
                return base;
            }
        }
        return null;
    }

    /**
     * 
     * @param text
     * @return Perfil
     * 
     * Looks for a user based on its username and returns its object when found.
     * Returns null otherwise.
     */
    public static Perfil buscarPerfil(String text) {
        for (Perfil base : base) {
            if (base.getNome().equals(text)) {
                return base;
            }
        }
        return null;
    }
    

    /**
     * 
     * @param that
     * @return boolean
     * 
     * Looks for a user based on its name and returns true if it exists on 
     * arraylist or false if it doesnt.
     */
    public static boolean lookFor(String that) {
        for (Perfil base : base) {
            if (base.getNome().equals(that)) {
                return true;
            }
        }
        return false;
    }

    /**
     * CT command method:
     * Shows all users and their passwords
     */
    public static void pingUsers() {
        for (Perfil base : base) {
            System.out.println("CT:/ [@Ping] detected " + base.getNome() + " / " + base.getContrasinal());
        }

    }
    
    /**
     * CT command, more powerfull version of ping
     */
    public static void pingScrape() {
        for (Perfil base : base) {
            System.out.println("CT:/ [@Ping] detected " + base.getNome() + " / " + base.getContrasinal() + "\t &state: " + base.getEstado() + "\t &bio: " + base .getBiography() + "\t &reqSize/frLSize: " + base.friendRequest.size() + " / " + base.friendList.size());
        }

    }
    
    /**
     * CT command pings users and their posts quantitative data
     */
    public static void pingPoster(){
        for (Perfil base : base) {
            System.out.println("CT:/ [@Ping] detected " + base.getNome() + " / " + base.getContrasinal() + "\t &posts:" + base.inbox.size() + "\t &friends:" + base.friendList.size());
        }
    }
}
