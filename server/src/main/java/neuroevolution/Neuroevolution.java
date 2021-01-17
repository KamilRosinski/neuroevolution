package neuroevolution;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Neuroevolution extends Application {

	public static void Main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		final Button button = new Button("Click me!");
		final Label label = new Label("Button not yet clicked");

		button.setOnAction(event -> label.setText("Button clicked"));

		final VBox vBox = new VBox(button, label);
		final Scene scene = new Scene(vBox, 400, 300);

		stage.setTitle("Neuroevolution");
		stage.setScene(scene);
		stage.show();
	}

}
