
import java.util.ArrayList;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class CaraLibroBD {

    //Atributes
    ArrayList<Perfil> base;

    //Construction methods
    public CaraLibroBD() {
        this.base = new ArrayList<Perfil>();
    }

    //Getters & Setters
    //Utility methods
    public void engadirPerfil(Perfil novoPerfil) {                              //Adding requires a Perfil obj which we will -ALWAYS- create on call
        this.base.add(novoPerfil);
    }

    /*
    Returns a profile if and only if 
    it matches given Strings in the fields of 
    name and password.
    */
    public Perfil obterPerfil(String nome, String contrasinal) {                //Not a getter (use this to swap profiles as currentProfile = obterPerfil();)
        //We will search in our list for a coincidence index of 2 (2 trues)
        for (Perfil base : this.base) {
            if (base.getNome().equals(nome) && base.getContrasinal().equals(contrasinal)) {
                return base;
            }
        }
        return null;
    }
    
    /*
    Returns true if the given String is contained 
    as a name in current database, returns false otherwise.
    */
    public boolean lookFor(String that) {
        for (Perfil base : this.base) {
            if (base.getNome().equals(that)) {
                return true;
            }
        }
        return false;
    }
    
    /*
    Shows all users 
    */
    public void pingUsers(){
        for (Perfil base : this.base) {
            System.out.println("CT:/ [@Ping] detected " + base.getNome() + " / " + base.getContrasinal());
        }

    }
}
