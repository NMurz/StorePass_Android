package com.password.kg.passwordbook.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.password.kg.model.Categories;
import com.password.kg.model.CategoriesDao;
import com.password.kg.passwordbook.CategoryActivity;
import com.password.kg.passwordbook.R;
import com.password.kg.passwordbook.colorpicker.ColorPickerDialog;
import com.password.kg.passwordbook.colorpicker.ColorStateDrawable;
import com.password.kg.passwordbook.colorpicker.Utils;
import com.password.kg.passwordbook.helper.DaoManager;

import java.util.List;

/**
 * Created by Nurs on 21.12.2015.
 */
public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.CategoryViewHolder>{

    List<Categories> categories;
    Context context;
    CategoriesDao categoriesDao;

    public CategoryViewAdapter(Context context, List<Categories> categories){
        this.categories = categories;
        this.context = context;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(v);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder,final int position) {
        holder.category_name.setText(categories.get(position).getName());
        int color = categories.get(position).getColor();
        //String hexColor = String.format("#%06X", (0xFFFFFF & color));
        Drawable[] colorDrawable = new Drawable[]
                {context.getResources().getDrawable(R.drawable.color_picker_swatch)};
        holder.color.setImageDrawable(new ColorStateDrawable(colorDrawable, color));
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(context, holder. menu);
                popup.getMenuInflater().inflate(R.menu.recyclerivew_item_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int i = item.getItemId();
                        if (i == R.id.edit_item) {
                            //do something
                            int[] mColor = Utils.ColorUtils.colorChoice(context);

                            Categories category = categories.get(position);
                            ColorPickerDialog colorcalendar = ColorPickerDialog.newInstanceWithCategory(
                                    R.string.color_picker_edit,
                                    mColor,
                                    0,
                                    5,
                                    ColorPickerDialog.SIZE_SMALL, category);
                            FragmentManager fm = ((Activity) context).getFragmentManager();
                            colorcalendar.show(fm,"cal");
                            Toast.makeText(context, "Edit pressed", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (i == R.id.delete_item) {
                            //do something
                            categoriesDao = DaoManager.getCategoriesDao();
                            categoriesDao.delete(categories.get(position));
                            categories.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, categories.size());
                            return true;
                        } else {
                            return onMenuItemClick(item);
                        }
                    }
                });

                popup.show();
            }
        });
//        holder.category_name.setTextColor(getTextColor(color));
//        holder.itemView.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public int getTextColor(int color){
        int d = 0;
        double a = 1 - ( 0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))/255;
        if(a < 0.5) {
            d = 0;
        } else {
            d = 255;
        }
        return Color.rgb(d,d,d);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView category_name;
        ImageView color;
        ImageButton menu;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            color = (ImageView) itemView.findViewById(R.id.color_imageView);
            menu =(ImageButton) itemView.findViewById(R.id.image_category);
        }
    }

}
