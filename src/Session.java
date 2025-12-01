public class Session {
    private int sessionID;
    private String time;
    private String room;
    private int maxAbility;
    private Film film; // objet Film obligatoire

    public Session(int sessionID, String time, String room, int maxAbility, Film film) {
        this.sessionID = sessionID;
        this.time = time;
        this.room = room;
        this.maxAbility = maxAbility;
        this.film = film;
    }

    public Film getFilm() { return film; }
    public String getTime() { return time; }
    public String getRoom() { return room; }
    public int getMaxAbility() { return maxAbility; }
}