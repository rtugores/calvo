package huitca1212.frotaalcalvo;

import java.text.DateFormat;
import java.util.Date;

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

import com.google.analytics.tracking.android.EasyTracker;


public class MainActivity extends Activity{
		
	protected int numerodefrase;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
        final String[] frase = {"Da un paseo y despéjate un poco",
        		"Llama a un familiar y pregúntale qué tal el día",
        		"Cómprate unas regalices",
        		"Mira si hacen algo por la tele",
        		"Sal a hacer un poco de ejercicio",
        		"Intenta dormir más esta noche",
        		"Relájate dándote un baño",
        		"Mira por la ventana y sonríe",
        		"Ve a la bolera más cercana y juega una partida",
        		"Afronta alguno de tus miedos",
        		"Comparte una anécdota tuya con alguien",
        		"Conoce a alguien interesante",
        		"Limpia y ordena tu habitación",
        		"Charla un poco con alguno de tus amigos",
        		"Mira ropa en algún centro comercial",
        		"Lee un libro en el parque",
        		"Búscate a alguien para ir al cine",
        		"Lee el periódico",
        		"Duerme una siesta",
        		"Visita algún museo cercano",
        		"Acércate hasta el bar de abajo de casa", //frase 20 (empezando en frase 0)
        		"Visita alguna biblioteca pública",
        		"Escribe un artículo o un relato",
        		"Ve a nadar",
        		"Aprende sobre los árboles y flores nativas de tu zona",
        		"Escucha la radio",
        		"Planea una salida al monte",
        		"Aprende a hacer mermeladas",
        		"Comienza un diario",
        		"Toma fotografías",
        		"Asiste a una obra de teatro",
        		"Haz un balance de tus gastos superfluos",
        		"Planifica tu dieta: organiza un menú equilibrado",
        		"Estudia cómo reducir el gasto energético en tu casa"}; //frase 33 (empezando en frase 0)
        		
        
        //Diálogo para primera ejecución
		boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
		   if (firstrun){
			   VariasFunciones opcion= new VariasFunciones();
			   opcion.crearDialogoInicio(this).show();
			   // Save the state
			   getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            		.edit()
            		.putBoolean("firstrun", false)
            		.commit();
		    }

		//Defino si hay frase inicial en la aplicación o no
		numerodefrase = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
    			.getInt("numero",-1);
    	if(numerodefrase!=-1){
    		TextView textView =(TextView)findViewById(R.id.Bienvenida);
    		textView.setText(frase[numerodefrase]);
    		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    	}
    	//Definimos qu� hacer con cada movimiento
		ImageView imageView = (ImageView)findViewById(R.id.calvo);
		imageView.setOnTouchListener(new OnSwipeTouchListener(this) {
		    public void onSwipe() {
		    	//Cojo fecha actual
		    	String currentDate = DateFormat.getDateInstance().format(new Date());
		    	//Cojo fecha anterior
		    	String anterior = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
		    			.getString("dateant","Defecto");
		    	//Las comparo
		    	MediaPlayer mpRes;
		    	if(currentDate.contains(anterior)){
		    		mpRes=MediaPlayer.create(getApplicationContext(),R.raw.repites);
	    			mpRes.start();
		    		Toast.makeText(MainActivity.this, "Vuelve mañana y te daré un nuevo consejo", Toast.LENGTH_LONG).show();
		    	}
		    	else{	
		    		mpRes=MediaPlayer.create(getApplicationContext(),R.raw.nuevoconsejo);
	    			mpRes.start();
		    		if(numerodefrase==-1) {
		    			numerodefrase= (int)(Math.random()*34); //primera vez comienza en n�mero del 0 al 33
		    			TextView textView =(TextView)findViewById(R.id.Bienvenida);
		    			textView.setText(frase[numerodefrase]); //si es el veinte, vuelve a empezar
		    			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		    			getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
		    			getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", numerodefrase).commit();
		    		}
		    		if(numerodefrase==33){
		    			numerodefrase=0;
		    			TextView textView =(TextView)findViewById(R.id.Bienvenida);
			    		textView.setText(frase[numerodefrase]); //si es el veinte, vuelve a empezar
			        	textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			    		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("dateant", currentDate).commit();
			    		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putInt("numero", numerodefrase).commit();
		    		}
		    		if(numerodefrase!= -1 && numerodefrase!= 33){
		    			numerodefrase++;
		    			TextView textView =(TextView)findViewById(R.id.Bienvenida);
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
			VariasFunciones opcion= new VariasFunciones();
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
		    		    		.putExtra(Intent.EXTRA_EMAIL  , new String[]{"huitca1212@gmail.com"});
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
		
		@Override
		  public void onStart() {
		    super.onStart();
		    EasyTracker.getInstance().activityStart(this);  // Add this method.
		  }

		 @Override
		  public void onStop() {
		    super.onStop();
		    EasyTracker.getInstance().activityStop(this);  // Add this method.
		  }
}