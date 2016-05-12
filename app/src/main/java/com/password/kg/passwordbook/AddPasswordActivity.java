package com.password.kg.passwordbook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.password.kg.model.Categories;
import com.password.kg.model.CategoriesDao;
import com.password.kg.model.Passwords;
import com.password.kg.model.PasswordsDao;
import com.password.kg.passwordbook.adapter.CategorySpinnerAdapter;
import com.password.kg.passwordbook.adapter.CategoryViewAdapter;
import com.password.kg.passwordbook.helper.AesCryptHelper;
import com.password.kg.passwordbook.helper.DaoManager;
import com.password.kg.passwordbook.helper.RandomPasswordGenerator;

import java.util.List;

public class AddPasswordActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText title;
    EditText website;
    EditText password;
    EditText description;
    EditText account;
    ImageButton changePasswordView;
    ImageButton generatePass;
    Button addPassword;
    Spinner categorySpinner;
    PasswordsDao passwordDao;
    CategoriesDao categoryDao;
    CoordinatorLayout coordinatorLayout;
    List<Categories> categoriesList;
    boolean isVisible = false;
    Passwords passwordObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLayout();
        initDaos();
        initCategorySpinner();
        passwordObject = new Passwords();

    }

    public void initLayout(){
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.addPassword_coordinatorLayout);
        title = (EditText) findViewById(R.id.row_title);
        website = (EditText) findViewById(R.id.row_website);
        password = (EditText) findViewById(R.id.row_password);
        account = (EditText) findViewById(R.id.row_account);
        description = (EditText) findViewById(R.id.row_description);
        changePasswordView = (ImageButton) findViewById(R.id.password_show);
        generatePass = (ImageButton) findViewById(R.id.generate_pass_button);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        addPassword = (Button) findViewById(R.id.addPassword);
        addPassword.setOnClickListener(this);
        changePasswordView.setOnClickListener(this);
        generatePass.setOnClickListener(this);
    }

    public void initDaos(){
        passwordDao = DaoManager.getPasswordsDao();
        categoryDao = DaoManager.getCategoriesDao();
        categoriesList = categoryDao.loadAll();
    }

    public void initCategorySpinner(){
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(this, R.layout.spinner_item, categoriesList);
        categorySpinner.setOnItemSelectedListener(this);
        categorySpinner.setAdapter(categorySpinnerAdapter);
    }

    public boolean checkFieldsAndUpdatePassword(){

        if(title.getText().toString().isEmpty()) {
            title.setError(getString(R.string.input_title));
            return false;
        } else {
            passwordObject.setTitle(title.getText().toString());
        }
        if(website.getText().toString().isEmpty()) {
            website.setError(getString(R.string.input_webaddress));
            return false;
        } else {
            passwordObject.setWebsite(website.getText().toString());
        }
        if(account.getText().toString().isEmpty()) {
            account.setError(getString(R.string.input_accountname));
            return false;
        } else {
            passwordObject.setAccount(account.getText().toString());
        }
        if(password.getText().toString().isEmpty()) {
            password.setError(getString(R.string.input_password));
            return false;
        } else {
            String passwordStr = password.getText().toString();
            //byte[] encryptedPasswordInBytes = AesCryptHelper.aesEncrypt(passwordStr);
            //String encryptedPassword = new String(encryptedPasswordInBytes);
            passwordObject.setPassword(AesCryptHelper.aesEncrypt(passwordStr));
        }
        if(!description.getText().toString().isEmpty()) passwordObject.setDescription(description.getText().toString());

        return true;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.password_show:
                if(!isVisible) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    changePasswordView.setImageResource(R.drawable.ic_eye_off);
                    isVisible = true;
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    changePasswordView.setImageResource(R.drawable.ic_eye);
                    isVisible = false;
                }
                break;
            case R.id.addPassword:
                if(checkFieldsAndUpdatePassword()){
                    passwordDao.insert(passwordObject);
                    finish();
                    Toast.makeText(AddPasswordActivity.this, R.string.entry_created, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.generate_pass_button:
                char[] pswd = RandomPasswordGenerator.generatePswd();
                String generatedPassword = new String(pswd);
                password.setText(generatedPassword);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        passwordObject.setCategoryId(id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
