package com.password.kg.passwordbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.password.kg.model.Categories;
import com.password.kg.model.CategoriesDao;
import com.password.kg.passwordbook.adapter.CategoryViewAdapter;
import com.password.kg.passwordbook.colorpicker.ColorPickerDialog;
import com.password.kg.passwordbook.colorpicker.ColorPickerSwatch;
import com.password.kg.passwordbook.colorpicker.Utils;
import com.password.kg.passwordbook.helper.DaoManager;
import com.password.kg.passwordbook.helper.DividerItemDecoration;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    int mSelectedColorCal0 = 0;
    CategoriesDao categoriesDao;
    CategoryViewAdapter categoryViewAdapter;
    RecyclerView categoryRecyclerView;
    TextView emptyView;
    List<Categories> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryRecyclerView = (RecyclerView) findViewById(R.id.category_recyclerView);
        emptyView = (TextView) findViewById(R.id.category_empty_view);

        categoriesDao = DaoManager.getCategoriesDao();
        categoriesList = categoriesDao.loadAll();

        if(categoriesList.isEmpty()) {
            categoryRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            categoryRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            categoryViewAdapter = new CategoryViewAdapter(this, categoriesList);
            categoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            categoryRecyclerView.setAdapter(categoryViewAdapter);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int[] mColor = Utils.ColorUtils.colorChoice(CategoryActivity.this);

                ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
                        R.string.color_picker_default_title,
                        mColor,
                        mSelectedColorCal0,
                        5,
                        ColorPickerDialog.SIZE_SMALL);

                //Implement listener to get selected color value
                colorcalendar.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener(){

                    @Override
                    public void onColorSelected(int color) {
                    }

                });

                colorcalendar.show(getFragmentManager(),"cal");
            }
        });
    }

    public void dataChanged(){
        categoriesList = categoriesDao.loadAll();
        if(!categoriesList.isEmpty() && categoriesList.size() == 1) {
            categoryRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            categoryViewAdapter = new CategoryViewAdapter(this, categoriesList);
            categoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            categoryRecyclerView.setAdapter(categoryViewAdapter);
        } else if(!categoriesList.isEmpty()){
            categoryViewAdapter.setCategories(categoriesList);
            categoryRecyclerView.setAdapter(categoryViewAdapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
