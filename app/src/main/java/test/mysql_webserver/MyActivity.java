package test.mysql_webserver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MyActivity extends Activity {
    private EditText dni;
    private EditText nombre;
    private EditText telefono;
    private EditText email;
    private Button insertar;
    private Button mostrar;
    private ImageButton mas;
    private ImageButton menos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_my);

        dni=(EditText)findViewById(R.id.dni);
        nombre=(EditText)findViewById(R.id.nombre);
        telefono=(EditText)findViewById(R.id.telefono);
        email=(EditText)findViewById(R.id.email);
        //Insertamos los datos de la persona.
        insertar=(Button)findViewById(R.id.insertar);
        insertar.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!dni.getText().toString().trim().equalsIgnoreCase("")||
                        !nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !telefono.getText().toString().trim().equalsIgnoreCase("")||
                        !email.getText().toString().trim().equalsIgnoreCase(""))

                    new Insertar(MyActivity.this).execute();

                else
                    Toast.makeText(MyActivity.this, "Hay información por rellenar", Toast.LENGTH_LONG).show();
            }

        });
        //Mostramos los datos de la persona por pantalla.
        mostrar=(Button)findViewById(R.id.mostrar);
        //Se mueve por nuestro ArrayList mostrando el objeto posterior.
        mas=(ImageButton)findViewById(R.id.mas);
        //Se mueve por nuestro ArrayList mostrando el objeto anterior
        menos=(ImageButton)findViewById(R.id.menos);
    }

    //Inserta los datos de las Personas en el servidor.
    private boolean insertar(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://192.168.0.11/picarcodigo/insert.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("dni",dni.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre",nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono",telefono.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("email",email.getText().toString().trim()));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            return true;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    //AsyncTask para insertar Personas
    class Insertar extends AsyncTask<String,String,String>{

        private Activity context;

        Insertar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(insertar())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Persona insertada con éxito", Toast.LENGTH_LONG).show();
                        nombre.setText("");
                        dni.setText("");
                        telefono.setText("");
                        email.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Persona no insertada con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}

//package test.mysql_webserver;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//
//public class MyActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.my, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
