package org.xine.flywaynominator;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.xine.flywaynominator.presentation.dashboard.DashboardView;

import com.airhacks.afterburner.injection.Injector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		/*
		 * Properties of any type can be easily injected.
		 */
		LocalDate date = LocalDate.of(4242, Month.JULY, 21);
		Map<Object, Object> customProperties = new HashMap<>();
		customProperties.put("date", date);
		
	    /*
         * any function which accepts an Object as key and returns
         * and return an Object as result can be used as source.
         */
		Injector.setConfigurationSource(customProperties::get);


		DashboardView appView = new DashboardView();
		Scene scene = new Scene(appView.getView());
		stage.setTitle("flywaynominator.fx");
	    final String uri = getClass().getResource("app.css").toExternalForm();
	    scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();
	}

	@Override
	public void stop() throws Exception {
		Injector.forgetAll();
		super.stop();
	}


	public static void main(String[] args) {
		launch(args);
	}

	//	public static void main(String[] args) {
	//		ZonedDateTime zdt = ZonedDateTime.now();
	//		ZoneId zoneId = ZoneId.of("UTC+1");
	//		ZonedDateTime zonedDateTime2 = ZonedDateTime.of(2015, 11, 30, 23, 45, 59, 1234, zoneId);
	//		
	//		Calendar born = GregorianCalendar.from(zdt) ;
	//		Funcionario funcionario = new Funcionario(born );
	//		
	//		System.err.println(funcionario);
	//	}

}
