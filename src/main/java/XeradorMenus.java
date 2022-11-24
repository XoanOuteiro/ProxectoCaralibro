
import java.util.Scanner;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class XeradorMenus {
    //Compilation atributes
    CaraLibroBD data;
    int input;
    Perfil current;
    
    //Construction methods
    
    public XeradorMenus(){                                                      //Constructor spawns database
        this.data = new CaraLibroBD();
    }
    
    //Essential menus
    
    public void mostrarMenuInicial(){
        doLogo();
        System.out.println("--Welcome to CaraLibro v0.0.1");
        System.out.println("--To continue please press an option number and the [ENTER_KEY]");
        
        //Cycle loop is existed upon detecting a valid intput that swaps to another menu
        Scanner reads = new Scanner(System.in);
        boolean hasChangedMenu = false;
        
        do{
            //Add options according to need.
            System.out.println("[1] to create an account.");
            System.out.println("[2] to login to existing account");
            
            
            reads.nextLine();                                                   //Required collapse for iteration skips     
            try{
                
                input = reads.nextInt(); 
                
            }catch (Exception InputMismatchException){continue;}                //Skip this iteration
            
            //Expand as needed
            switch (input){
  
                case 1:
                    crearPerfil();
                    break;
                    
                case 2:
                    iniciarSesion();
                    //hasChangedMenu = true;                                      //Once we succesfully have a session we close this menu and go to MainMenu
                    if(current != null){
                        //Main menu here
                    }
                    break;
                    
                default:
                    System.out.println(">Input not valid, please try again");                
            }
            
        }while(!hasChangedMenu);
    }
    
    private void crearPerfil(){
        Scanner reads = new Scanner(System.in);
        System.out.println("-Insert your desired username, then press [Enter]");
        String usrName = reads.nextLine();
        System.out.println("-Insert your desired password, then press [Enter]");
        String psswd = reads.nextLine();
        
        this.data.engadirPerfil(new Perfil(usrName,psswd));
    }
    
    private void iniciarSesion(){
        Scanner reads = new Scanner(System.in);
        System.out.println("-Write your username");
        String usrName = reads.nextLine();
        System.out.println("-Write your password");
        String psswd = reads.nextLine();
        
        Perfil current = this.data.obterPerfil(usrName, psswd);                 //Pull user from database if exists
        
        if(current == null){                                                    //Check user isnt none
            System.out.println(">This user does not exist or the password was not correct.");
                                                                                //Ask to try login again or create account
        } else{
            System.out.println(">Logged in as: " + current.getNome());
        }
        
        
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
