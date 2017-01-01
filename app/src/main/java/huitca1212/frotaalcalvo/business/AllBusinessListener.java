package huitca1212.frotaalcalvo.business;


public abstract class AllBusinessListener <T> {
	private AllBusinessListener<T> listener;

	public AllBusinessListener() {
	}

	public AllBusinessListener(AllBusinessListener<T> listener) {
		this.listener = listener;
	}

	public void onDatabaseSuccess(T object) {
		if (listener != null) {
			listener.onDatabaseSuccess(object);
		}
	}

	public void onServerSuccess(T object) {
		if (listener != null) {
			listener.onServerSuccess(object);
		}
	}

	public void onFinish() {
		if (listener != null) {
			listener.onFinish();
		}
	}

	public void onFailure(Exception e) {
		if (listener != null) {
			listener.onFailure(e);
		}
	}
}
