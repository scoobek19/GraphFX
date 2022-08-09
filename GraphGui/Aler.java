package app.graphgui;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Aler extends Alert{
    Image im = new Image(getClass().getResourceAsStream("alert.jpg"));
    ImageView imageView = new ImageView(im);
    public Aler(AlertType alertType) {
        super(alertType);
    }

    public void alertText(String title, String head, String content){
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(im);
        this.setGraphic(imageView);
        this.setTitle(title);
        this.setHeaderText(head);
        this.setContentText(content);
        this.show();
    }
}
