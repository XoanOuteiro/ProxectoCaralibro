
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
    public XeradorMenus() {                                                      //Constructor spawns database IF NOT STATIC
        //this.data = new CaraLibroBD();
    }

    //Essential menus
    public void mostrarMenuInicial() {
        doLogo();
        System.out.println("--Welcome to CaraLibro v0.0.7");
        System.out.println("--To continue please press an option number and the [ENTER_KEY]");

        //Cycle loop is existed upon detecting a valid intput that swaps to another menu
        Scanner reads = new Scanner(System.in);
        boolean hasChangedMenu = false;

        do {
            //Add options according to need.
            System.out.println("[1] to create an account.");
            System.out.println("[2] to login to existing account");

            //So that we dont need exceptions we will do all compares to String 
            try {

                input = reads.nextLine();

            } catch (Exception InputMismatchException) {
                continue;
            }                //Skip this iteration

            //Expand as needed
            switch (input) {

                case "1":
                    crearPerfil();
                    break;

                case "2":
                    iniciarSesion();
                    //Once we succesfully have a session we go to MainMenu
                    if (current != null) {                                         //At 26/12 this condition crashes or isnt met, fixed 14:41 
                        clr();
                        mostrarMenuPrincipal();
                    }
                    break;

                case "ct":
                    ctCommand();
                    break;

                default:
                    System.out.println(">Input not valid, please try again");
                    break;
            }
            clr();

        } while (!hasChangedMenu);
    }

    public void mostrarMenuPrincipal() {
        //Method atributes
        boolean hasChangedMenu = false;
        Scanner reads = new Scanner(System.in);
        String input;

        //Logic
        do {
            clr();
            System.out.println("-Current user:" + current.getNome());
            System.out.println("-[1] State");
            System.out.println("-[2] Biography");
            System.out.println("-[3] FriendList");
            System.out.println("-[0] Close session");

            try {
                input = reads.nextLine();
            } catch (Exception InputMismatchException) {
                System.out.println(">That input was not valid, please try again");
                continue;
            }

            switch (input) {

                case "1":             //Consider the option to call a different method in each case containing this logic
                    if (current.getEstado() == null) {

                        System.out.println(">You have not set a state yet.");   //If the user has no state we ask them to write one
                        System.out.println(">Please write a new state: ");

                        String newState = reads.nextLine();
                        current.setEstado(newState);

                    } else {

                        System.out.println(">Current state: " + current.getEstado());   //If they have a state they have the option to change it                        System.out.println(">Press [1] if you wish to change your state. Any other number to continue");
                        System.out.println(">Press [1] if you wish to change the state. Any other number to continue");

                        input = reads.nextLine();

                        switch (input) {

                            case "1":

                                System.out.println(">Please, write a new state: ");

                                String newState = reads.nextLine();
                                current.setEstado(newState);
                                break;

                            default:
                                break;

                        }
                    }
                    break;

                case "2":
                    if (current.getBiography() == null) {

                        System.out.println(">You have not set a biography yet.");
                        System.out.println(">Please write a new biography: ");

                        String newBiography = reads.nextLine();
                        current.setBiography(newBiography);

                    } else {

                        System.out.println(">Current biography: " + current.getBiography());
                        System.out.println(">Press [1] if you wish to change the biography. Any other number to continue");

                        input = reads.nextLine();

                        switch (input) {
                            case "1":

                                System.out.println(">Please, write new biography: ");

                                String newBiography = reads.nextLine();
                                current.setBiography(newBiography);
                                break;

                            default:
                                break;
                        }
                    }
                    break;

                case "3":
                    friendMenu();
                    break;

                case "0":
                    current = null;
                    hasChangedMenu = true;
                    break;

                default:
                    System.out.println(">That input was not valid please try again.");
                    break;
            }

        } while (!hasChangedMenu);
    }

    private void crearPerfil() {
        Scanner reads = new Scanner(System.in);
        boolean done = false;

        do {
            System.out.println("-Insert your desired username, then press [Enter]");
            String usrName = reads.nextLine();
            System.out.println("-Insert your desired password, then press [Enter]");
            String psswd = reads.nextLine();

            if (this.data.lookFor(usrName)) {
                System.out.println(">That username is already taken please try another one.");
            } else {
                this.data.engadirPerfil(new Perfil(usrName, psswd));
                done = true;
            }
        } while (!done);
    }

    private void iniciarSesion() {
        Scanner reads = new Scanner(System.in);
        System.out.println("-Write your username");
        String usrName = reads.nextLine();
        System.out.println("-Write your password");
        String psswd = reads.nextLine();

        current = this.data.obterPerfil(usrName, psswd);                 //Pull user from database if exists

        if (current == null) {                                                          //Check user isnt none
            System.out.println(">This user does not exist or the password was not correct");
            //Ask to try login again or create account
        } else {
            System.out.println(">Logged in as: " + current.getNome());
        }

    }

    //Secondary menus
    public void friendMenu() {
        boolean hasChanged = false;
        Scanner reads = new Scanner(System.in);

        do {
            clr();
            System.out.println("-You currently have " + current.friendRequest.size() + " pending friend requests");
            System.out.println("[1] to send a friend request");
            System.out.println("[2] to see friend requests");
            System.out.println("[3] to see FriendList");
            System.out.println("[0] to exit menu");

            String input = reads.nextLine();

            switch (input) {
                case "1":
                    System.out.println(">Who do you want to add as a friend?");
                    String name = reads.nextLine();
                    current.engadirSolicitudeDeAmistade(current.retrieveUser(name));
                    break;

                case "2":
                    printFriendRequestList();
                    break;

                case "3":
                    printFriendList();
                    break;

                case "0":
                    hasChanged = true;
                    break;

                default:
                    System.out.println(">Input not valid please try again");
                    break;
            }

        } while (!hasChanged);
    }

    public void printFriendList() {
        Scanner reads = new Scanner(System.in);
        if (current.friendList.size() > 0) {
            for (int cycle = 0; cycle <= current.friendList.size(); cycle++) {
                System.out.println(">[" + cycle + "] " + current.friendList.get(cycle));
            }
        } else {
            System.out.println(">You still havent added any friends.");
        }

        System.out.println(">Press enter to continue.");
        reads.nextLine();
    }

    public void printFriendRequestList() {
        Scanner reads = new Scanner(System.in);
        if (current.friendRequest.size() > 0) {
            for (int cycle = 0; cycle <= current.friendList.size(); cycle++) {
                System.out.println(">[" + cycle + "] " + current.friendRequest.get(cycle) + " wants to be your friend");
            }
        } else {
            System.out.println(">You have no pending requests.");
        }

        System.out.println(">Press enter to continue.");
        reads.nextLine();
    }

    //Sthetics
    public void doLogo() {                                                               //Shows ASCII art for logo
        System.out.println("   _____                _      _ _               \n"
                + "  / ____|              | |    (_) |              \n"
                + " | |     __ _ _ __ __ _| |     _| |__  _ __ ___  \n"
                + " | |    / _` | '__/ _` | |    | | '_ \\| '__/ _ \\ \n"
                + " | |___| (_| | | | (_| | |____| | |_) | | | (_) |\n"
                + "  \\_____\\__,_|_|  \\__,_|______|_|_.__/|_|  \\___/ \n"
                + "                                                 \n"
                + "                                                 ");
    }

    public void clr() {                                                                 //Creates 30 whitespace linebreaks as a method to clear screen
        for (int jump = 0; jump < 20; jump++) {
            System.out.println();
        }
    }

    //Utility
    

    /*
    This debugings console provides simple automatisms for the initial menu
     */
    private void ctCommand() {
        boolean hasExtd = false;
        Scanner reads = new Scanner(System.in);

        do {

            System.out.println("CT:/");

            switch (reads.nextLine()) {

                case "add lib":                                                     //Adds an amount of users
                    this.data.engadirPerfil(new Perfil("a1", "a1"));
                    this.data.engadirPerfil(new Perfil("a22xoanmoj", "a22"));
                    this.data.engadirPerfil(new Perfil("bmo", "bmo_"));
                    this.data.engadirPerfil(new Perfil("rotary", "rot21"));
                    this.data.engadirPerfil(new Perfil("jess", "123abc."));
                    this.data.engadirPerfil(new Perfil("anon12", "lolmao"));
                    this.data.engadirPerfil(new Perfil("kev", "javac"));
                    this.data.engadirPerfil(new Perfil("user", "user"));
                    System.out.println("CT:/[OK] 8 users added.");
                    break;

                case "exit":
                    hasExtd = true;
                    break;

                case "ping":
                    this.data.pingUsers();
                    break;

                default:
                    System.out.println("CT:/[Error] InputNotValid");
                    break;

            }

        } while (!hasExtd);

    }
}
