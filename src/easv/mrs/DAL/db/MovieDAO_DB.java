package easv.mrs.DAL.db;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.IMovieDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {

        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM Movie;";

            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                //Map DB row to Movie object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                int year = rs.getInt("Year");

                Movie movie = new Movie(id, year, title);
                allMovies.add(movie);
            }
            return allMovies;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not get movies from database", ex);
        }

    }

    public Movie createMovie(String title, int year) throws Exception {

        String sql = "INSERT INTO Movie (Title, Year) VALUES (?,?);";

        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Bind parameters
            stmt.setString(1,title);
            stmt.setInt(2,year);

            //Run the specified SQL statement
            stmt.executeUpdate();

            //Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            //Create movie object and send up the layers
            Movie movie = new Movie(id, year, title);
            return movie;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not create movie", ex);
        }

    }

    public void updateMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public void deleteMovie(Movie movie) throws Exception {
        //TODO Do this
        throw new UnsupportedOperationException();
    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

}
