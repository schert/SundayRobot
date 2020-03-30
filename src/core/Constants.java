package core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Constants {
	private static final Logger log4j = LogManager.getLogger(Constants.class);
	private static Properties errorProperties = new Properties();
	private static Properties warningProperties = new Properties();
	private static Properties uiProperties = new Properties();
	
	public static void initConstants() {
		try {
			InputStream input = Constants.class.getResourceAsStream("/error_message.properties");
			errorProperties.load(input);
			input = Constants.class.getResourceAsStream("/waring_message.properties");
			warningProperties.load(input);
			input = Constants.class.getResourceAsStream("/ui_message.properties");
			uiProperties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			log4j.error("Impossibile leggere il file di properies");
		}
	}
	
	public static interface ERROR {
		public final String INTERRUPT_ACTION = errorProperties.getProperty("action.sleep.after");
		public final String INTERRUPT_RECORD = errorProperties.getProperty("record.sleep.after");
		public final String ROBOT_INSTANCE = errorProperties.getProperty("robot.instance");
		public final String ROBOT_NO_DEVICE = errorProperties.getProperty("record.get.screen");
		public final String INVALID_KEY = errorProperties.getProperty("invalid.key");
		public final String FILE_WRITE_ERROR = errorProperties.getProperty("file.write");
		public final String FILE_READ_ERROR = errorProperties.getProperty("file.read");
	}
	
	public static interface WARNING {
		public final String ACTION_ALREADY_START = warningProperties.getProperty("action.already.start");
		public final String RECORD_ALREADY_START = warningProperties.getProperty("record.already.start");
	}
	
	public static interface SETTINGS {
		public final int INTERVAL = 15;
	}
	
	public static interface UI {
		public final String STOP = uiProperties.getProperty("button.stop");
		public final String PLAY = uiProperties.getProperty("button.play");
		public final String REGISTRA = uiProperties.getProperty("button.rec");
		public final String STOP_REC = uiProperties.getProperty("button.stop.rec");
		public final String PULISCI = uiProperties.getProperty("button.pulisci");
		public final String EXIT_SICURO_TITILE = uiProperties.getProperty("exit.title.sicuro");
		public final String EXIT_SICURO = uiProperties.getProperty("exit.sicuro");
		public final String RIPETI = uiProperties.getProperty("check.ripeti");
		public final String SI = uiProperties.getProperty("exit.si");
		public final String NO = uiProperties.getProperty("exit.no");
		public final String SALVA = uiProperties.getProperty("file.salva");
		public final String CARICA = uiProperties.getProperty("file.carica");
		public final String TITLE = uiProperties.getProperty("title.text");
		public final String ETI_CONTROL = uiProperties.getProperty("eti.control");
		public final String ETI_LOOP = uiProperties.getProperty("eti.loop");
		public final String ETI_FILE = uiProperties.getProperty("eti.file");
	}
}
