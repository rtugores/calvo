package huitca1212.frotaalcalvo.business;


public abstract class AllBusinessListener <T> {
	public void onDatabaseSuccess(T object) {
	}

	public void onServerSuccess(T object) {
	}

	public void onFinish() {
	}

	public void onFailure(Exception e) {
	}
}
