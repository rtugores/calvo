package huitca1212.frotaalcalvo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;

public class Utils {

	public static void shareApp(Context ctx) {
		final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("text/plain");
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		} else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, ctx.getString(R.string.menu_share_subject));
		intent.putExtra(Intent.EXTRA_TEXT, ctx.getString(R.string.menu_share_text));
		ctx.startActivity(Intent.createChooser(intent, ctx.getString(R.string.menu_share_chooser)));
	}

	public static void showInitDialog(Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(R.string.dialog_welcome);
		builder.setMessage(R.string.dialog_welcome_text);
		builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public static void showInfoApp(final Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(R.string.dialog_info);
		builder.setMessage(R.string.dialog_info_text);
		builder.setPositiveButton(R.string.dialog_info_contact, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822").putExtra(Intent.EXTRA_EMAIL, new String[]{ctx.getString(R.string.email)});
				ctx.startActivity(Intent.createChooser(i, ctx.getString(R.string.dialog_info_contact_text)));
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}
