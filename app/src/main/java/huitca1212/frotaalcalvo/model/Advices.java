package huitca1212.frotaalcalvo.model;

import com.google.gson.annotations.SerializedName;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import huitca1212.frotaalcalvo.ui.CircularList;

@DatabaseTable
public class Advices {

	@DatabaseField(id = true, columnName = "id", canBeNull = false) private Integer id;
	@DatabaseField(dataType = DataType.SERIALIZABLE) @SerializedName("advices") private CircularList<String> advices;

	public CircularList<String> getAdvices() {
		return advices;
	}
}