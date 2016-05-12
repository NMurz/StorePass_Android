package com.password.kg.passwordbook;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.password.kg.model.Passwords;
import com.password.kg.model.PasswordsDao;
import com.password.kg.passwordbook.helper.AesCryptHelper;
import com.password.kg.passwordbook.helper.DaoManager;

public class ViewPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    EditText website;
    EditText account;
    EditText password;
    EditText description;
    ImageButton websiteCopyBtn;
    ImageButton accountCopyBtn;
    ImageButton passwordCopyBtn;
    ImageButton descriptionCopyBtn;
    ImageButton passwordVisibleBtn;
    PasswordsDao passwordsDao;
    Passwords passwordItem;
    String passwordId;
    CoordinatorLayout coordinatorLayout;
    boolean isPasswordVisible;
    ClipboardManager clipboard;
    ClipData clip;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        website = (EditText) findViewById(R.id.row_website);
        account = (EditText) findViewById(R.id.row_account);
        password = (EditText) findViewById(R.id.row_password);
        description = (EditText) findViewById(R.id.row_description);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.viewPassword_coordinatorLayout);

        websiteCopyBtn = (ImageButton) findViewById(R.id.copy_web_button);
        accountCopyBtn = (ImageButton) findViewById(R.id.copy_account_button);
        passwordCopyBtn = (ImageButton) findViewById(R.id.copy_pass_button);
        descriptionCopyBtn = (ImageButton) findViewById(R.id.copy_description_button);
        passwordVisibleBtn = (ImageButton) findViewById(R.id.password_show);

        websiteCopyBtn.setOnClickListener(this);
        accountCopyBtn.setOnClickListener(this);
        passwordCopyBtn.setOnClickListener(this);
        descriptionCopyBtn.setOnClickListener(this);
        passwordVisibleBtn.setOnClickListener(this);

        Intent intent = getIntent();

        passwordId = intent.getStringExtra("PasswordId");

        passwordsDao = DaoManager.getPasswordsDao();
        passwordItem = passwordsDao.loadByRowId(Integer.parseInt(passwordId));

        getSupportActionBar().setTitle(passwordItem.getTitle());
        website.setText(passwordItem.getWebsite());
        account.setText(passwordItem.getAccount());
        byte[] passwordInBytes = passwordItem.getPassword().getBytes();
        //byte[] decryptedPassword = AesCryptHelper.aesDecrypt(passwordItem.getPassword());
        //String decryptedPasswordStr = new String(decryptedPassword);
        password.setText(AesCryptHelper.aesDecrypt(passwordItem.getPassword()));
        description.setText(passwordItem.getDescription());

        website.setKeyListener(null);
        account.setKeyListener(null);
        password.setKeyListener(null);
        description.setKeyListener(null);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_password_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.edit_password_menu_item:
                Intent intent = new Intent(ViewPasswordActivity.this, EditPasswordActivity.class);
                intent.putExtra("PasswordId", passwordId);
                startActivity(intent);
                return true;
            case R.id.delete_password_menu_item:
                passwordsDao.delete(passwordItem);
                finish();
                Toast.makeText(ViewPasswordActivity.this, "Entry deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.window:
                Intent windowIntent = new Intent(getBaseContext(), FloatingWindow.class);
                windowIntent.putExtra("Login", passwordItem.getAccount());
                windowIntent.putExtra("Password", passwordItem.getPassword());
                startService(windowIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            case R.id.copy_web_button:
                clip = ClipData.newPlainText("web", website.getText().toString());
                clipboard.setPrimaryClip(clip);
                snackbar = Snackbar.make(coordinatorLayout, "Website copied to clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
            case R.id.copy_account_button:
                clip = ClipData.newPlainText("account", account.getText().toString());
                clipboard.setPrimaryClip(clip);
                snackbar = Snackbar.make(coordinatorLayout, "Account copied to clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;

            case R.id.copy_pass_button:
                clip = ClipData.newPlainText("password", password.getText().toString());
                clipboard.setPrimaryClip(clip);
                snackbar = Snackbar.make(coordinatorLayout, "Password copied to clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
            case R.id.copy_description_button:
                clip = ClipData.newPlainText("description", description.getText().toString());
                clipboard.setPrimaryClip(clip);
                snackbar = Snackbar.make(coordinatorLayout, "Description copied to clipboard", Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
        }

    }
}
