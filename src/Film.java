import java.util.ArrayList;
import java.util.List;

public class Film {
    private int id;
    private String title;
    private String duration;
    private String category;
    private List<Session> seances = new ArrayList<>();

    public Film(int id ,String title, String duration, String category) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void addSeance(Session s) {
        seances.add(s);
    }
}