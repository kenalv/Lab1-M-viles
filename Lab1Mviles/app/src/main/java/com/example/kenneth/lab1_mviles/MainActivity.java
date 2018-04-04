package com.example.kenneth.lab1_mviles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<Usuario> model=new ArrayList<>();
    UsuarioAdapter adapter=null;
    Uri imgUri = null;
    ArrayList<String> matchesText;


    private static final int REQUEST_CODE_SPEECH = 1234;

    class UsuarioAdapter extends ArrayAdapter<Usuario> {

        UsuarioAdapter() {
            super(MainActivity.this, R.layout.row_user_info, model);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View row=convertView;
            UsuarioHolder holder=null;


            if(row==null){
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.row_user_info, parent,false);
                holder=new UsuarioHolder(row);
                row.setTag(holder);
            }
            else{
                holder=(UsuarioHolder)row.getTag();
            }
            holder.populateFrom(model.get(position));
            //Hay que modificar el model
            return (row);
        }
    }

    static class UsuarioHolder{
        private TextView name=null;
        private TextView profile=null;
        private  TextView gender = null;
        private ImageView imgUser = null;
        UsuarioHolder(View row){
            name= row.findViewById(R.id.title);
            profile= row.findViewById(R.id.profile);
            gender = row.findViewById(R.id.gender);
            imgUser = row.findViewById(R.id.icon);
        }
        void populateFrom(Usuario user){
            imgUser.setImageURI(user.getImg());
            name.setText(user.getName());
            profile.setText(user.getProfile());
            gender.setText(user.getGender());
        }
    }

    private  static  final  int  IMAGE_CAPTURE = 1;
    public  void  startRecording(View  view)
    {
        Intent intent = new  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent , IMAGE_CAPTURE);
    }
    public  void  startSpeaking(View  view)
    {
        if(isConnected()){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, REQUEST_CODE_SPEECH);
        } else {
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!= null && net.isAvailable() && net.isConnected()){
            return true;
        }   else {
            return false;
        }
    }



    private  boolean  hasCamera () {
        return (getPackageManager ().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY));
    }
//result para la camara
    protected  void  onActivityResult(int  requestCode ,
                                      int  resultCode , Intent  data) {

        if (requestCode  ==  IMAGE_CAPTURE) {

            if (resultCode  ==  RESULT_OK) {

                imgUri = data.getData();

            }
        }

         if (requestCode == REQUEST_CODE_SPEECH ){


             matchesText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //Get data of data

             EditText profiletext = findViewById(R.id.profile);

             profiletext.setText(matchesText.get(0));

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button photo = findViewById(R.id.photo);
        if (! hasCamera ())
            photo.setEnabled(false);

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usuario user= new Usuario();

                EditText name= findViewById(R.id.name);
                EditText profile= findViewById(R.id.profile);


                user.setImg(imgUri);
                user.setName(name.getText().toString()); //guarda el nombre
                user.setProfile(profile.getText().toString());//guarda el perfil
                //user.setImg(imageUser);


                RadioGroup types= findViewById(R.id.genderGroup);
                switch(types.getCheckedRadioButtonId()){
                    case R.id.gender_male:
                        user.setGender("Male");
                        break;
                    case   R.id.gender_female:
                        user.setGender("Female");
                        break;
                }
                adapter.add(user);

            }
        });

        adapter= new UsuarioAdapter();
        ListView listView = findViewById(R.id.users_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }
}
