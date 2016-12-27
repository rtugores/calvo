package huitca1212.frotaalcalvo.ui;

import java.util.ArrayList;

public class CircularList <E> extends ArrayList<E> {

	@Override
	public E get(int index) {
		return super.get(index % size());
	}
}
