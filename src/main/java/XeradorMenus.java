
import java.util.Scanner;

/**
 *
 * by @XoanOuteiro & @Samuyo
 */
public class XeradorMenus {
    //Compilation atributes
    CaraLibroBD data;
    String input;
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
            
            
            //So that we dont need exceptions we will do all compares to String 
            try{
                
                input = reads.nextLine(); 
                
            }catch (Exception InputMismatchException){continue;}                //Skip this iteration
            
            //Expand as needed
            switch (input){
  
                case "1":
                    crearPerfil();
                    break;
                    
                case "2":
                    iniciarSesion();
                                                                                 //Once we succesfully have a session we go to MainMenu
                    if(current != null){                                         //At 26/12 this condition crashes or isnt met, fixed 14:41 
                        clr();
                        mostrarMenuPrincipal();
                    }
                    break;
                    
                default:
                    System.out.println(">Input not valid, please try again");                
            }
            
        }while(!hasChangedMenu);
    }
    
    public void mostrarMenuPrincipal(){
        //Method atributes
        boolean hasChangedMenu = false;
        Scanner reads = new Scanner(System.in);
        String input;
        
        //Logic
        do{
            clr();
            System.out.println("-Current user:" +  current.getNome());
            System.out.println("-[1] State");
            System.out.println("-[2] Biography");
            System.out.println("-[0] Close session");
            

            try{
                input = reads.nextLine();
            }catch(Exception InputMismatchException){
                System.out.println(">That input was not valid, please try again");
                continue;
            }
            
            switch (input){
                
                case "1":                                                         //Consider the option to call a different method in case 1 containing this logic
                    if(current.getEstado() == null){
                        
                        System.out.println(">You have not set a state yet.");
                        System.out.println(">Please write a new state: ");
                        
                        String newState = reads.nextLine();
                        current.setEstado(newState);
                        
                    } else {
                        
                        System.out.println(">Current state: " + current.getEstado());
                        System.out.println(">Press [1] if you wish to change your state. Any other number to continue");
                        
                        input = reads.nextLine();
                        
                        switch(input){
                            
                            case "1":
                                
                                System.out.println(">Please write a new state: ");
                                
                                String newState = reads.nextLine();
                                current.setEstado(newState);
                                break;
                                
                            default:
                                break;
                                
                        }
                    }
                    break;
                    
                case "2":
                    if (current.getBiography() == null){
                        
                        System.out.println(">You have not set a biography yet.");
                        System.out.println(">Please write a new biography: ");
                        
                        String newBiography = reads.nextLine();
                        current.setBiography(newBiography);
                        
                    } else {
                        
                        System.out.println(">Current biography: " + current.getBiography());
                        System.out.println(">Press [1] if you wish to change the biography. Any other number to continue");
                        
                        input = reads.nextLine();
                        
                        switch(input){
                            case "1":
                                
                                System.out.println(">Please write new biography: ");
                                
                                String newBiography = reads.nextLine();
                                current.setBiography(newBiography);
                                break;
                                
                            default:
                                break;
                        }
                    }
                case "0":
                    current = null;
                    hasChangedMenu = true;
                    break;
                
                default:
                    System.out.println(">That input was not valid please try again.");
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
        
        current = this.data.obterPerfil(usrName, psswd);                 //Pull user from database if exists
        
        if(current == null){                                                    //Check user isnt none
            System.out.println(">This user does not exist or the password was not correct");
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
        for(int jump = 0; jump < 60; jump++){
            System.out.println();
        }
    }
}
