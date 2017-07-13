package arma.arma3app;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import arma.itemdao.AmunicjaDao;
import arma.itemdao.BronieDao;
import arma.itemdb.Amunicja;
import arma.itemdb.Bronie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class WiecejOpcjiOkno extends Dialog<Boolean> {

	private Bronie bron;
	private boolean zmieniono;
	private ListView<Amunicja> matchingAmmo;
	private ComboBox<Amunicja> matchingCal;

	public WiecejOpcjiOkno(Bronie bron) {
		this.bron = bron;

		setTitle("Więcej opcji: " + bron.getModel_broni());
		ButtonType btnZapisz = new ButtonType("Zapisz", ButtonData.OK_DONE);
		ButtonType btnAnuluj = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(btnZapisz, btnAnuluj);

		Button btnUsunBron = new Button("Usuń broń");
		btnUsunBron.setOnAction(event -> {
			BronieDao.deleteBronie(bron);
			zmieniono = true;

		});

		matchingAmmo = new ListView<>();
		Button btnUsunAmmo = new Button("Usuń amunicję");
		Label lbLista = new Label("Przypisane magazynki:");

		Set<Amunicja> listMatchingAmmo = bron.getAmunicja();
		ObservableList<Amunicja> obsListMatchingAmmo = FXCollections
				.observableList(new ArrayList<Amunicja>(listMatchingAmmo));

		matchingAmmo.setItems(obsListMatchingAmmo);

		List<Amunicja> lstMatchingKaliberAmmo = AmunicjaDao.getAmunicjaPoKalibrzeAmmo(bron.getKaliber());
		ObservableList<Amunicja> obslstMatchingKaliberAmmo = FXCollections.observableList(lstMatchingKaliberAmmo);

		GridPane gp = new GridPane();
		gp.setVgap(10);
		gp.setHgap(10);

		gp.add(btnUsunBron, 0, 0);
		gp.add(lbLista, 0, 1);
		gp.add(btnUsunAmmo, 1, 0);
		gp.add(matchingAmmo, 0, 2);

		// przypisywanie
		GridPane gpCal = new GridPane();
		Label lbMatchingCal = new Label("Wybierz magazynek do przypisania");
		matchingCal = new ComboBox<>();

		matchingCal.setItems(obslstMatchingKaliberAmmo);
		Button btnPrzypisz = new Button("Przypisz");
		gpCal.add(lbMatchingCal, 0, 0);
		gpCal.add(matchingCal, 0, 1);
		gpCal.add(btnPrzypisz, 0, 2);
		btnPrzypisz.setOnAction(event -> {
			bron.getAmunicja().add(matchingCal.getValue());
			BronieDao.updateBronie(bron);
		});

		// odlaczanie przypisan
		GridPane gpOdlacz = new GridPane();
		Label lbOdlacz = new Label("Odłącz powiązanie:");
		ComboBox<String> associated = new ComboBox<String>();

		gp.add(gpCal, 1, 2);

		getDialogPane().setContent(gp);

		setResultConverter(dialogButton -> {
			if (dialogButton == btnZapisz) {
				save();
				return zmieniono;
			}
			return false;
		});


	}

	private void save() {

	}

}
