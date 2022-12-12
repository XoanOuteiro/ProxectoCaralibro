
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
            clr();
            //Add options according to need.
            System.out.println("[1] to create an account.");
            System.out.println("[2] to login to existing account");

            //So that we dont need exceptions we will do all compares to String 
            input = reads.nextLine();

            //Expand as needed
            switch (input) {

                case "1":
                    crearPerfil();
                    break;

                case "2":
                    iniciarSesion();
                    //Once we succesfully have a session we go to MainMenu
                    if (current != null) {                                         //At 26/12 this condition crashes or isnt met, fixed 14:41 
                        mostrarMenuPrincipal();
                    }
                    break;

                case "ct":
                    ctCommand();
                    break;

                case "close sys":
                    hasChangedMenu = true;
                    break;

                default:
                    System.out.println(">Input not valid, please try again");
                    break;
            }

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
            System.out.println("-[1] State ->Current: " + current.getEstado());
            System.out.println("-[2] Biography & Posts ->Current: " + current.getBiography());
            System.out.println("-[3] FriendList");
            System.out.println("-[0] Close session");

            try {
                input = reads.nextLine();
            } catch (Exception InputMismatchException) {
                System.out.println(">That input was not valid, please try again");
                continue;
            }

            switch (input) {

                case "1":             //Consider the option to call a different method in each case containing this logic (done 02/12/22)
                    stateCase();
                    break;

                case "2":
                    biographyCase();
                    break;

                case "3":
                    friendMenu();
                    break;

                case "0":
                    current = null;
                    hasChangedMenu = true;                                          //Change session to null and jump back into sessions menu
                    break;

                default:
                    System.out.println(">That input was not valid please try again.");
                    break;
            }

        } while (!hasChangedMenu);
    }

    /**
     * This is the menu for selfs state
     */
    private void stateCase() {
        Scanner reads = new Scanner(System.in);
        if (current.getEstado() == null) {

            System.out.println(">You have not set a state yet.");   //If the user has no state we ask them to write one
            System.out.println(">Please write a new state: ");

            String newState = reads.nextLine();
            current.setEstado(newState);

        } else {

            System.out.println(">Current state: " + current.getEstado());   //If they have a state they have the option to change it                        System.out.println(">Press [1] if you wish to change your state. Any other number to continue");
            System.out.println(">Press [1] if you wish to change the state. Any other number to go back");

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
    }

    /**
     * This is the menu for selfs biography
     */
    private void biographyCase() {
        Scanner reads = new Scanner(System.in);
        if (current.getBiography() == null) {

            System.out.println(">You have not set a biography yet.");   //Same procedure for bios
            System.out.println(">Please write a new biography: ");

            String newBiography = reads.nextLine();
            current.setBiography(newBiography);

        } else {

            System.out.println(">Current biography: " + current.getBiography());
            System.out.println(">Press [1] if you wish to change the biography. Any other number to go back");
            System.out.println(">Press [2] if you wish to enter post feed.");

            input = reads.nextLine();

            switch (input) {
                case "1":

                    System.out.println(">Please, write new biography: ");

                    String newBiography = reads.nextLine();
                    current.setBiography(newBiography);
                    break;

                case "2":
                    postFeedMenu();

                default:
                    break;
            }
        }
    }

    /**
     * This is the menu for creating new users
     */
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

    /**
     * This is the menu for logging in
     */
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
    /**
     * This is a utility non-essential menu for managing selfs friendList
     */
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
                    printFriendRequestMenu();
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

    /**
     * This is a non-essential menu for managing selfs friend requests
     */
    public void printFriendRequestMenu() {
        Scanner reads = new Scanner(System.in);
        System.out.println(">Who do you want to add as a friend?");
        String name = reads.nextLine();
        if (data.lookFor(name)) {

            if (current.friendListContainsName(name)) {                //Since we force adding users to both lists theres no need to check both lists

                System.out.println(">That user already is your friend");

            } else if (name.equals(current.getNome())) {

                System.out.println(">You cant add yourself as a friend.");

            } else if (current.friendRequest.contains(name) || data.buscarPerfil(name).friendRequest.contains(current.getNome())) {

                System.out.println(">You/This user already have/has a pending friend request from you/that user.");

            } else {

                current.engadirSolicitudeDeAmistade(data.buscarPerfil(name));

            }
        } else {

            System.out.println(">That user does not exist!");

        }
    }

    /**
     * This method allows for visualizing selfs added friends as names
     */
    public void printFriendList() {
        Scanner reads = new Scanner(System.in);
        System.out.println(">---------------------------------------------------------------<");
        if (current.friendList.size() > 0) {
            for (int cycle = 0; cycle < current.friendList.size(); cycle++) {
                System.out.println(">[" + cycle + "] " + current.friendList.get(cycle).getNome());
            }
        } else {
            System.out.println(">You still havent added any friends.");
        }

        System.out.println(">---------------------------------------------------------------<");
    }

    /**
     * This method shows a list enumerating all users that have sent a yet
     * unanswered request
     */
    public void printFriendRequestList() {
        Scanner reads = new Scanner(System.in);
        if (current.friendRequest.size() > 0) {

            for (int cycler = 0; cycler < current.friendRequest.size(); cycler++) {
                System.out.println(">[" + cycler + "] " + current.friendRequest.get(cycler) + " wants to be your friend");
            }

            System.out.println(">Write the name of the user you wish to [add/reject].");

            String input = reads.nextLine();

            if (current.friendRequest.contains(input)) {

                System.out.println(">Write [1] to accept or [2] to reject.");
                String accrej = reads.nextLine();

                if (accrej.equals("1")) {
                    current.aceptarSolicitudeDeAmistade(data.buscarPerfil(input));
                } else if (accrej.equals("2")) {
                    current.rexeitarSolicitudeDeAmistade(data.buscarPerfil(input));
                } else {
                    System.out.println(">Input not valid.");
                }

            } else {
                System.out.println(">That name was not valid.");
            }
            ;

        } else {
            System.out.println(">You have no pending requests.");
        }

        System.out.println(">Press enter to continue.");
        reads.nextLine();
    }

    /**
     * Shows initial posts menu
     */
    private void postFeedMenu() {
        clr();
        Scanner reads = new Scanner(System.in);
        System.out.println(">Welcome to inbox! Here you and your friends can make new posts.");
        boolean hasChangedMenu = false;
        Perfil dir = current;                                                   //The atribute dir is used to reference on which users inbox we will be posting
        String input;

        do {
            System.out.println(">This is " + dir.getNome() + "'s inbox.");
            System.out.println("[1] to check posts at this inbox");             //add notifs here?
            System.out.println("[2] to make a new post");
            System.out.println("[3] to change inbox directory");
            System.out.println("[4] to go back");

            input = reads.nextLine();

            switch (input) {

                case "1":
                    showPosts();
                    break;

                case "2":
                    createPostMenu();
                    break;

                case "3":
                    if (current.friendList.size() == 0) {
                        System.out.println(">Looks like you still havent added any friends :(");
                    } else {
                        System.out.println(">To which one of your friends inbox would you like to go?");
                        printFriendList();
                        input = reads.nextLine();

                        if (current.friendListContainsName(input)) {

                            dir = data.buscarPerfil(input);

                        } else {

                            System.out.println(">Whoops! Looks like this user is not in your friend list :(");

                        }
                    }
                    break;
                
                case "4":
                    hasChangedMenu = true;
                    break;
                
                default:
                    System.out.println(">Input not valid, please try again.");
                    break;
            }
            clr();

        } while (!hasChangedMenu);
    }

    /**
     * Menu for creating and editing posts (editing ONLY before publishing via
     * "Are you sure?" messages)
     */
    private void createPostMenu() {
        System.out.println("TBI");
    }

    private void showPosts() {
        System.out.println("TBI");
    }

    //Sthetics
    /**
     * Prints CaraLibro in ASCII art.
     */
    public void doLogo() {
        System.out.println("   _____                _      _ _               \n"
                + "  / ____|              | |    (_) |              \n"
                + " | |     __ _ _ __ __ _| |     _| |__  _ __ ___  \n"
                + " | |    / _` | '__/ _` | |    | | '_ \\| '__/ _ \\ \n"
                + " | |___| (_| | | | (_| | |____| | |_) | | | (_) |\n"
                + "  \\_____\\__,_|_|  \\__,_|______|_|_.__/|_|  \\___/ \n"
                + "                                                 \n"
                + "                                                 ");
    }

    /**
     * Jumps N lines to create readable menus
     */
    public void clr() {
        for (int jump = 0; jump < 3; jump++) {
            System.out.println();
        }
        System.out.println("^^^ Later action ^^^");
        System.out.println("><><><><><><><><><><><><><><><><><><><><><><");
        System.out.println("-> Newer action <-");
        
        for (int jump = 0; jump < 3; jump++) {
            System.out.println();
        }
    }

    //Utility
    /**
     * This command console provides quick admin tools for debugging
     */
    private void ctCommand() {
        boolean hasExtd = false;
        Scanner reads = new Scanner(System.in);

        do {

            System.out.println("CT:/");

            switch (reads.nextLine()) {

                case "add lib":                                                     //Adds an amount of users
                    data.engadirPerfil(new Perfil("a1", "a1"));
                    data.engadirPerfil(new Perfil("a22xoanmoj", "a22"));
                    data.engadirPerfil(new Perfil("bmo", "bmo_"));
                    data.engadirPerfil(new Perfil("rotary", "rot21"));
                    data.engadirPerfil(new Perfil("jess", "123abc."));
                    data.engadirPerfil(new Perfil("anon12", "lolmao"));
                    data.engadirPerfil(new Perfil("kev", "javac"));
                    data.engadirPerfil(new Perfil("user", "user"));
                    System.out.println("CT:/[OK] 8 users added.");
                    break;

                case "exit":
                    hasExtd = true;
                    break;

                case "ping":
                    this.data.pingUsers();
                    break;

                case "load friendtest":
                    data.engadirPerfil(new Perfil("sender1", "sender1"));
                    data.engadirPerfil(new Perfil("sender2", "sender2"));
                    data.engadirPerfil(new Perfil("reciever", "reciever"));

                    current = data.buscarPerfil("sender1");
                    current.engadirSolicitudeDeAmistade(data.buscarPerfil("reciever"));
                    current = data.buscarPerfil("sender2");
                    current.engadirSolicitudeDeAmistade(data.buscarPerfil("reciever"));
                    current = null;
                    System.out.println("CT:/[OK] case test 'friends' loaded");
                    break;
                    
                case "metaload":
                    //Friendtest
                    data.engadirPerfil(new Perfil("sender1", "sender1"));
                    data.engadirPerfil(new Perfil("sender2", "sender2"));
                    data.engadirPerfil(new Perfil("reciever", "reciever"));

                    current = data.buscarPerfil("sender1");
                    current.engadirSolicitudeDeAmistade(data.buscarPerfil("reciever"));
                    current = data.buscarPerfil("sender2");
                    current.engadirSolicitudeDeAmistade(data.buscarPerfil("reciever"));
                    current = null;
                    
                    //Lib
                    data.engadirPerfil(new Perfil("a1", "a1"));
                    data.engadirPerfil(new Perfil("a22xoanmoj", "a22"));
                    data.engadirPerfil(new Perfil("bmo", "bmo_"));
                    data.engadirPerfil(new Perfil("rotary", "rot21"));
                    data.engadirPerfil(new Perfil("jess", "123abc."));
                    data.engadirPerfil(new Perfil("anon12", "lolmao"));
                    data.engadirPerfil(new Perfil("kev", "javac"));
                    data.engadirPerfil(new Perfil("user", "user"));
                    System.out.println("CT:/[OK] [!WARNING!] all libs added, PING recommended");
                    break;
                    
                case "ping *":
                    data.pingScrape();
                    break;
                
                case "-?":
                    System.out.println("[add lib] will load blank users to try");
                    System.out.println("[load friendtest] will add 3 users with friend request relations");
                    System.out.println("[metaload] will load all existing libs");
                    System.out.println("[ping] shows users and their passwords");
                    System.out.println("[ping *] shows all the uppermost information of data");
                    
                    break;

                default:
                    System.out.println("CT:/[Error] InputNotValid use command -? for help");
                    break;

            }

        } while (!hasExtd);

    }
}
