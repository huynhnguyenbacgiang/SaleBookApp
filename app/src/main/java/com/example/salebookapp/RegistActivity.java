package com.example.salebookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationHolder;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.CustomErrorReset;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidation;
import com.basgeekball.awesomevalidation.utility.custom.CustomValidationCallback;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.basgeekball.awesomevalidation.ValidationStyle.TEXT_INPUT_LAYOUT;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class RegistActivity extends AppCompatActivity {

    EditText edtFullName, edtUserName, edtPassword,edtCellPhone,edtAddress;
    TextView txtChangeLogin;
    CheckBox cbAgree;
    Button btnCreate;
    Spinner spRole;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        setup();
        setItem();
        addValidation();

        cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    btnCreate.setEnabled(false);
                } else {
                    btnCreate.setEnabled(true);
                }
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awesomeValidation.validate();
            }
        });


    }
    private void addValidation(){
        awesomeValidation = new AwesomeValidation(BASIC);
        awesomeValidation.addValidation(RegistActivity.this,R.id.edt_fullname,"(?=.*\\s)",R.string.err_value);
        awesomeValidation.addValidation(RegistActivity.this,R.id.edt_cellphone,"(?=.*\\d)",R.string.err_value);
        awesomeValidation.addValidation(RegistActivity.this,R.id.edt_address,"(?=.*\\s)",R.string.err_value);
        awesomeValidation.addValidation(RegistActivity.this, R.id.sp_role, new CustomValidation() {
            @Override
            public boolean compare(ValidationHolder validationHolder) {
                if (((Spinner) validationHolder.getView()).getSelectedItem().toString().equals("")) {
                    return false;
                } else {
                    return true;
                }
            }
        }, new CustomValidationCallback() {
            @Override
            public void execute(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(validationHolder.getErrMsg());
                textViewError.setTextColor(Color.RED);
            }
        }, new CustomErrorReset() {
            @Override
            public void reset(ValidationHolder validationHolder) {
                TextView textViewError = (TextView) ((Spinner) validationHolder.getView()).getSelectedView();
                textViewError.setError(null);
                textViewError.setTextColor(Color.BLACK);
            }
        }, R.id.sp_role);
        awesomeValidation.addValidation(RegistActivity.this, R.id.edt_cellphone, RegexTemplate.TELEPHONE, R.string
                        .err_phone);
        awesomeValidation.addValidation(RegistActivity.this,R.id.edt_username, Patterns.EMAIL_ADDRESS,R.string
                .err_email);
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        awesomeValidation.addValidation(RegistActivity.this,R.id.edt_password, regexPassword,R.string
                .err_password);
    }

    private void setItem() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegistActivity.this,
                R.array.option, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRole.setAdapter(adapter);
    }

    private void setup() {
        edtFullName = findViewById(R.id.edt_fullname);
        edtUserName = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtCellPhone = findViewById(R.id.edt_cellphone);
        edtAddress = findViewById(R.id.edt_address);
        txtChangeLogin = findViewById(R.id.txt_change_login);
        cbAgree = findViewById(R.id.cb_agree);
        btnCreate = findViewById(R.id.btn_create);
        spRole = findViewById(R.id.sp_role);
    }

    public void onClick(View v) {
        Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}