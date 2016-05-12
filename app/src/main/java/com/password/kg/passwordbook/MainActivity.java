package com.password.kg.passwordbook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.password.kg.model.Categories;
import com.password.kg.model.CategoriesDao;
import com.password.kg.model.Passwords;
import com.password.kg.model.PasswordsDao;
import com.password.kg.model.User;
import com.password.kg.model.UserDao;
import com.password.kg.passwordbook.adapter.PasswordViewAdapter;
import com.password.kg.passwordbook.dto.CategoryDto;
import com.password.kg.passwordbook.dto.DataDto;
import com.password.kg.passwordbook.dto.PasswordDto;
import com.password.kg.passwordbook.helper.DaoManager;
import com.password.kg.passwordbook.model.SyncData;
import com.password.kg.passwordbook.rest.ApiUrls;
import com.password.kg.passwordbook.rest.ServiceGenerator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener{

    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    List<Passwords> passwordsList;
    PasswordsDao passwordsDao;
    CategoriesDao categoriesDao;
    TextView emptyView;
    RecyclerView recyclerView;
    PasswordViewAdapter adapter;
    UserDao userDao;
    User user;
    List<User> user_lst;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAuthenticated();
        setContentView(R.layout.activity_main);
        
        initToolbar();

        passwordsDao = DaoManager.getPasswordsDao();
        categoriesDao = DaoManager.getCategoriesDao();

        if(isAuthenticated()){
            if(passwordsDao.loadAll().isEmpty()) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading data...");
                progressDialog.setTitle("Synchronization");
                progressDialog.show();
                GetSyncData();
            }
        }


        passwordsList = passwordsDao.loadAll();

        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        emptyView = (TextView) findViewById(R.id.empty_view);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinatorLayout);

        if(passwordsList.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PasswordViewAdapter(this, passwordsList);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
        recyclerView.setOnClickListener(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddPasswordActivity.class));
            }
        });

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.manage_categories:
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialogStyle);
                builder.setTitle("Exit")
                        .setMessage("Are you sure? All your data will be deleted")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                passwordsDao.deleteAll();
                                categoriesDao.deleteAll();
                                userDao.deleteAll();
                                System.exit(0);
                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }

    @Override
    protected void onResume() {

        refreshList();
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Passwords> filteredModelList = filter(passwordsList, newText);
        if(!filteredModelList.isEmpty()) adapter.setFilter(filteredModelList);
        return true;
    }

    public void refreshList(){
        passwordsList.clear();
        passwordsList = passwordsDao.loadAll();

        if(!passwordsList.isEmpty()){
            if(recyclerView.getVisibility() == View.GONE)  recyclerView.setVisibility(View.VISIBLE);
            if(emptyView.getVisibility() == View.VISIBLE) emptyView.setVisibility(View.GONE);
            if(adapter == null)  {
                adapter = new PasswordViewAdapter(this, passwordsList);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setPasswords(passwordsList);
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    private List<Passwords> filter(List<Passwords> models, String query) {
        query = query.toLowerCase();

        final List<Passwords> filteredModelList = new ArrayList<>();
        for (Passwords model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void PostSyncData(){

    }

    public void GetSyncData(){
        ApiUrls.DataSync getData = ServiceGenerator.createService(ApiUrls.DataSync.class, user.getToken());
        Call<DataDto> call = getData.getSyncData();
        call.enqueue(new Callback<DataDto>() {
            @Override
            public void onResponse(Response<DataDto> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    user.setLanguageId(response.body().getUser().getLanguageId());
                    user.setFIO(response.body().getUser().getFio());
                    userDao.update(user);
                    for (CategoryDto categoryDto : response.body().getData()) {
                        Categories category = new Categories();
                        category.setUserId(user.getId());
                        category.setName(categoryDto.getCategory());
                        categoriesDao.insert(category);
                        if(!categoryDto.getPasswords().isEmpty()){
                            for (PasswordDto passwordDto : categoryDto.getPasswords()) {
                                Passwords password = new Passwords();
                                password.setCategoryId(category.getId());
                                password.setTitle(passwordDto.getTitle());
                                password.setAccount(passwordDto.getTitle());
                                password.setPassword(passwordDto.getTitle());
                                password.setDescription(passwordDto.getDescriptionText());
                                password.setWebsite(passwordDto.getLink());
                                passwordsDao.insert(password);
                            }
                        }

                    }
                    progressDialog.dismiss();
                    refreshList();
                } else {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialogStyle);
                    builder.setTitle("Network")
                            .setMessage("Network error. Try again later.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialogStyle);
                builder.setTitle("Network")
                        .setMessage("No internet access.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    public boolean isAuthenticated(){
        userDao = DaoManager.getUserDao();
        user_lst = userDao.loadAll();
        if(!user_lst.isEmpty()) {
            user = user_lst.get(0);
            return true;
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return false;
        }
    }
}
