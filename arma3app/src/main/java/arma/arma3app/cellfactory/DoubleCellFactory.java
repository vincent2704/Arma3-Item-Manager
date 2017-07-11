package arma.arma3app.cellfactory;

import javafx.scene.control.*;
import javafx.util.Callback;

//interfejsy sie wziely z tych, ktore implementuje metoda setcellvaluefactory. Klasa ma na celu modyfikacje zapisu double w tableview
//chodzi o wartosci double kalibru jaki sie pojawia w oknach

public class DoubleCellFactory<S> implements Callback<TableColumn<S, Double>, TableCell<S, Double>> {

   // metoda z interfejsu
   @Override
   public TableCell<S, Double> call(TableColumn<S, Double> param) {
      final TableCell<S, Double> tableCell = new TableCell<S, Double>() {
	 @Override
	 public void updateItem(Double item, boolean empty) {
	    super.updateItem(item, empty);
	    if (empty) {
	       setGraphic(null);
	       setText(null);
	    } else {
	       String itemS = item.toString();
	       if (itemS.startsWith("0")) {
		  itemS = itemS.substring(1);
	       }
	       setText(itemS);
	    }
	 }
      };

      return tableCell;
   }

}
