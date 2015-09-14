package huitca1212.frotaalcalvo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class VariasFunciones extends Activity {

    public void compartir(Context eso) {
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.putExtra(Intent.EXTRA_SUBJECT, "¡Descarga \"Frota al Calvo\"!");
        intent.putExtra(Intent.EXTRA_TEXT, "El calvo de la aplicación \"Frota al calvo\" sí que sabe dar buenos consejos. Está disponible en Google Play: https://play.google.com/store/apps/details?id=huitca1212.frotaalcalvo");
        eso.startActivity(Intent.createChooser(intent, "Compartir mediante"));
    }

    public Dialog crearDialogoInicio(Context eso) {
        AlertDialog.Builder builder = new AlertDialog.Builder(eso);
        builder.setTitle("BIENVENIDO");
        builder.setMessage("¡Frótale la calva al calvo y te dará un consejo! Recuerda " +
                "que sólo da un consejo por día.");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

}
