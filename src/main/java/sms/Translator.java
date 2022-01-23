package sms;

import java.util.HashMap;

/**
 * The enum that represents the selected language
 * 
 * @author Artiom
 *
 */
enum Language {
	ENG, RU, RO
}

/**
 * The class that handles internationalization and language changes
 * 
 * @author Artiom
 *
 */
public class Translator {
	/**
	 * HashMap that holds tags as keys and messages as values
	 */
	private static HashMap<String, String> messages;
	/**
	 * The variable that holds currently selected language
	 */
	private static Language language;

	static {
		messages = new HashMap<String, String>();
		setLanguage(Language.ENG);
	}

	/**
	 * The constructor
	 */
	public Translator() {

	}

	/**
	 * Gets messages in language selected by the user
	 * 
	 * @param language - Language user wishes to select
	 */
	public static void getValue(final String key) {

	}

	public static void main(String[] args) {
		getValue("connectButton");
	}

	/**
	 * @return the currently selected language
	 */
	public static Language getLanguage() {
		return language;
	}

	/**
	 * @param language - Language user wishes to select
	 */
	public static void setLanguage(Language language) {
		Translator.language = language;
	}
}
