package huitca1212.frotaalcalvo.business;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import huitca1212.frotaalcalvo.model.Advices;
import huitca1212.frotaalcalvo.service.AdvicesService;
import huitca1212.frotaalcalvo.service.ApiUtils;
import huitca1212.frotaalcalvo.ui.CircularList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvicesBusiness {

	private static RuntimeExceptionDao<Advices, Integer> advicesDao;

	public static void getAdvices(final AllBusinessListener<CircularList<String>> listener) {
		getAdvicesFromDatabase(new AllBusinessListener<CircularList<String>>() {
			@Override
			public void onDatabaseSuccess(CircularList<String> object) {
				if (object != null && !object.isEmpty()) {
					listener.onDatabaseSuccess(object);
				} else {
					listener.onDatabaseSuccess(new CircularList<String>());
				}
				getAdvicesFromBackend(listener);
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onFailure(Exception e) {
				Log.e("AdvicesBusiness", "Error in DB", e);
			}
		});
	}

	private static void getAdvicesFromDatabase(final AllBusinessListener<CircularList<String>> listener) {
		new DefaultAsyncTask<String, CircularList<String>>(listener) {
			@Override
			protected CircularList<String> dbOperation(String... params) throws SQLException {
				advicesDao = DatabaseHelper.getDbHelper().getAdvicesDao();
				ArrayList<Advices> advices = (ArrayList<Advices>) advicesDao.queryBuilder().query();

				if (advices != null && !advices.isEmpty()) {
					return advices.get(0).getAdvices();
				} else {
					return new CircularList<>();
				}
			}
		}.execute();
	}

	private static void getAdvicesFromBackend(final AllBusinessListener<CircularList<String>> listener) {
		AdvicesService advicesService = ApiUtils.getAdvicesService();
		advicesService.getAdvices().enqueue(new Callback<Advices>() {
			@Override
			public void onResponse(Call<Advices> call, Response<Advices> response) {
				advicesDao.createOrUpdate(response.body());
				if (listener != null) {
					if (response.isSuccessful()) {
						listener.onServerSuccess(response.body().getAdvices());
					} else {
						listener.onFailure(new Exception());
					}
				}
			}

			@Override
			public void onFailure(Call<Advices> call, Throwable t) {
				if (listener != null) {
					listener.onFailure(new Exception());
				}
			}
		});
	}
}
