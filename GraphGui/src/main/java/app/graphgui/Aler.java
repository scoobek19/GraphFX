package app.graphgui;
import javafx.scene.control.Alert;

public class Aler extends Alert{

    public Aler(AlertType alertType) {
        super(alertType);
    }

    public void alertText(String title, String head, String content){
        this.setTitle(title);
        this.setHeaderText(head);
        this.setContentText(content);
        this.show();
    }
}
