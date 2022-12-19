
import java.util.ArrayList;


/**
 *
 * @author XoanOuteiro & Samuyo
 */
public class Evento {
    
    private Perfil host;
    private ArrayList <Perfil> goers;
    private String topic;
    private String date;
    
    public Evento(Perfil host, String topic, String date){
        this.host = host;
        this.topic = topic;
        this.date = date;
        this.goers = new ArrayList();
    }

    public Perfil getHost() {
        return host;
    }

    public ArrayList<Perfil> getGoers() {
        return goers;
    }

    public String getTopic() {
        return topic;
    }

    public String getDate() {
        return date;
    }

    public void setHost(Perfil host) {
        this.host = host;
    }

    public void setGoers(ArrayList<Perfil> goers) {
        this.goers = goers;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
