package com.example.mobilebanking.Raports;

import android.content.Context;
import android.os.Bundle;

import com.example.mobilebanking.Chart.ChartActivity;
import com.example.mobilebanking.SharedPreferencesUser;
import com.example.mobilebanking.database.DatabaseManager;
import com.example.mobilebanking.database.models.Transaction;
import com.example.mobilebanking.database.models.User;
import com.example.mobilebanking.database.service.TranzactionService;
import com.example.mobilebanking.util.AdapterTranzactie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proiectmobilebanking.R;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaportAmount extends AppCompatActivity {
EditText editRaportAmount;
Button btnViewRaportAmount;
long idUser;
int amount;
SharedPreferencesUser preferences;
ListView lvRaportAmount;
Button btnRaportToTxt;
List<Transaction> tranzactions=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport_amount);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editRaportAmount=findViewById(R.id.etRaportAmount);
        btnViewRaportAmount=findViewById(R.id.btnViewRaportAmount);
        lvRaportAmount=findViewById(R.id.lvRaportAmount);
        preferences=new SharedPreferencesUser(getApplicationContext());
        idUser=preferences.getUser();
        btnRaportToTxt=findViewById(R.id.btnRaportToTxt);
        btnViewRaportAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TranzactionService.GetAllTransactions(getApplicationContext()) {
                    @Override
                    protected void onPostExecute(
                            List<Transaction> results) {
                        if (results != null) {
                            for(Transaction tranzaction:results){
                                if(tranzaction.getIdUser()==idUser&&tranzaction.getAmount()>Integer.parseInt(editRaportAmount.getText().toString())){
                                    tranzactions.add(tranzaction);
                                    AdapterTranzactie adapter=new AdapterTranzactie(getApplicationContext(),R.layout.tranzaction_row,tranzactions,getLayoutInflater());
                                    lvRaportAmount.setAdapter(adapter);
                                }

                            }
                        }
                    }
                }.execute();
            }
        });
        btnRaportToTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRaportToTxt(tranzactions);
                Toast.makeText(getApplicationContext(),getString(R.string.raport_saved_successfully),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveRaportToTxt(List<Transaction> list){
        try {
            FileOutputStream fileOutputStream=openFileOutput("raportAmount.txt", Context.MODE_PRIVATE);
            DataOutputStream out=new DataOutputStream(fileOutputStream);
            for(Transaction tranzaction:list){
                out.write("Transaction: ".getBytes());
                out.write(tranzaction.toString().getBytes());
                out.write("\n".getBytes());
            }
            out.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
