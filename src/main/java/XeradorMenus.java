
import java.util.Scanner;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class XeradorMenus {
    //Compilation atributes
    CaraLibroBD data;
    
    //Construction methods
    
    public XeradorMenus(){                          //Constructor spawns database
        this.data = new CaraLibroBD();
    }
    
    //Essential menus
    
    public void mostrarMenuInicial(){
        doLogo();
        System.out.println("--Welcome to CaraLibro v0.0.1");
        System.out.println("--To continue please press an option number and the [ENTER_KEY]");
        
        //Add options according to need.
        System.out.println("[1] to create an account.");
        System.out.println("[2] to login to existing account");
        
        //Cycle loop is existed upon detecting a valid intput 
        Scanner reads = new Scanner(System.in);
        boolean hasPicked = false;
        
        do{
            int input = reads.nextInt();
            
            //Expand as needed
            switch (input){
  
                case 1:
                    crearPerfil();
                    break;
                    
                case 2:
                    break;
                    
                default:
                    System.out.println(">Input not valid, please try again");
                    
                    
            }
        }while(!hasPicked);
    }
    
    public void crearPerfil(){
        Scanner reads = new Scanner(System.in);
        System.out.println("-Insert your desired username, then press [Enter]");
        String usrName = reads.nextLine();
        System.out.println("-Insert your desired password, then press [Enter]");
        String psswd = reads.nextLine();
        
        this.data.engadirPerfil(new Perfil(usrName,psswd));
    }

    //Secondary menus
    
    
    
    
    
    
    
    
    
    
    
    
    //Sthetics
    
    public void doLogo(){                                                               //Shows ASCII art for logo
        System.out.println("   _____                _      _ _               \n" +
"  / ____|              | |    (_) |              \n" +
" | |     __ _ _ __ __ _| |     _| |__  _ __ ___  \n" +
" | |    / _` | '__/ _` | |    | | '_ \\| '__/ _ \\ \n" +
" | |___| (_| | | | (_| | |____| | |_) | | | (_) |\n" +
"  \\_____\\__,_|_|  \\__,_|______|_|_.__/|_|  \\___/ \n" +
"                                                 \n" +
"                                                 ");
    }
    
    public void clr(){                                                                 //Creates 30 whitespace linebreaks as a method to clear screen
        for(int jump = 0; jump < 30; jump++){
            System.out.println();
        }
    }
}
