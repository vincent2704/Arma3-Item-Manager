package arma.arma3app;

import java.util.Locale;
import java.util.ResourceBundle;

public class Lang {

	private static ResourceBundle bundle;

	public static void init(Locale locale) {
		bundle = ResourceBundle.getBundle("langs.Lang", locale);
	}

	public static String t(String key) {
		return bundle.getString(key);

	}
}
