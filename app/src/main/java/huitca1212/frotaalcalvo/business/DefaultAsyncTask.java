package huitca1212.frotaalcalvo.business;

import android.os.AsyncTask;

import java.sql.SQLException;

public abstract class DefaultAsyncTask <String, T> extends AsyncTask<String, Void, T> {

	private Exception exception;
	private AllBusinessListener listener;

	public DefaultAsyncTask() {
	}

	public DefaultAsyncTask(AllBusinessListener<T> listener) {
		this.listener = listener;
	}

	@Override
	protected T doInBackground(String... params) {
		try {
			T result = dbOperation(params);
			return result;
		} catch (SQLException e) {
			exception = e;
			return null;
		}
	}

	protected abstract T dbOperation(String... params) throws SQLException;

	@Override
	protected void onPostExecute(T result) {
		if (listener != null) {
			if (exception != null) {
				listener.onFailure(exception);
			} else {
				listener.onDatabaseSuccess(result);
			}
		}
		super.onPostExecute(result);
	}
}