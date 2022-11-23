
import java.util.ArrayList;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class CaraLibroBD {
    //Atributes
    ArrayList <Perfil> base;
    
    //Construction methods
    public CaraLibroBD(){
        this.base = new ArrayList<Perfil>();
    }
    
    //Getters & Setters
    
    
    //Utility methods
    public void engadirPerfil(Perfil novoPerfil){        //Adding requires a Perfil obj which we will -ALWAYS- create on call
        this.base.add(novoPerfil);
    }
}
