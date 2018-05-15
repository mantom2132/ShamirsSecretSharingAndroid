package com.example.mantom2132.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    public boolean secretSet = false;
    public boolean thresholdSet = false;
    public boolean noOfSharesSet = false;
    private BigInteger prime;
    private SecretShare[] shares;
    Vector inputs = new Vector();
    private int noOfShares;
    private int noOfInputs;
    private int threshold;
    private int noOfSharesToInput;
    final SecureRandom random = new SecureRandom();
    final int CERTAINTY = 256;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText inputSec = findViewById(R.id.inputSecret);
        final EditText inputThresh = findViewById(R.id.inputThreshold);
        final EditText inputNoOfShares = findViewById(R.id.inputNoOfShares);
        final EditText inputSharesToInput = findViewById(R.id.sharesToInput);
        final EditText inputWhichShare = findViewById(R.id.inputWhichShare);
        final EditText generatedSec = findViewById(R.id.SecretGenerated);

        Button generateShares = findViewById(R.id.generateShares);
        generateShares.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                boolean secretSet;
                if (inputSec.getText().toString().isEmpty()){
                    /*
                    InvalidSecret error1 = new InvalidSecret();
                    error1.setVisible(true);
                    */
                    secretSet = false;
                }
                else {
                    secretSet = true;
                    String textFromInputSecret = inputSec.getText().toString();
                    int secret = Integer.parseInt(textFromInputSecret);
                }
                if (inputThresh.getText().equals("")) {
                    thresholdSet = false;
                }
                else {
                    thresholdSet = true;
                    String textFromInputThreshold = inputThresh.getText().toString();
                    int threshold = Integer.parseInt(textFromInputThreshold);
                }
                if (inputNoOfShares.getText().equals("")){
                    noOfSharesSet = false;

                }
                else{
                    noOfSharesSet = true;
                    String textFromInputNoOfShares = inputNoOfShares.getText().toString();
                    int noOfShares = Integer.parseInt(textFromInputNoOfShares);
                }

                if (secretSet == true && thresholdSet == true && noOfSharesSet == true){
                    String textFromInput1 = inputSec.getText().toString();
                    BigInteger sec = BigInteger.valueOf(Integer.parseInt(textFromInput1));
                    prime = new BigInteger(sec.bitLength() + 1, CERTAINTY, random);
                    final ListView list = findViewById(R.id.myList);
                    ArrayList<String> arrayList = new ArrayList<String>();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,
                            arrayList);
                    list.setAdapter(adapter);
                    shares = ShamirSecretSharing.split(sec, Integer.parseInt(inputThresh.getText().toString()),Integer.parseInt(inputNoOfShares.getText().toString()), prime, random);
                    arrayList.add("My app");
                    adapter.notifyDataSetChanged();
                    System.out.println(noOfShares);
                    for (int i = 1; i <= Integer.parseInt(inputNoOfShares.getText().toString()); i++) {
                        arrayList.add("Share " + shares[i - 1]);
                        System.out.println(arrayList.get(i));
                        adapter.notifyDataSetChanged();
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        Button genSec = findViewById(R.id.generateSecret);
        genSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean sharesToInputSet;
                if (inputSharesToInput.getText().equals("")){
                    sharesToInputSet = false;
                }
                else{
                    if((Integer.parseInt(inputSharesToInput.getText().toString()) > Integer.parseInt(inputNoOfShares.getText().toString())))
                    sharesToInputSet = true;
                     noOfSharesToInput= Integer.parseInt(inputSharesToInput.getText().toString());

                }
                boolean whichShareSet;
                if(inputWhichShare.getText().equals("")){
                    whichShareSet = false;
                }
                else {
                    whichShareSet = true;
                    inputs.addElement(Integer.parseInt(inputWhichShare.getText().toString()));
                    inputWhichShare.setText("");
                    System.out.println(inputs.toString());
                    System.out.println(noOfSharesToInput);
                }
                System.out.println("past the if else loop");
                System.out.println(inputs.size());
                if (inputs.size()== Integer.parseInt(inputSharesToInput.getText().toString())){
                    System.out.println("in the second if loop");
                    SecretShare[] sharesInputed = new SecretShare[Integer.parseInt(inputSharesToInput.getText().toString())];
                    for (int i =0;i<=noOfSharesToInput-1; i++) {
                        sharesInputed[i] = shares[(int) inputs.get(i) - 1];
                    }
                    BigInteger result = ShamirSecretSharing.combine(sharesInputed, prime);
                    System.out.println(result.toString());
                    generatedSec.setText(result.toString());
                }
            }
        });
    }
}