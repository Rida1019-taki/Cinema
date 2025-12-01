import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cinema {
    private String name;
    private List<Film> films = new ArrayList<>();
    private List<Spectator> spectators = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<Session> sessions = new ArrayList<>();


    public Cinema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public List<Spectator> getSpectators() {
        return spectators;
    }

    public void setSpectators(List<Spectator> spectators) {
        this.spectators = spectators;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addFilm(Film film) {
        films.add(film);
    }

    public void showFilms() {
        for (Film f : films) {
            System.out.println(f.getId() + " " +
                    f.getTitle() + " " + f.getDuration() + " " +
                    f.getCategory());
        }
    }

    public void removeFilm(int filmId) {
        Film filmToRemove = null;

        for (Film f : films) {
            if (f.getId() == filmId) {
                filmToRemove = f;
                break;
            }
        }

        if (filmToRemove != null) {
            films.remove(filmToRemove);
            System.out.println("Film removed from memory: " + filmToRemove.getTitle());
        } else {
            System.out.println("Film not found in list!");
        }
    }


    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void FindSpectator(int s){

        for (Spectator sp : spectators){
            if (sp.getId() == s){
                System.out.println("ID : " + sp.getId() + "| Name : "
                        + sp.getName() + "| Email : " + sp.getEmail());
            }
        }
    }

    public void ShowSession() {
        for(Session session : sessions) {
            System.out.println("Titre : " + session.getFilm().getTitle()
                    + " | Horaire : " + session.getTime()
                    + " | Salle : " + session.getRoom()
                    + " | Capacite : " + session.getMaxAbility());
        }
    }

    public void addSession(Session ses){
        sessions.add(ses);
    }

    public void showTickets() {
        for (Ticket t : tickets) {
            System.out.println(
                    "Spectator: " + t.getSpectator().getName() +
                            " | Film: " + t.getSessions().getFilm().getTitle() +
                            " | Time: " + t.getSessions().getTime() +
                            " | Room: " + t.getSessions().getRoom() +
                            " | Price: " + t.getPrice()
            );
        }
    }

}