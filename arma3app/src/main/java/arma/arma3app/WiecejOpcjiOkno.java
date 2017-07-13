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
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class WiecejOpcjiOkno extends Dialog<Boolean> {

	private Bronie bron;
	private boolean zmieniono, toDelete, ammoZmienionePow, customIlosc;
	private ListView<Amunicja> matchingAmmo;
	private ComboBox<Amunicja> matchingCal;

	public WiecejOpcjiOkno(Bronie bron) {
		this.bron = bron;

		setTitle("Więcej opcji: " + bron.getModel_broni());
		setupButtons();


		Label lbLista = new Label("Przypisane magazynki:");

		matchingAmmo = new ListView<>();
		Set<Amunicja> listMatchingAmmo = bron.getAmunicja();
		ObservableList<Amunicja> obsListMatchingAmmo = FXCollections
				.observableList(new ArrayList<Amunicja>(listMatchingAmmo));

		matchingAmmo.setItems(obsListMatchingAmmo);


		GridPane gp = new GridPane();
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setGridLinesVisible(true);
		gp.add(lbLista, 0, 1);
		gp.add(matchingAmmo, 0, 2);

		// przypisywanie
		GridPane gpCal = new GridPane();
		gpCal.setVgap(10);
		Label lbMatchingCal = new Label("Wybierz magazynek do przypisania");
		matchingCal = new ComboBox<>();
		matchingCal.setPrefWidth(187);
		List<Amunicja> lstMatchingKaliberAmmo = AmunicjaDao.getAmunicjaPoKalibrzeAmmo(bron.getKaliber());
		ObservableList<Amunicja> obslstComboBox = FXCollections.observableList(lstMatchingKaliberAmmo);
		for (Amunicja a : obsListMatchingAmmo) {
			if (obslstComboBox.contains(a)) {
				obslstComboBox.remove(a);
			}
		}
		matchingCal.setItems(obslstComboBox);
		Button btnPrzypisz = new Button("Przypisz");
		btnPrzypisz.setOnAction(event -> {
			Amunicja ammo = matchingCal.getValue();
			if (ammo != null) {
			bron.getAmunicja().add(ammo);
			obsListMatchingAmmo.add(ammo);
			obslstComboBox.remove(ammo);
			ammoZmienionePow = true;
			zmieniono = true;
			}
		});
		TextField tfCustomIlosc = new TextField("Ilość");
		tfCustomIlosc.setOnMousePressed(event -> {
			tfCustomIlosc.clear();
		});

		tfCustomIlosc.setMaxWidth(50);

		tfCustomIlosc.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				bron.setIlosc(Integer.parseInt(tfCustomIlosc.getText()));
				BronieDao.updateBronie(bron);
				customIlosc = true;

			}
		});
		Label lbCustomIlosc = new Label("Wprowadź nową ilość:");
		gpCal.add(lbCustomIlosc, 0, 3);
		gpCal.add(tfCustomIlosc, 0, 4);
		gpCal.add(lbMatchingCal, 0, 0);
		gpCal.add(matchingCal, 0, 1);
		gpCal.add(btnPrzypisz, 0, 2);

		// odlaczanie przypisan
		Button btnUsunAmmo = new Button("Usuń amunicję");
		btnUsunAmmo.setOnAction(event -> {
			Amunicja selectedItem = matchingAmmo.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				obsListMatchingAmmo.remove(selectedItem);
				obslstComboBox.add(selectedItem);
				bron.getAmunicja().remove(selectedItem);
				ammoZmienionePow = true;
				zmieniono = true;
			}
		});
		gp.add(btnUsunAmmo, 0, 3);
		gp.setValignment(btnUsunAmmo, VPos.TOP);

		Button btnUsunBron = new Button("Usuń broń");
		btnUsunBron.setOnAction(event -> {
			toDelete = true;
			zmieniono = true;

		});
		gp.add(btnUsunBron, 1, 3);
		gp.setHalignment(btnUsunBron, HPos.RIGHT);

		gp.add(gpCal, 1, 2);
		getDialogPane().setContent(gp);

	}

	private void setupButtons() {
		ButtonType btnZapisz = new ButtonType("Zapisz", ButtonData.OK_DONE);
		ButtonType btnAnuluj = new ButtonType("Anuluj", ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(btnZapisz, btnAnuluj);
		setResultConverter(dialogButton -> {
			if (dialogButton == btnZapisz) {
				save();
				return zmieniono;
			}
			return false;
		});
	}

	private void save() {
		if (zmieniono) {
			if (toDelete) {
				BronieDao.deleteBronie(bron);
			}
			if (ammoZmienionePow || customIlosc) {
				BronieDao.updateBronie(bron);
			}
		}
	}

}
