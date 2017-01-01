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
	private static String adviceType;
	private static RuntimeExceptionDao<Advices, Integer> advicesDao;

	public static void getAdvices(final String type, final AllBusinessListener<CircularList<String>> listener) {
		adviceType = type;
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
				getAdvicesFromBackend(listener);
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
					return getAdvicesFromObject(advices.get(0));
				} else {
					return new CircularList<>();
				}
			}
		}.execute();
	}

	private static void getAdvicesFromBackend(final AllBusinessListener<CircularList<String>> listener) {
		AdvicesService advicesService = ApiUtils.getAdvicesService();
		Call<Advices> advicesCall;
		advicesCall = advicesService.getAdvices();
		advicesCall.enqueue(new Callback<Advices>() {
			@Override
			public void onResponse(Call<Advices> call, final Response<Advices> response) {
				AllBusinessListener<Object> databaseListener = new AllBusinessListener<Object>() {
					@Override
					public void onDatabaseSuccess(Object object) {
						if (listener != null) {
							if (response.isSuccessful()) {
								listener.onServerSuccess(getAdvicesFromObject(response.body()));
							} else {
								listener.onFailure(new Exception());
							}
							listener.onFinish();
						}
					}
				};
				new DefaultAsyncTask<String, Object>(databaseListener) {
					@Override
					protected Object dbOperation(String... params) throws SQLException {
						advicesDao.createOrUpdate(response.body());
						return null;
					}
				}.execute();
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

	private static CircularList<String> getAdvicesFromObject(Advices object) {
		return adviceType.equals(DAILY_ADVICES) ? object.getDailyAdvices() : object.getLoveAdvices();
	}
}