package huitca1212.frotaalcalvo.ui;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import huitca1212.frotaalcalvo.BaldApplication;

public abstract class OnSwipeTouchListener implements OnTouchListener {

	private final GestureDetector gestureDetector;

	public OnSwipeTouchListener() {
		gestureDetector = new GestureDetector(BaldApplication.getInstance(), new GestureListener());
	}

	public boolean onTouch(final View view, final MotionEvent motionEvent) {
		return gestureDetector.onTouchEvent(motionEvent);
	}

	private final class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			onSwipe();
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			onTap();
			return false;
		}
	}

	public abstract void onSwipe();

	public abstract void onTap();
}