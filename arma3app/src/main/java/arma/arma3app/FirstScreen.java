package arma.arma3app;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class FirstScreen extends GridPane {

	App app;
	private Button btnBronie;
	private Button btnAmunicja;

	public FirstScreen(App app) {
		this.app = app;

		setVgap(10);
		setHgap(10);

		setAlignment(Pos.CENTER);

		btnBronie = new Button();
		applyEffects(btnBronie, "Baza broni");
		btnBronie.setOnAction(event -> {
			app.moveToBronieOkno();
		});
		add(btnBronie, 0, 0);

		btnAmunicja = new Button();
		applyEffects(btnAmunicja, "Baza amunicji");
		btnAmunicja.setOnAction(event -> {
			app.moveToAmunicjaOkno();
		});
		add(btnAmunicja, 0, 1);

		Button btnKonf = new Button();
		applyEffects(btnKonf, "Konfiguracja");
		btnKonf.setOnAction(event -> {
			app.moveToKonfiguracjaOkno();
		});
		add(btnKonf, 0, 2);
	}

	private void applyEffects(Button btn, String txt) {
		InnerShadow shadowbtn = new InnerShadow();
		shadowbtn.setHeight(100);
		shadowbtn.setWidth(300);
		shadowbtn.setOffsetY(40);
		// shadow.setInput(new ColorAdjust(0.5, 0.5, 0.3, 0));
		shadowbtn.setColor(Color.ANTIQUEWHITE);

		btn.getStyleClass().add("ghost-button");
		btn.setMaxHeight(Double.MAX_VALUE);
		btn.setPrefWidth(300);
		btn.setPrefHeight(100);
		btn.setEffect(shadowbtn);
		btn.setMaxWidth(Double.MAX_VALUE);
		
		InnerShadow shadowtxt = new InnerShadow();
		shadowtxt.setHeight(80);
		shadowtxt.setWidth(250);
		shadowtxt.setOffsetY(15);
		shadowtxt.setColor(Color.WHEAT);

		Text txBronie = new Text(txt);
		txBronie.setFont(Font.font(null, FontWeight.BOLD, 40));
		txBronie.setWrappingWidth(300);
		txBronie.setTextAlignment(TextAlignment.CENTER);
		txBronie.setFill(Color.WHITESMOKE);

		btn.setGraphic(txBronie);
		btn.setOnMouseEntered(event -> {
			txBronie.setFill(Color.BLACK);
			btn.setEffect(null);
			txBronie.setEffect(shadowtxt);
		});
		btn.setOnMouseExited(event -> {
			txBronie.setFill(Color.WHITESMOKE);
			btn.setEffect(shadowbtn);
			txBronie.setEffect(null);
		});
	}

	public void enableButtons(boolean enable) {
		btnBronie.setDisable(!enable);
		btnAmunicja.setDisable(!enable);

	}

}
