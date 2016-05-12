package com.password.kg.passwordbook.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.password.kg.model.Categories;
import com.password.kg.passwordbook.AddPasswordActivity;
import com.password.kg.passwordbook.CategoryActivity;
import com.password.kg.passwordbook.R;
import com.password.kg.passwordbook.colorpicker.ColorStateDrawable;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Nurs on 20.01.2016.
 */
public class CategorySpinnerAdapter extends ArrayAdapter<Categories> {

    Context context;
    Activity activity;
    List<Categories> categoriesList;
    LayoutInflater inflater;

    public CategorySpinnerAdapter(Activity activitySpinner, int resourse, List<Categories> categoriesList) {
        super(activitySpinner, resourse, categoriesList);
        activity = activitySpinner;
        this.categoriesList = categoriesList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder {

        TextView categoryName;

        public ViewHolder(View view){
            categoryName = (TextView) view.findViewById(R.id.category_name);
        }

    }

    @Override
    public long getItemId(int position) {
        return categoriesList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
       return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView,ViewGroup parent) {
// TODO Auto-generated method stub
// return super.getView(position, convertView, parent);

       /* ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
            holder = new ViewHolder(convertView);
            holder.categoryName = (TextView) convertView.findViewById(R.id.category_name);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }*/
        View row = inflater.inflate(R.layout.spinner_item, parent, false);

        TextView categoryName = (TextView) row.findViewById(R.id.spinner_category_name);
        ImageView imageView = (ImageView) row.findViewById(R.id.spinner_image);

        Drawable[] colorDrawable = new Drawable[]
                {activity.getResources().getDrawable(R.drawable.color_picker_swatch)};

        imageView.setImageDrawable(new ColorStateDrawable(colorDrawable, categoriesList.get(position).getColor()));
        categoryName.setText(categoriesList.get(position).getName());

        /*holder.categoryName.setText(categoriesList.get(position).getName());
        holder.categoryName.setBackgroundColor(categoriesList.get(position).getColor());
        holder.categoryName.setTextColor(getTextColor(categoriesList.get(position).getColor()));*/

        return row;

       /* convertView  = inflater.inflate(R.layout.category_item, null);
        TextView categoryName = (TextView) convertView.findViewById(R.id.category_name);
        categoryName.setText(categoriesList.get(position).getName());
        categoryName.setBackgroundColor(categoriesList.get(position).getColor());
        categoryName.setTextColor(getTextColor(categoriesList.get(position).getColor()));

        return convertView;*/
    }

    public int getTextColor(int color){
        int d = 0;
        double a = 1 - ( 0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))/255;
        if(a < 0.5) {
            d = 0;
        } else {
            d = 255;
        }
        return Color.rgb(d, d, d);
    }
}
