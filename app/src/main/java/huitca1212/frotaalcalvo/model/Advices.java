package huitca1212.frotaalcalvo.model;

import com.google.gson.annotations.SerializedName;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import huitca1212.frotaalcalvo.ui.CircularList;

@DatabaseTable
public class Advices {
	@DatabaseField(id = true) private Integer id = 1;
	@DatabaseField(dataType = DataType.SERIALIZABLE) @SerializedName("dailyAdvices") private CircularList<String> dailyAdvices;
	@DatabaseField(dataType = DataType.SERIALIZABLE) @SerializedName("loveAdvices") private CircularList<String> loveAdvices;

	public CircularList<String> getDailyAdvices() {
		return dailyAdvices;
	}

	public CircularList<String> getLoveAdvices() {
		return loveAdvices;
	}
}