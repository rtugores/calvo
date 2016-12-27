package huitca1212.frotaalcalvo.service;

import huitca1212.frotaalcalvo.model.Advices;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AdvicesService {

	@GET("advices.json")
	Call<Advices> getAdvices();
}