package huitca1212.frotaalcalvo.business;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

import huitca1212.frotaalcalvo.BaldApplication;
import huitca1212.frotaalcalvo.model.Advices;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "dbAdvices";

	private static DatabaseHelper databaseHelper;
	private static RuntimeExceptionDao<Advices, Integer> advicesDao;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Advices.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int previousVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Advices.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + previousVersion + " to new " + newVersion, e);
		}
	}

	public RuntimeExceptionDao<Advices, Integer> getAdvicesDao() throws SQLException {
		if (advicesDao == null) {
			advicesDao = getRuntimeExceptionDao(Advices.class);
		}
		return advicesDao;
	}

	public static DatabaseHelper getDbHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(BaldApplication.getInstance(), DatabaseHelper.class);
		}
		return databaseHelper;
	}
}