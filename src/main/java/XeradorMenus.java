
import java.util.ArrayList;
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
    Perfil dir = current;                                                   //The atribute dir is used to reference on which users inbox we will be posting

    //Construction methods
    public XeradorMenus() {                                                      //Constructor spawns database IF NOT STATIC
        //this.data = new CaraLibroBD();
    }

    //Essential menus
    public void mostrarMenuInicial() {
        doLogo();
        System.out.println("--Welcome to CaraLibro v1.0.0");
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
                        dir = current;
                        mostrarMenuPrincipal(current);
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

    public void mostrarMenuPrincipal(Perfil current) {
        //Method atributes
        boolean hasChangedMenu = false;
        Scanner reads = new Scanner(System.in);
        String input;

        //Logic
        do {
            clr();
            System.out.println("-Current user:" + current.getNome());
            System.out.println("-[1] State \t\t->Current: " + current.getEstado());
            System.out.println("-[2] Biography & Posts \t->Current: " + current.getBiography());
            System.out.println("-[3] FriendRequests \t->Pending: " + current.getFriendRequest().size());
            System.out.println("-[4] FriendList");
            System.out.println("-[5] Chats \t\t->Unread: " + current.getUnreadMssgs());
            System.out.println("-[0] Close session");

            try {
                input = reads.nextLine();
            } catch (Exception InputMismatchException) {
                System.out.println(">That input was not valid, please try again");
                continue;
            }

            switch (input) {

                case "1":             //Consider the option to call a different method in each case containing this logic (done 02/12/22)
                    cambiarEstado(current);
                    break;

                case "2":
                    mostrarBiografia(current);
                    break;

                case "3":
                    friendRequestMenu();
                    break;

                case "4":
                    friendMenu();
                    break;

                case "5":
                    mainChatMenu(current);
                    break;

                case "0":
                    pecharSesion();
                    hasChangedMenu = true;                                          //Change session to null and jump back into sessions menu
                    break;

                default:
                    System.out.println(">That input was not valid please try again.");
                    break;
            }

        } while (!hasChangedMenu);
    }

    public void pecharSesion() {
        current = null;
    }

    /**
     * This is the menu for selfs state
     */
    private void cambiarEstado(Perfil current) {
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
    public void mostrarBiografia(Perfil current) {
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

            boolean correctRep = false;
            String psswd = "";                                                  //Compilation init
            do {
                System.out.println("-Insert your desired password, then press [Enter]");
                psswd = reads.nextLine();
                System.out.println("-Confirm password");
                String psswdCheck = reads.nextLine();

                if (psswd.equals(psswdCheck)) {
                    correctRep = true;
                    if (this.data.lookFor(usrName)) {
                        System.out.println(">That username is already taken please try another one.");
                    } else {
                        this.data.engadirPerfil(new Perfil(usrName, psswd));
                        done = true;
                    }
                } else {
                    System.out.println("-Password confirmation incorrect.");
                }
            } while (!correctRep);

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

    /**
     * Initial menu for chatting
     */
    private void mainChatMenu(Perfil current) {
        Scanner reads = new Scanner(System.in);
        boolean hasExited = false;

        do {
            clr();
            mostrarMensaxes(current);

            System.out.println(">What do you want to do?");
            System.out.println("[1] to mark a message as read");
            System.out.println("[2] to mark all messages as read");
            System.out.println("[3] to delete a message");
            System.out.println("[4] to reply to a message");
            System.out.println("[5] to send a message");
            System.out.println("[0] to go back");

            String input = reads.nextLine();

            switch (input) {
                case "1":
                    System.out.println(">Which one? [id]");
                    String inpt1 = reads.nextLine();
                    int id1 = Integer.parseInt(inpt1);

                    if (id1 < current.getMsgbox().size()) {                         //Only if message exists, this is done on all cases here

                        marcarMensaxeComoLida(current.getMsgbox().get(id1));

                    } else {
                        System.out.println(">This message does not exist");
                    }
                    break;

                case "2":
                    if (current.getMsgbox().size() > 0) {
                        current.setAllAsRead();
                    } else {
                        System.out.println(">No messages to set as read.");
                    }
                    break;

                case "3":
                    System.out.println(">Which one? [id]");
                    String inpt3 = reads.nextLine();
                    int id3 = Integer.parseInt(inpt3);

                    if (id3 < current.getMsgbox().size()) {

                        eliminarMensaxe(current, current.getMsgbox().get(id3));

                    } else {
                        System.out.println(">This message does not exist");
                    }
                    break;

                case "4":
                    System.out.println(">What message do you want to reply to? [id]");
                    String inpt4 = reads.nextLine();
                    int id4 = Integer.parseInt(inpt4);

                    if (id4 < current.getFriendList().size()) {
                        if (current.getFriendList().contains(current.getMsgbox().get(id4).getRemitente())) {              //In case of friend deletion after mmsg is sent

                            if (id4 < current.getMsgbox().size()) {
                                System.out.println("Write your reply: ");
                                String text4 = reads.nextLine();
                                current.getMsgbox().get(id4).getRemitente().engadirMensaxePrivada(new Mensaxe("[In reply to your message]-> " + text4, current));
                            } else {
                                System.out.println(">This message does not exist");
                            }
                        } else {
                            System.out.println(">That user is not your friend anymore");
                        }
                    } else {
                        System.out.println("That is not a valid option");
                    }
                    break;

                case "5":
                    if (current.getFriendList().size() > 0) {
                        mostrarListaDeAmigos(current);

                        System.out.println(">To which user? [id]");
                        int id5 = reads.nextInt();                              //Using String read and parsing caused issues
                        reads.nextLine();

                        if (id5 < current.getFriendList().size()) {

                            escribirMensaxe(current, current.getFriendList().get(id5));

                        } else {

                            System.out.println("That is not a valid option");

                        }

                    } else {

                        System.out.println("You still havent added any friends!");

                    }
                    break;

                case "0":
                    hasExited = true;
                    break;

                default:
                    System.out.println(">Input not valid, please try again.");
            }

        } while (!hasExited);

    }

    private void marcarMensaxeComoLida(Mensaxe m) {
        m.setLido(true);
    }

    private void eliminarMensaxe(Perfil p, Mensaxe m) {
        p.getMsgbox().remove(m);
    }

    /**
     * Method to show all of this users messages
     */
    public void mostrarMensaxes(Perfil current) {
        int pos = 0;
        for (Mensaxe mensaxe : current.getMsgbox()) {
            System.out.println("->MSSG_ID= " + pos);
            System.out.println("-@" + mensaxe.getRemitente().getNome() + " said: ");
            System.out.println("\" " + mensaxe.getTexto() + " \"");
            System.out.println("--at: " + mensaxe.getData() + " //--read: " + hasBeenRead(pos));
            pos++;
            System.out.println(">-----------------------------------------------------------------<");
        }
        
        /**
         * Aclaración, gustounos a idea de que as mensaxes aparecesen coma un chat, amosando o contido do 
         * array literalmente podemos ver as mensaxes mais recentes de ultima(e dicir , mais cercanas ao 
         * menu de usuario, de tal maneira que de haber moitas as antigas aparecen moi arriba// non se vexan 
         * por o lugar alto no que se amosan)
         * 
         * De todos modos a opción para amosar "as mensaxes mais recentes primeiro" seria facendo un bucle for
         * que comece en current.getMssgbox.size() e baixe ata 0
         */
    }

    /**
     *
     * @param pos
     * @return "Yes"/"No"
     *
     * Method to check if a message is read or not
     */
    private String hasBeenRead(int pos) {
        String read;

        read = current.getMsgbox().get(pos).isLido() ? "Yes" : "No";

        return read;
    }

    /**
     * Menu for sending messages to friends
     */
    private void escribirMensaxe(Perfil remitente, Perfil destinatario) {
        Scanner reads = new Scanner(System.in);
        System.out.println(">Write your message:");
        String texto = reads.nextLine();
        destinatario.engadirMensaxePrivada(new Mensaxe(texto, remitente));
    }

    /**
     * This is a utility non-essential menu for managing selfs friendList
     */
    private void friendMenu() {
        boolean hasChanged = false;
        Scanner reads = new Scanner(System.in);

        do {
            mostrarListaDeAmigos(current);

            System.out.println(">What do you want to do?");
            System.out.println("[1] to delete a friend");
            System.out.println("[2] to send a chat message");
            System.out.println("[0] to go back");

            String input = reads.nextLine();

            switch (input) {

                case "1":
                    deleteFriendMenu();
                    break;

                case "2":
                    if (current.getFriendList().size() > 0) {
                        mostrarListaDeAmigos(current);

                        System.out.println(">To which user? [id]");
                        int id2 = reads.nextInt();                              //Using String read and parsing caused issues
                        reads.nextLine();

                        if (id2 < current.getFriendList().size()) {

                            escribirMensaxe(current, current.getFriendList().get(id2));

                        } else {
                            System.out.println("That is not a valid option");
                        }

                    } else {

                        System.out.println("You still havent added any friends!");

                    }
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
     * Menu for friend deletion
     */
    private void deleteFriendMenu() {
        Scanner reads = new Scanner(System.in);
        System.out.println("Which user do you wish to delete from your friendlist? [name]");
        String user = reads.nextLine();

        if (current.friendListContainsName(user)) {
            current.eliminarAmigo(data.buscarPerfil(user));
        } else {
            System.out.println(">That user is not your friend already.");
        }

    }

    /**
     * This is a non-essential menu for managing selfs friend requests
     */
    private void printFriendRequestAddMenu() {
        Scanner reads = new Scanner(System.in);
        System.out.println(">Who do you want to add as a friend?");
        String name = reads.nextLine();
        if (data.lookFor(name)) {

            if (current.friendListContainsName(name)) {                //Since we force adding users to both lists theres no need to check both lists

                System.out.println(">That user already is your friend");

            } else if (name.equals(current.getNome())) {

                System.out.println(">You cant add yourself as a friend.");

            } else if (current.getFriendRequest().contains(name) || data.buscarPerfil(name).getFriendRequest().contains(current.getNome())) {

                System.out.println(">You/This user already have/has a pending friend request from you/that user.");

            } else {

                current.engadirSolicitudeDeAmistade(data.buscarPerfil(name));

            }
        } else {

            System.out.println(">That user does not exist!");

        }
    }

    private void friendRequestMenu() {
        boolean hasExited = false;
        Scanner reads = new Scanner(System.in);
        do {
            clr();
            System.out.println("-You currently have " + current.getFriendRequest().size() + " pending friend requests");
            System.out.println("[1] to send a friend request");
            System.out.println("[2] to see friend requests");
            System.out.println("[3] to see friends of friends");
            System.out.println("[0] to go back");

            String input = reads.nextLine();

            switch (input) {
                case "1":
                    printFriendRequestAddMenu();
                    break;

                case "2":
                    mostrarSolicitudesDeAmizade(current);
                    break;

                case "3":
                    friendSuggestions();
                    break;

                case "0":
                    hasExited = true;
                    break;

                default:
                    System.out.println("Input not valid, please try again.");
                    break;
            }
        } while (!hasExited);
    }

    /**
     * For each friend prints a their friendlist
     */
    private void friendSuggestions() {
        clr();
        if (current.getFriendList().size() > 0) {                                        //If current has friends
            for (Perfil friend : current.getFriendList()) {                              //For each friend
                if (friend.getFriendList().size() > 0) {                                 //If friend has friends
                    System.out.println("---> " + friend.getNome() + " friends:");
                    for (Perfil friendOfFriend : friend.getFriendList()) {               //For each friend of friend
                        if (!friendOfFriend.getNome().equals(current.getNome())) {  //If friendOfFriendisnt current
                            System.out.println("-> " + friendOfFriend.getNome());   //Show -> + name
                        }
                    }
                }
            }
        } else {
            System.out.println(">You still havent added any friends.");
        }
    }

    /**
     * This method allows for visualizing selfs added friends as names
     */
    public void mostrarListaDeAmigos(Perfil current) {
        Scanner reads = new Scanner(System.in);
        System.out.println(">---------------------------------------------------------------<");
        if (current.getFriendList().size() > 0) {
            for (int cycle = 0; cycle < current.getFriendList().size(); cycle++) {
                System.out.println(">[" + cycle + "] " + current.getFriendList().get(cycle).getNome() + " -State: " + current.getFriendList().get(cycle).getEstado());
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
    public void mostrarSolicitudesDeAmizade(Perfil current) {
        Scanner reads = new Scanner(System.in);
        if (current.getFriendRequest().size() > 0) {

            for (int cycler = 0; cycler < current.getFriendRequest().size(); cycler++) {
                System.out.println(">[" + cycler + "] " + current.getFriendRequest().get(cycler) + " wants to be your friend");
            }

            System.out.println(">Write the name of the user you wish to [add/reject].");

            String input = reads.nextLine();

            if (current.getFriendRequest().contains(input)) {

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
        String input;

        do {
            System.out.println(">This is " + dir.getNome() + "'s inbox.");
            System.out.println("[1] to check posts at this inbox");             //add notifs here?
            System.out.println("[2] to make a new post");
            System.out.println("[3] to change inbox directory");
            System.out.println("[4] to manage events");
            System.out.println("[0] to go back");

            input = reads.nextLine();

            switch (input) {

                case "1":
                    showPostsMenu();
                    break;

                case "2":
                    createPostMenu();
                    break;

                case "3":
                    if (current.getFriendList().size() == 0) {
                        System.out.println(">Looks like you still havent added any friends :(");
                    } else {
                        System.out.println(">To which one of your friends inbox would you like to go?");
                        mostrarListaDeAmigos(current);
                        input = reads.nextLine();

                        if (current.friendListContainsName(input)) {

                            dir = data.buscarPerfil(input);

                        } else {

                            System.out.println(">Whoops! Looks like this user is not in your friend list :(");

                        }
                    }
                    break;

                case "4":
                    if (current.getFriendList().size() > 0) {
                        eventsMenu();
                    } else {
                        System.out.println(">You need at least one friend to use events");
                    }
                    break;

                case "0":
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
        boolean hasExited = false;
        boolean newPost = false;
        String post;
        String sure;
        Scanner reads = new Scanner(System.in);
        do {
            System.out.println(">Write your new post:");
            post = reads.nextLine();
            do {
                System.out.println(">Ok, your new post is: " + post);
                System.out.println(">Are you sure? [1] yes / [2] no");
                sure = reads.nextLine();
                switch (sure) {

                    case "1":
                        dir.getInbox().add(new Publicacion(current, post));
                        hasExited = true;
                        newPost = true;
                        break;

                    case "2":
                        System.out.println(">Please rewrite the post");
                        post = reads.nextLine();
                        break;

                    default:
                        System.out.println(">Input not valid, please try again.");
                        break;
                }
            } while (!newPost);

        } while (!hasExited);

    }

    private void eventsMenu() {
        boolean hasExited = false;
        Scanner reads = new Scanner(System.in);
        do {
            clr();
            System.out.println(">You are looking at @" + dir.getNome() + "'s events");
            System.out.println("[1] to host an event (as yourself)");
            System.out.println("[2] to invite users to (your) event");
            System.out.println("[3] to check this users events");
            System.out.println("[0] to go back (go to posts menu to change directory)");

            String input = reads.nextLine();

            switch (input) {
                case "1":
                    System.out.println(">Write a topic for your event");
                    String topic = reads.nextLine();
                    System.out.println(">Write a date for this event");
                    String date = reads.nextLine();
                    current.getHostedEvents().add(new Evento(current, topic, date));
                    break;

                case "2":
                    showEvents(current);

                    mostrarListaDeAmigos(current);

                    System.out.println(">To which event do you wish to add a friend? [id]");
                    int id = reads.nextInt();
                    reads.nextLine();
                    
                    if (id < current.getHostedEvents().size()) {
                        
                        System.out.println(">Which user would you like to invite? [name]");
                        String name = reads.nextLine();
                        
                        if(current.friendListContainsName(name)){
                            
                        current.getHostedEvents().get(id).getGoers().add(data.buscarPerfil(name));
                        
                        } else {
                            
                            System.out.println(">That user is not your friend!");
                            
                        }
                        
                    } else {
                        
                        System.out.println(">Not a valid option");
                        
                    }
                    break;

                case "3":
                    showEvents(dir);
                    break;

                case "0":
                    hasExited = true;
                    break;

                default:
                    System.out.println("Input not valid, please try again");
            }
        } while (!hasExited);
    }

    private void showEvents(Perfil user) {
        int i = 0;
        if (user.getHostedEvents().size() > 0) {
            for (Evento evento : user.getHostedEvents()) {
                System.out.println("ID_EVENT = " + i);
                System.out.println("--->Event host: " + evento.getHost().getNome());
                System.out.println("->Event topic: " + evento.getTopic());
                System.out.println("->Event date: " + evento.getDate());
                System.out.println("---->Event goers<----");

                for (Perfil goer : evento.getGoers()) {
                    System.out.println("\t@" + goer.getNome());
                }
                System.out.println(">------------------------------------------------------<");
                i++;
            }
        } else {
            System.out.println(">No events here yet");
        }
    }

    private void showPostsMenu() {
        boolean hasExited = false;
        Scanner reads = new Scanner(System.in);
        System.out.println("This is " + dir.getNome() + "'s wall of posts.");
        do {
            if (dir.getInbox().size() > 0) {
                showPosts();
            } else {
                System.out.println(">No posts here yet");
            }
            System.out.println("What do you want to do?");
            System.out.println("[1] to enter a post");
            System.out.println("[0] to go back");

            String input = reads.nextLine();

            switch (input) {
                case "1":
                    System.out.println(">Which one? [num]");
                    input = reads.nextLine();
                    int checkInput = Integer.parseInt(input);
                    if (checkInput < dir.getInbox().size()) {                           //Only access post if it exists
                        enterPost(input);
                    } else {
                        System.out.println("This post does not exist!");
                    }
                    break;

                case "0":
                    hasExited = true;
                    break;

                default:
                    System.out.println(">Input not valid please try again");
                    break;
            }

        } while (!hasExited);
    }

    private void showPosts() {
        clr();

        for (int i = 0; i < dir.getInbox().size(); i++) {

            System.out.println(">--------- Post number: [" + i + "]");
            System.out.println("\"" + dir.getInbox().get(i).getTexto() + "\"");
            System.out.println("@" + author(i) + " // at: " + dir.getInbox().get(i).getData());
            System.out.println(">Likes: " + dir.getInbox().get(i).getLikes().size() + " >Comments: " + dir.getInbox().get(i).getComments().size());
            System.out.println("\n\n");
            //here will go comments and likes
        }
    }

    private String author(int i) {
        String author;

        author = dir.getInbox().get(i).getAutor().getNome().equals(current.getNome()) ? "You" : dir.getInbox().get(i).getAutor().getNome();

        return author;
    }

    private void enterPost(String input) {
        int pos = Integer.parseInt(input);
        Scanner reads = new Scanner(System.in);

        clr();
        System.out.println(">--------- This is " + dir.getInbox().get(pos).getAutor().getNome() + "'s post");
        System.out.println("->Local post ID: " + pos);
        System.out.println("- " + dir.getInbox().get(pos).getTexto() + " -");
        System.out.println("->>Liked by: ");
        showThisLikes(pos);
        System.out.println("->>Comments: ");
        showThisComments(pos);

        System.out.println("//-------------------------//");
        System.out.println("What do you wish to do?");
        System.out.println("[1] to like this post");
        System.out.println("[2] to comment on this post");
        System.out.println("[] Any other key + enter to go back");
        String inputer = reads.nextLine();

        switch (inputer) {
            case "1":
                if (author(pos).equals("You")) {
                    System.out.println(">You cant like your own post!");
                } else {
                    facerMeGusta(dir.getInbox().get(pos));
                }
                break;

            case "2":
                escribirComentario(dir.getInbox().get(pos), current);
                break;

            default:
                break;
        }

    }

    private void facerMeGusta(Publicacion publ) {
        if (!publ.getLikes().contains(current)) {

            publ.engadirMeGusta(current);

        } else {

            System.out.println(">You already liked this post");

        }
    }

    private void escribirComentario(Publicacion pbl, Perfil p) {
        clr();
        boolean hasExited = false;
        Scanner reads = new Scanner(System.in);

        do {
            System.out.println(">What do you want to comment?");
            String text = reads.nextLine();
            System.out.println("\n");
            System.out.println(">This is your comment: ");
            System.out.println(text);
            System.out.println(">Are you sure you want to post it? [1] yes [0] no");
            String input = reads.nextLine();

            switch (input) {
                case "1":
                    hasExited = true;
                    pbl.engadirComentario(new Comentario(p, text));
                    break;
            }

        } while (!hasExited);

    }

    private void showThisLikes(int pos) {
        if (dir.getInbox().get(pos).getLikes().size() == 0) {

            System.out.println("-Looks like no one has liked this post yet :c -");

        } else {

            for (int cnt = 0; cnt < dir.getInbox().get(pos).getLikes().size(); cnt++) {
                System.out.println(dir.getInbox().get(pos).getLikes().get(cnt).getNome());
            }
        }
    }

    private void showThisComments(int pos) {
        if (dir.getInbox().get(pos).getComments().size() == 0) {

            System.out.println("-Looks like no one has commented on this post yet :c -");

        } else {

            for (int cnt = 0; cnt < dir.getInbox().get(pos).getComments().size(); cnt++) {
                System.out.println(dir.getInbox().get(pos).getComments().get(cnt).getAutor().getNome() + ": ");
                System.out.println("\"" + dir.getInbox().get(pos).getComments().get(cnt).getTexto() + "\"");
                System.out.println(">-------------------------------------------------------------------<");
            }
        }
    }

    //Sthetics
    /**
     * Prints CaraLibro in ASCII art.
     */
    private void doLogo() {
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
                    loadLib1();
                    System.out.println("CT:/[OK] 8 users added.");
                    break;

                case "exit":
                    hasExtd = true;
                    break;

                case "ping":
                    this.data.pingUsers();
                    break;

                case "load friendtest":
                    loadFriendtest();
                    System.out.println("CT:/[OK] case test 'friends' loaded");
                    break;

                case "load postest":
                    loadPostest();
                    break;

                case "metaload":
                    //Friendtest
                    loadPostest();
                    loadFriendtest();
                    System.out.println("CT:/[OK] [!WARNING!] all libs added, PING recommended");
                    break;

                case "ping *":
                    data.pingScrape();
                    break;

                case "ping -post":
                    data.pingPoster();
                    break;

                case "SwipeAllUsers":
                    data.base = new ArrayList();
                    break;

                case "emu -1":
                    loadSimuLib();
                    break;

                case "-?":
                    System.out.println("[add lib] will load blank users to try");
                    System.out.println("[load friendtest] will add 3 users with friend request relations");
                    System.out.println("[metaload] will load all test cases");
                    System.out.println("[ping] shows users and their passwords");
                    System.out.println("[ping *] shows all the uppermost information of data");
                    System.out.println("[ping -posts] to show users basic post and friends info");
                    System.out.println("[load postest] to load post debugging");
                    System.out.println("[SwipeAllUsers] will rerun database, deleting all units");
                    System.out.println("[emu -1] to load LifeLib1.0");
                    System.out.println("[exit] to get out of this menu");

                    break;

                default:
                    System.out.println("CT:/[Error] InputNotValid use command -? for help");
                    break;

            }

        } while (!hasExtd);

    }

    private void loadLib1() {
        data.engadirPerfil(new Perfil("a1", "a1"));
        data.engadirPerfil(new Perfil("a22xoanmoj", "a22"));
        data.engadirPerfil(new Perfil("bmo", "bmo_"));
        data.engadirPerfil(new Perfil("rotary", "rot21"));
        data.engadirPerfil(new Perfil("jess", "123abc."));
        data.engadirPerfil(new Perfil("anon12", "lolmao"));
        data.engadirPerfil(new Perfil("kev", "javac"));
        data.engadirPerfil(new Perfil("user", "user"));
    }

    private void loadPostest() {
        data.engadirPerfil(new Perfil("poster1", "poster1"));
        data.engadirPerfil(new Perfil("poster2", "poster2"));

        data.buscarPerfil("poster1").getFriendList().add(data.buscarPerfil("poster2"));
        data.buscarPerfil("poster2").getFriendList().add(data.buscarPerfil("poster1"));

        data.buscarPerfil("poster1").getInbox().add(new Publicacion(data.buscarPerfil("poster1"), "blablabla1-1"));
        data.buscarPerfil("poster1").getInbox().add(new Publicacion(data.buscarPerfil("poster1"), "blablabla2-1"));
        data.buscarPerfil("poster1").getInbox().add(new Publicacion(data.buscarPerfil("poster2"), "blablabla1-2"));
        data.buscarPerfil("poster2").getInbox().add(new Publicacion(data.buscarPerfil("poster1"), "blablabla3"));
        data.buscarPerfil("poster2").getInbox().add(new Publicacion(data.buscarPerfil("poster2"), "blablabla4"));

        data.buscarPerfil("poster2").getInbox().get(0).getComments().add(new Comentario(data.buscarPerfil("poster1"), "Comentario numero 1"));
        data.buscarPerfil("poster2").getInbox().get(0).getComments().add(new Comentario(data.buscarPerfil("poster1"), "Comentario numero 2"));
        data.buscarPerfil("poster2").getInbox().get(0).getComments().add(new Comentario(data.buscarPerfil("poster1"), "Comentario numero 3"));
        data.buscarPerfil("poster2").getInbox().get(0).getComments().add(new Comentario(data.buscarPerfil("poster2"), "Comentario numero 1"));
    }

    private void loadFriendtest() {
        data.engadirPerfil(new Perfil("sender1", "sender1"));
        data.engadirPerfil(new Perfil("sender2", "sender2"));
        data.engadirPerfil(new Perfil("reciever", "reciever"));

        current = data.buscarPerfil("sender1");
        current.engadirSolicitudeDeAmistade(data.buscarPerfil("reciever"));
        current = data.buscarPerfil("sender2");
        current.engadirSolicitudeDeAmistade(data.buscarPerfil("reciever"));
        current = null;
    }

    private void loadSimuLib() {
        //Create users
        data.engadirPerfil(new Perfil("testGuy", "1234", "Hiii", "This is a biography"));
        data.engadirPerfil(new Perfil("randomUser_", "psswd123", "Eating", "This is another biography"));
        data.engadirPerfil(new Perfil("lad", "cotl234", "ñ", "This is yet another biography"));
        data.engadirPerfil(new Perfil("uppercasegGuy", "1231234", "G g", "This is the forth biography"));
        data.engadirPerfil(new Perfil("secondLad", "sec12", ":)))", "This isnt a biography"));
        data.engadirPerfil(new Perfil("testGuy_2", "12345678", "Hiii, heloo", "c:"));
        data.engadirPerfil(new Perfil("testGuyWasTaken", "1", "testGuy :(", "AAAAAAAAAAAA"));

    }
}
