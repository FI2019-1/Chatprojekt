package Client;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public interface WindowProperty
{
    default void DragDrop(Parent root, Stage stage)
    {
        root.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            double xOffset = root.getBoundsInParent().getWidth() / 2;
            double yOffset = 20;

            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            double xOffset = root.getBoundsInParent().getWidth() / 2;
            double yOffset = 20;

            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
