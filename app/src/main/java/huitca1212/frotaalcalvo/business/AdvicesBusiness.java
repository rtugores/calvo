package huitca1212.frotaalcalvo.business;

import com.j256.ormlite.dao.RuntimeExceptionDao;

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
	public static final String DAILY_ADVICES = "dailyAdvices";
	public static final String LOVE_ADVICES = "loveAdvices";
	private static RuntimeExceptionDao<Advices, Integer> advicesDao;

	public static void getAdvices(final String adviceType, final AllBusinessListener<CircularList<String>> listener) {
		//TODO: adviceType is not working yet and needs a refactor
		getAdvicesFromDatabase(new AllBusinessListener<CircularList<String>>(listener) {
			@Override
			public void onDatabaseSuccess(CircularList<String> object) {
				if (listener != null) {
					if (object != null && !object.isEmpty()) {
						listener.onDatabaseSuccess(object);
					} else {
						listener.onDatabaseSuccess(new CircularList<String>());
					}
				}
				getAdvicesFromBackend(adviceType, listener);
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

	private static void getAdvicesFromBackend(String adviceType, final AllBusinessListener<CircularList<String>> listener) {
		AdvicesService advicesService = ApiUtils.getAdvicesService();
		Call<Advices> advicesCall;
		if (adviceType.equals(DAILY_ADVICES)) {
			advicesCall = advicesService.getDailyAdvices();
		} else {
			advicesCall = advicesService.getLoveAdvices();
		}
		advicesCall.enqueue(new Callback<Advices>() {
			@Override
			public void onResponse(Call<Advices> call, Response<Advices> response) {
				advicesDao.createOrUpdate(response.body());
				if (listener != null) {
					if (response.isSuccessful()) {
						listener.onServerSuccess(response.body().getAdvices());
					} else {
						listener.onFailure(new Exception());
					}
					listener.onFinish();
				}
			}

			@Override
			public void onFailure(Call<Advices> call, Throwable t) {
				if (listener != null) {
					listener.onFailure(new Exception());
					listener.onFinish();
				}
			}
		});
	}
}
