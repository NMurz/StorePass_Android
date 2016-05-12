package com.password.kg.passwordbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.password.kg.model.User;
import com.password.kg.model.UserDao;
import com.password.kg.passwordbook.helper.DaoManager;
import com.password.kg.passwordbook.model.SignUpUser;
import com.password.kg.passwordbook.rest.ApiUrls;
import com.password.kg.passwordbook.rest.ServiceGenerator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText email;
    EditText password;
    EditText confirm_password;
    EditText pincode;
    Button signUp;
    TextView logIn;
    Spinner languageSpinner;
    CoordinatorLayout coordinatorLayout;
    String email_s;
    String password_s;
    String confirmPassword_s;
    String pinCode_s;
    int languageId = 0;
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_=+-/]).{6,20})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initWidgets();



    }

    public void initWidgets() {
        email = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        confirm_password = (EditText) findViewById(R.id.register_confirmPassword);
        pincode = (EditText) findViewById(R.id.pincode_edittext);

        signUp = (Button) findViewById(R.id.btn_signup);
        logIn = (TextView) findViewById(R.id.link_login);
        languageSpinner = (Spinner) findViewById(R.id.spinnerLanguage);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.register_coordinatorLayout);

        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        languageSpinner.setAdapter(adapter);

    }

    public boolean checkDetails(){

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        password_s = password.getText().toString();
        confirmPassword_s = confirm_password.getText().toString();
        email_s = email.getText().toString();

        if(!email_s.contains("@")) {
            email.setError(getString(R.string.email_invalid));
            return false;
        }

        if(languageId == 0) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.select_language, Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }

        if(password_s.isEmpty() || confirmPassword_s.isEmpty()) {
            password.setError(getString(R.string.input_password));
            return false;
        }
        else if(password_s.equals(confirmPassword_s)){
            Matcher matcher = pattern.matcher(password_s);
            if(matcher.matches()){
                return true;
            } else {
                password.setError(getString(R.string.password_check));
                return false;
            }
        } else {
            password.setError(getString(R.string.password_not_match));
            return false;
        }

    }

    public void signUp(){

        pinCode_s = pincode.getText().toString();

        if(checkDetails()){
            SignUpUser user = new SignUpUser(email_s, password_s, confirmPassword_s, languageId);
            ApiUrls.Authorization authorization = ServiceGenerator.createService(ApiUrls.Authorization.class);
            Call<SignUpUser> call = authorization.signUp(user);
            call.enqueue(new Callback<SignUpUser>() {
                @Override
                public void onResponse(Response<SignUpUser> response, Retrofit retrofit) {
                    Snackbar snackbar;
                    if(response.isSuccess()){
                        snackbar = Snackbar.make(coordinatorLayout, R.string.register_success, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        snackbar = Snackbar.make(coordinatorLayout, response.message(), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.CustomAlertDialogStyle);
                    builder.setTitle(R.string.connection)
                            .setMessage(R.string.networkUnreachable)
                            .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    signUp();
                                }
                            })
                            .show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                signUp();
                break;
            case R.id.link_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        languageId = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
