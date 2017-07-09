package arma.arma3app;

import java.util.List;

import arma.itemdao.BronieDao;
import arma.itemdb.Bronie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class BronieOkno extends BorderPane {


	App app;

	private ObservableList<Bronie> listaObsBroni;

	private List<Bronie> listaBroni;

	BronieOkno(App app) {
		this.app = app;

		Button btnBack = new Button("Powrót");
		btnBack.setOnAction(event -> {
			app.moveToGlowny();
		});
		setBottom(btnBack);

		listaBroni = BronieDao.getListaBroni();

		setCenter(createTabelaBroni());
		setTop(gp());

	}

	private TableView<Bronie> createTabelaBroni() {
		TableView<Bronie> tabela = new TableView<Bronie>();

		TableColumn<Bronie, String> colModelBroni = new TableColumn<Bronie, String>("Model broni");
		colModelBroni.setCellValueFactory(new PropertyValueFactory<>("model_broni"));
		TableColumn<Bronie, Integer> colIlosc = new TableColumn<Bronie, Integer>("Ilość");
		colIlosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
		TableColumn<Bronie, Double> colKaliber = new TableColumn<Bronie, Double>("Kaliber");
		colKaliber.setCellValueFactory(new PropertyValueFactory<>("kaliber"));

		tabela.getColumns().addAll(colModelBroni, colIlosc, colKaliber);

		listaObsBroni = FXCollections.observableArrayList(listaBroni);
		tabela.setItems(listaObsBroni);

		return tabela;
	}

	private HBox hbox() {
		HBox hbox = new HBox(10);

		Button btnPlus = new Button("+1");
		hbox.getChildren().add(btnPlus);

		Button btnMinus = new Button("-1");
		hbox.getChildren().add(btnMinus);

		return hbox;
	}

	private GridPane gp() {
		GridPane gp = new GridPane();
		Button btnDodajBron = new Button("Dodaj broń");

		btnDodajBron.setMaxWidth(USE_COMPUTED_SIZE);
		btnDodajBron.setOnAction(event -> {
			app.moveToDodajBron();
			;
		});

		gp.add(btnDodajBron, 1, 0);
		gp.setHalignment(btnDodajBron, HPos.RIGHT);

		return gp;
	}

}
