package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Model.MovieModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    public Button btnUpdate;
    public Button btnDelete;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtYear;

    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lstMovies.setItems(movieModel.getObservableMovies());

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

        lstMovies.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Movie>() {
            @Override
            public void changed(ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) {
                //Sets the values of selected movie into their appropriate text fields
                if (newValue != null) {
                    btnUpdate.setDisable(false);
                    btnDelete.setDisable(false);
                    txtTitle.setText(newValue.getTitle());
                    txtYear.setText(String.valueOf(newValue.getYear()));
                } else {
                    btnUpdate.setDisable(true);
                    btnDelete.setDisable(true);
                }

            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }


    public void handleAddNewMovie(ActionEvent actionEvent) {
        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());

        try {
            movieModel.createNewMovie(title,year);
        } catch (Exception e) {
            displayError(e);
        }

    }

    public void handleUpdate(ActionEvent actionEvent) {
        System.out.println("Update btn clicked");

        try {
            Movie updatedMovie = lstMovies.getSelectionModel().getSelectedItem();

            //set values from textfields to the updated movie
            updatedMovie.setTitle(txtTitle.getText());
            updatedMovie.setYear(Integer.parseInt(txtYear.getText()));

            movieModel.updateMovie(updatedMovie);
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
        try {
            Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();

            movieModel.deleteMovie(selectedMovie);
        } catch (Exception e) {
            displayError(e);
        }
    }
}
