import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int id;
    private double price;
    private Spectator spectator;
    private Session sessions;

    public Ticket(Spectator spectator, Session session, double price) {
        this.spectator = spectator;
        this.sessions = session;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSpectator(Spectator spectator) {
        this.spectator = spectator;
    }

    public Session getSessions() {
        return sessions;
    }

    public void setSessions(Session sessions) {
        this.sessions = sessions;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public Spectator getSpectator() {
        return spectator;
    }

}
