package huitca1212.frotaalcalvo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends Activity {

    protected int numerodefrase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final String[] frase = {
                getString(R.string.c0),
                getString(R.string.c1),
                getString(R.string.c2),
                getString(R.string.c3),
                getString(R.string.c4),
                getString(R.string.c5),
                getString(R.string.c6),
                getString(R.string.c7),
                getString(R.string.c8),
                getString(R.string.c9),
                getString(R.string.c10),
                getString(R.string.c11),
                getString(R.string.c12),
                getString(R.string.c13),
                getString(R.string.c14),
                getString(R.string.c15),
                getString(R.string.c16),
                getString(R.string.c17),
                getString(R.string.c18),
                getString(R.string.c19),
                getString(R.string.c20),
                getString(R.string.c21),
                getString(R.string.c22),
                getString(R.string.c23),
                getString(R.string.c24),
                getString(R.string.c25),
                getString(R.string.c26),
                getString(R.string.c27),
                getString(R.string.c28),
                getString(R.string.c29),
                getString(R.string.c30),
                getString(R.string.c31),
                getString(R.string.c32),
                getString(R.string.c33),
        };


        //Diálogo para primera ejecución
        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun) {
            VariasFunciones opcion = new VariasFunciones();
            opcion.crearDialogoInicio(this).show();
            // Save the state
            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();
        }

        //Defino si hay frase inicial en la aplicación o no
        numerodefrase = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getInt("numero", -1);
        if (numerodefrase != -1) {
            TextView textView = (TextView) findViewById(R.id.Bienvenida);
            textView.setText(frase[numerodefrase]);
        }
        //Definimos qué hacer con cada movimiento
        ImageView imageView = (ImageView) findViewById(R.id.calvo);
        imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipe() {
                MediaPlayer mpRes;
                //Cojo fecha actual
                String currentDate = DateFormat.getDateInstance().format(new Date());
                //Cojo fecha anterior
                String anterior = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                        .getString("dateant", "Defecto");
                //Las comparo
                assert anterior != null;
                if (currentDate.contains(anterior)) {
                    mpRes = MediaPlayer.create(getApplicationContext(), R.raw.repites);
                    mpRes.start();
                    Toast.makeText(MainActivity.this, R.string.vuelve_manana, Toast.LENGTH_LONG).show();
                } else {
                    mpRes = MediaPlayer.create(getApplicationContext(), R.raw.nuevoconsejo);
                    mpRes.start();
                    if (numerodefrase == -1) {
                        numerodefrase = (int) (Math.random() * (frase.length + 1)); //primera vez comienza en número aleatorio
                        TextView textView = (TextView) findViewById(R.id.Bienvenida);
                        textView.setText(frase[numerodefrase]); //si es el veinte, vuelve a empezar
                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", numerodefrase).commit();
                    }
                    if (numerodefrase == frase.length) {
                        numerodefrase = 0;
                        TextView textView = (TextView) findViewById(R.id.Bienvenida);
                        textView.setText(frase[numerodefrase]); //si es el veinte, vuelve a empezar
                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", numerodefrase).commit();
                    }
                    if (numerodefrase != -1 && numerodefrase != frase.length) {
                        numerodefrase++;
                        TextView textView = (TextView) findViewById(R.id.Bienvenida);
                        textView.setText(frase[numerodefrase]); //si es el veinte, vuelve a empezar
                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", numerodefrase).commit();
                    }
                }
            }
        });
    }
    //================================================================
    //==============CODIGO PARA ACTION BAR============================
    //================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        VariasFunciones opcion = new VariasFunciones();
        switch (item.getItemId()) {
            case R.id.menu_share:
                opcion.compartir(this);
                return true;
            case R.id.menu_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Información");
                builder.setMessage("Aplicación desarrollada por RJ Apps. " +
                        "Para cualquier sugerencia, no dude en contactar.");
                builder.setPositiveButton("Contactar", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822")
                                .putExtra(Intent.EXTRA_EMAIL, new String[]{"huitca1212@gmail.com"});
                        startActivity(Intent.createChooser(i, "Enviar mediante"));
                    }
                });
                builder.setNegativeButton("Volver", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
