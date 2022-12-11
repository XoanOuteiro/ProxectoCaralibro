
import java.util.ArrayList;
/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public final class CaraLibroBD {

    //Atributes
    static ArrayList<Perfil> base = new ArrayList();

    //Construction methods


    //Getters & Setters
    //Utility methods
    public static void engadirPerfil(Perfil novoPerfil) {                              //Adding requires a Perfil obj which we will -ALWAYS- create on call
        base.add(novoPerfil);
    }

    /*
    Returns a profile if and only if 
    it matches given Strings in the fields of 
    name and password.
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

    /*
      Searchs and retrieves a profile based on its name.
      Returns null if not exists.
     */
    public static Perfil buscarPerfil(String text) {
        for (Perfil base : base) {
            if (base.getNome().equals(text)) {
                return base;
            }
        }
        return null;
    }
    

    /*
    Returns true if the given String is contained 
    as a name in current database, returns false otherwise.
     */
    public static boolean lookFor(String that) {
        for (Perfil base : base) {
            if (base.getNome().equals(that)) {
                return true;
            }
        }
        return false;
    }

    /*
    Shows all users 
     */
    public static void pingUsers() {
        for (Perfil base : base) {
            System.out.println("CT:/ [@Ping] detected " + base.getNome() + " / " + base.getContrasinal());
        }

    }
}
