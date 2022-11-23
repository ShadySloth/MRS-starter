package easv.mrs.BE;

import easv.mrs.DAL.MovieDAO;
import easv.mrs.DAL.db.MyDatabaseConnector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InsertMoviesFromFile {
    MovieDAO movieFile = new MovieDAO();
    private MyDatabaseConnector databaseConnector;

    public InsertMoviesFromFile() throws SQLException, IOException {
        databaseConnector = new MyDatabaseConnector();
    }

    public void moviesToSQL() throws IOException, SQLException {
        int counter = 0;
        String sql = "INSERT INTO Movie (Title, Year) VALUES (?,?);";
        Connection conn = databaseConnector.getConnection();

        for (Movie m: movieFile.getAllMovies()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Bind parameters
            stmt.setString(1,m.getTitle());
            stmt.setInt(2,m.getYear());

            //Run the specified SQL statement
            stmt.executeUpdate();

            //Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            //Create movie object and send up the layers
            Movie movie = new Movie(id, m.getYear(), m.getTitle());

            counter++;
            System.out.println(counter);
        }

    }
    public static void main(String[] args) throws SQLException, IOException {

        InsertMoviesFromFile insertmovies = new InsertMoviesFromFile();
        insertmovies.moviesToSQL();
    }
}
