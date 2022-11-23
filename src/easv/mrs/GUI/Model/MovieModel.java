package easv.mrs.GUI.Model;

import easv.mrs.BE.Movie;
import easv.mrs.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }



    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public void createNewMovie(String title, int year) throws Exception {
        //Create movie to data storage
        Movie m = movieManager.createNewMovie(title, year);

        //Add movie to observable list
        moviesToBeViewed.add(m);
    }

    public void updateMovie(Movie updatedMovie) throws Exception {
        //Call BLL
        //update movie in db
        movieManager.updateMovie(updatedMovie);

        //update ListView
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public void deleteMovie(Movie selectedMovie) throws Exception {
        //delete movie in db
        movieManager.deleteMovie(selectedMovie);

        //update ListView
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }
}
