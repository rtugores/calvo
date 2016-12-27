package huitca1212.frotaalcalvo.service;

public class ApiUtils {

	private static final String BASE_URL = "http://rjapps.x10host.com/baldManApp/";

	public static AdvicesService getAdvicesService() {
		return RetrofitClient.getClient(BASE_URL).create(AdvicesService.class);
	}
}