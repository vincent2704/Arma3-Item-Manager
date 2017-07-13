package arma.arma3app;

import java.util.List;

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
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class WiecejOpcjiOkno extends Dialog<Boolean> {

	private Bronie bron;
	private Amunicja amunicja;
	private boolean zmieniono;

	List<String> listMatchingAmmo;
	ObservableList<String> obsListAmmoName;
	List<String> lstMatchingKaliberAmmo;
	ObservableList<String> obslstMatchingKaliberAmmo;

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

		ListView<String> matchingAmmo = new ListView<>();
		ComboBox<String> matchingCal = new ComboBox<>();
		Button btnUsunAmmo = new Button("Usuń amunicję");

		listMatchingAmmo = AmunicjaDao.getNazwaAmmo();
		obsListAmmoName = FXCollections.observableList(listMatchingAmmo);

		matchingAmmo.setItems(obsListAmmoName);

		lstMatchingKaliberAmmo = AmunicjaDao.getNazwaPoKalibrzeAmmo(bron.getKaliber());
		obslstMatchingKaliberAmmo = FXCollections.observableList(lstMatchingKaliberAmmo);

		matchingCal.setItems(obslstMatchingKaliberAmmo);




		GridPane gp = new GridPane();
		gp.setVgap(10);
		gp.setHgap(10);
		gp.add(btnUsunBron, 0, 0);
		gp.add(btnUsunAmmo, 1, 0);
		gp.add(matchingAmmo, 0, 1);
		gp.add(matchingCal, 1, 1);

		getDialogPane().setContent(gp);

		setResultConverter(dialogButton -> {
			if (dialogButton == btnZapisz) {
				return zmieniono;
			}
			return false;
		});


	}


}
