package com.co.colector.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.co.colector.ColectorApplication;
import com.co.colector.R;
import com.co.colector.helpers.PreferencesHelper;
import com.co.colector.model.Enterprise;
import com.co.colector.network.NetworkCalls;
import com.co.colector.utils.ColectorConstants;
import com.co.colector.utils.OperationWsCall;

import java.util.ArrayList;

public class MainActivity extends Activity{

    private NetworkCalls networkCalls;
    private ProgressDialog progressDialog;
    private ArrayList<Enterprise> enterpriseArrayList;
    private AlertDialog.Builder alertDialog;
    private LayoutInflater inflater;
    private View convertView;
    private String names[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        networkCalls = new NetworkCalls(this);
        enterpriseArrayList = new ArrayList<Enterprise>();
        ((EditText) findViewById(R.id.editTextEmail)).setText("jcastillo@perast.cl");
        ((EditText) findViewById(R.id.editTextPassword)).setText("123456");
        init();
    }

    private Boolean getPassOfView(){
        return ((EditText) findViewById(R.id.editTextEmail)).getText().toString().length() != 0 &&
                ((EditText) findViewById(R.id.editTextPassword)).getText().toString().length() != 0;
    }

    public void callToErrorLoginDisplay(){
        startActivity(new Intent(this,ErrorLoginActivity.class));
        finish();
    }

    public void requestEmpresas(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                networkCalls.makeWsCall(OperationWsCall.ENTERPRISE_CALL,null);
            }
        });
    }

    public void hideDialog(){
        progressDialog.hide();
    }

    public void showEnterpriseDialog(){
        names = new String[enterpriseArrayList.size()];
        fillNames();
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        convertView = (View) inflater.inflate(R.layout.list, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Select enterprise");
        ((ListView) convertView.findViewById(R.id.lv)).
            setAdapter(new ArrayAdapter<String>(this, R.layout.mytextview, names));
        alertDialog.show();

        ((ListView) convertView.findViewById(R.id.lv)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PreferencesHelper.insertIdSistema(enterpriseArrayList.get(position).getId());
                PreferencesHelper.insertBdIdSistema(enterpriseArrayList.get(position).getDb_system());

                //TODO - remove after finish test
                Log.i("sistemaId", "" + PreferencesHelper.getIdSistema());
                Log.i("dbSistema", "" + PreferencesHelper.getDbIdSistema());
                Log.i("userId", ""+ PreferencesHelper. getUserId());
                //

                startActivity(new Intent(MainActivity.this, ListFormsActivity.class));
                finish();
            }
        });
    }

    private void fillNames(){
        int i = 0;
        for (Enterprise enterprise: enterpriseArrayList){
            names[i] = " - "+enterprise.getName();
            i++;
        }
    }

    public void addEnterprise(Enterprise e){
        enterpriseArrayList.add(e);
    }

    private void init(){

        ((Button) findViewById(R.id.buttonSignIn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPassOfView()){

                    progressDialog = ProgressDialog.show(MainActivity.this,
                            getString(R.string.downloading_title),
                            getString(R.string.please_take_a_moment),
                            true);

                    ColectorConstants.username = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
                    ColectorConstants.password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            networkCalls.makeWsCall(OperationWsCall.LOGIN,null);
                        }
                    });
                }
                else {
                    progressDialog.hide();
                    Toast.makeText(ColectorApplication.getInstance(),getString(R.string.error_message),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}