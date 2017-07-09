package arma.arma3app.cellfactory;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

//interfejsy sie wziely z tych, ktore implementuje metoda setcellvaluefactory. Klasa ma na celu modyfikacje zapisu double w tableview
//chodzi o wartosci double kalibru jaki sie pojawia w oknach

public class DoubleCellFactory<S> implements Callback<TableColumn<S, Double>, TableCell<S, String>> {

	// metoda z interfejsu
	@Override
	public TableCell<S, String> call(TableColumn<S, Double> param) {
		final TableCell<S, String> tableCell = new TableCell<S, String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					setText(item);
				}
			}
		};

		return tableCell;
	}


}
