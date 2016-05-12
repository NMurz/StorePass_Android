package com.password.kg.passwordbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
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
import com.password.kg.passwordbook.helper.DaoManager;
import com.password.kg.passwordbook.helper.RandomPasswordGenerator;

import java.util.List;

public class EditPasswordActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    String passwordId;
    EditText title;
    EditText website;
    EditText account;
    EditText username;
    EditText password;
    EditText description;
    ImageButton passwordVisibleBtn;
    ImageButton passwordGenerateBtn;
    Button passwordSaveBtn;
    CoordinatorLayout coordinatorLayout;
    Spinner categorySpinner;
    PasswordsDao passwordsDao;
    CategoriesDao categoriesDao;
    Passwords passwordObject;
    List<Categories> categoriesList;
    boolean isPasswordVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        passwordId = intent.getStringExtra("PasswordId");

        initDaos();
        initWidgets();
        initCategorySpinner();









    }

    public void initDaos() {
        passwordsDao = DaoManager.getPasswordsDao();
        categoriesDao = DaoManager.getCategoriesDao();
        passwordObject = passwordsDao.loadByRowId(Integer.parseInt(passwordId));
        categoriesList = categoriesDao.loadAll();
    }

    public void initWidgets() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.passwordEdit_coordinatorLayout);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        title = (EditText) findViewById(R.id.row_title);
        website = (EditText) findViewById(R.id.row_website);
        account = (EditText) findViewById(R.id.row_account);
        password = (EditText) findViewById(R.id.row_password);
        description = (EditText) findViewById(R.id.row_description);
        passwordVisibleBtn = (ImageButton) findViewById(R.id.password_show);
        passwordGenerateBtn = (ImageButton) findViewById(R.id.generate_pass_button);
        passwordSaveBtn = (Button) findViewById(R.id.passwordSaveBtn);

        passwordVisibleBtn.setOnClickListener(this);
        passwordGenerateBtn.setOnClickListener(this);
        passwordSaveBtn.setOnClickListener(this);

        title.setText(passwordObject.getTitle());
        website.setText(passwordObject.getWebsite());
        account.setText(passwordObject.getAccount());
        password.setText(passwordObject.getPassword());
        description.setText(passwordObject.getDescription());
    }

    public void initCategorySpinner(){
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(this, R.layout.spinner_item, categoriesList);
        categorySpinner.setOnItemSelectedListener(this);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        for (Categories category : categoriesList) {
            if(passwordObject.getId().equals(category.getId())) {
                categorySpinner.setSelection(categorySpinnerAdapter.getPosition(category));
            }
        }

    }

    public boolean checkFieldsAndUpdatePassword(){

        if(title.getText().toString().isEmpty()) {
            title.setError("Input title");
            return false;
        } else {
            passwordObject.setTitle(title.getText().toString());
        }
        if(website.getText().toString().isEmpty()) {
            website.setError("Input website address");
            return false;
        } else {
            passwordObject.setWebsite(website.getText().toString());
        }
        if(account.getText().toString().isEmpty()) {
            account.setError("Input account name");
            return false;
        } else {
            passwordObject.setAccount(account.getText().toString());
        }

        if(password.getText().toString().isEmpty()) {
            password.setError("Input password");
            return false;
        } else {
            passwordObject.setPassword(password.getText().toString());
        }
        if(!description.getText().toString().isEmpty()) passwordObject.setDescription(description.getText().toString());

        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.password_show:
                if(!isPasswordVisible) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordVisibleBtn.setImageResource(R.drawable.ic_eye_off);
                    isPasswordVisible = true;
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordVisibleBtn.setImageResource(R.drawable.ic_eye);
                    isPasswordVisible = false;
                }
                break;
            case R.id.passwordSaveBtn:
                if(checkFieldsAndUpdatePassword()){
                    passwordsDao.update(passwordObject);
                    Toast.makeText(EditPasswordActivity.this, "Saving successful", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
            case R.id.generate_pass_button:
                AlertDialog dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle)
                        .setTitle("Password")
                        .setMessage("Change password?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                char[] pswd = RandomPasswordGenerator.generatePswd();
                                String generatedPassword = new String(pswd);
                                password.setText(generatedPassword);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

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
