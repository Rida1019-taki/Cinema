import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Cinema cinema = new Cinema("testing cinema");
    static List<Spectator> spectators = new ArrayList<>();
    static List<Film> films = new ArrayList<>();

    public static void menu() {
        System.out.println("-- Menu Principal --");
        System.out.println(". Films Management");
        System.out.println("    1. Show Films");
        System.out.println("    2. Add Film");
        System.out.println("    3. Remove Film");
        System.out.println(". Sessions Management");
        System.out.println("    4. Show Sessions");
        System.out.println("    5. Add Session");
        System.out.println(". Spectators Management");
        System.out.println("    6. Add Spectator");
        System.out.println("    7. Find Spectator");
        System.out.println("    8. Show Spectators");
        System.out.println(". Tickets Management");
        System.out.println("    9. Show The Past Tickets");
        System.out.println("0. Quit");
        System.out.print("  Enter your choice: ");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cine",
                "root",
                ""
        );
    }

    public static void main(String[] args) {

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Films");
            while (rs.next()) {
                int id = rs.getInt("idFilm");
                String title = rs.getString("titre");
                String duration = rs.getString("duree");
                String category = rs.getString("categorie");

                Film film = new Film(id, title, duration, category);
                cinema.addFilm(film);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("    === Cinema Management System ===");

        String choice;
        while (true) {
            menu();
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n--------------------------- Films -------------------------------");
                    cinema.showFilms();
                    break;
                case "2":
                    System.out.print("Enter title Film: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Duree : ");
                    String duree = scanner.nextLine();
                    System.out.print("Enter Category : ");
                    String categorie = scanner.nextLine();

                    try {
                        Connection conn = getConnection();
                        String sql = "INSERT INTO Films(titre , duree , categorie) VALUES (?, ? , ?)";
                        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, title);
                        ps.setString(2, duree);
                        ps.setString(3, categorie);


                        int rows = ps.executeUpdate();

                        if (rows > 0) {
                            ResultSet keys = ps.getGeneratedKeys();
                            if (keys.next()) {
                                int id = keys.getInt(1);

                                Film f = new Film(id , title , duree , categorie);
                                cinema.addFilm(f);

                                System.out.println("Film added successfully! ID = " + id);
                            }
                        }

                        ps.close();
                        conn.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    System.out.println("-------------------------------------------------------------------");
                    break;

                case "3":
                    System.out.print("ID Film : ");
                    int filmId = scanner.nextInt();
                    scanner.nextLine();
                    cinema.removeFilm(filmId);
                    try {
                        Connection conn = getConnection();
                        String sql = "DELETE FROM Films WHERE idFilm = ?";
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setInt(1, filmId);

                        int rows = pst.executeUpdate();
                        if (rows > 0) {
                            System.out.println("Film deleted successfully! ID = " + filmId);
                        } else {
                            System.out.println("Film not found with ID = " + filmId);
                        }

                        pst.close();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    break;

                case "4":
                    try {
                        Connection conn = getConnection();
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Seances");

                        while (rs.next()) {
                            int seancesID = rs.getInt("seancesID");
                            String horaire = rs.getString("horaire");
                            String salle = rs.getString("salle");
                            int capacite = rs.getInt("capacite");
                            int filmsID = rs.getInt("filmsID");

                            Film filmForSession = null;
                            for(Film f : cinema.getFilms()) {
                                if(f.getId() == filmsID) {
                                    filmForSession = f;
                                    break;
                                }
                            }
                            Session session = new Session(seancesID, horaire, salle, capacite, filmForSession);
                            cinema.addSession(session);
                        }
                        rs.close();
                        stmt.close();
                        conn.close();
                        cinema.ShowSession();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    break;
                case "5":
                    System.out.print("Enter Film ID for this session: ");
                    int filmID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Horaire (format: 2025-01-10 14:00:00): ");
                    String horaire = scanner.nextLine();

                    System.out.print("Enter Salle: ");
                    String salle = scanner.nextLine();

                    System.out.print("Enter Capacite: ");
                    int capacite = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Connection conn = getConnection();

                        String sql = "INSERT INTO Seances(horaire, salle, capacite, filmsID) VALUES (?, ?, ?, ?)";
                        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                        ps.setString(1, horaire);
                        ps.setString(2, salle);
                        ps.setInt(3, capacite);
                        ps.setInt(4, filmID);

                        int rows = ps.executeUpdate();

                        if (rows > 0) {
                            ResultSet keys = ps.getGeneratedKeys();
                            if (keys.next()) {

                                int seanceID = keys.getInt(1);

                                Film filmForSession = null;
                                for (Film f : cinema.getFilms()) {
                                    if (f.getId() == filmID) {
                                        filmForSession = f;
                                        break;
                                    }
                                }

                                Session session = new Session(seanceID, horaire, salle, capacite, filmForSession);
                                cinema.addSession(session);

                                System.out.println("Seance added successfully! ID = " + seanceID);
                            }
                        }

                        ps.close();
                        conn.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    break;
                case "6":
                    System.out.print("Enter name spectator: ");
                    String nameSp = scanner.nextLine();
                    System.out.print("Enter email spect   ator: ");
                    String email = scanner.nextLine();

                    try {
                        Connection conn = getConnection();
                        String sql = "INSERT INTO Spectators(nom, email) VALUES (?, ?)";
                        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, nameSp);
                        ps.setString(2, email);

                        int rows = ps.executeUpdate();

                        if (rows > 0) {
                            ResultSet keys = ps.getGeneratedKeys();
                            if (keys.next()) {
                                int newId = keys.getInt(1);

                                Spectator s = new Spectator(newId, nameSp, email);
                                spectators.add(s);

                                System.out.println("Spectator added successfully! ID = " + newId);
                            }
                        }

                        ps.close();
                        conn.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "7":
                    try {
                        System.out.print("Enter ID : ");
                        int idSp = scanner.nextInt();
                        scanner.nextLine();


                        Connection conn = getConnection();
                        String sql = "SELECT * FROM spectators WHERE spectateursID = ?";
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setInt(1, idSp);
                        ResultSet rs = pst.executeQuery();

                        if(rs.next()) {
                            System.out.println("Spectator found:");
                            System.out.println("ID : " + rs.getInt("spectateursID")
                                    + " | Name : " + rs.getString("nom")
                                    + " | Email : " + rs.getString("email"));
                        } else {
                            System.out.println("Spectator not found.");
                        }

                        rs.close();
                        pst.close();
                        conn.close();

                        cinema.FindSpectator(idSp);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "9":
                    System.out.println("---------------------------- Add Ticket ----------------------------");

                    try {
                        System.out.print("Enter Spectator ID: ");
                        int spectatorID = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter Session ID: ");
                        int sessionID = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter Ticket Price: ");
                        double price = Double.parseDouble(scanner.nextLine());

                        Connection conn = getConnection();


                        String sqlSp = "SELECT * FROM Spectators WHERE spectateursID = ?";
                        PreparedStatement psSp = conn.prepareStatement(sqlSp);
                        psSp.setInt(1, spectatorID);
                        ResultSet rsSp = psSp.executeQuery();

                        if (!rsSp.next()) {
                            System.out.println("Spectator not found!");
                            rsSp.close();
                            psSp.close();
                            conn.close();
                            break;
                        }

                        Spectator spectator = new Spectator(
                                rsSp.getInt("spectateursID"),
                                rsSp.getString("nom"),
                                rsSp.getString("email")
                        );


                        String sqlSes = "SELECT * FROM Seances WHERE seancesID = ?";
                        PreparedStatement psSes = conn.prepareStatement(sqlSes);
                        psSes.setInt(1, sessionID);
                        ResultSet rsSes = psSes.executeQuery();

                        if (!rsSes.next()) {
                            System.out.println("Session not found!");
                            rsSes.close();
                            psSes.close();
                            rsSp.close();
                            psSp.close();
                            conn.close();
                            break;
                        }

                        int film_ID = rsSes.getInt("filmsID");
                        Film filmForSession = null;
                        for (Film f : cinema.getFilms()) {
                            if (f.getId() == film_ID) {
                                filmForSession = f;
                                break;
                            }
                        }

                        Session session = new Session(
                                rsSes.getInt("seancesID"),
                                rsSes.getString("horaire"),
                                rsSes.getString("salle"),
                                rsSes.getInt("capacite"),
                                filmForSession
                        );

                        Ticket ticket = new Ticket(spectator, session, price);

                        String sqlInsert = "INSERT INTO Tickets(prix, seancesID, spectateursID) VALUES (?, ?, ?)";
                        PreparedStatement psInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

                        psInsert.setDouble(1, price);
                        psInsert.setInt(2, sessionID);
                        psInsert.setInt(3, spectatorID);

                        int rows = psInsert.executeUpdate();

                        if (rows > 0) {
                            ResultSet keys = psInsert.getGeneratedKeys();
                            if (keys.next()) {
                                int ticketID = keys.getInt(1);
                                ticket.setId(ticketID);
                                cinema.addTicket(ticket);
                                System.out.println("✔ Ticket added successfully! ID = " + ticketID);
                            }
                        }


                        psInsert.close();
                        rsSes.close();
                        psSes.close();
                        rsSp.close();
                        psSp.close();
                        conn.close();

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }


                    break;

                case "0":
                    return;
                default:
                    System.out.println("The choice doesn't exist.");
            }
        }
    }
}
