package com.password.kg.passwordbook.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.password.kg.model.Passwords;
import com.password.kg.model.PasswordsDao;
import com.password.kg.passwordbook.R;
import com.password.kg.passwordbook.ViewPasswordActivity;
import com.password.kg.passwordbook.helper.DaoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurs on 21.12.2015.
 */
public class PasswordViewAdapter extends RecyclerView.Adapter<PasswordViewAdapter.PasswordViewHolder>{

    List<Passwords> passwords;
    Activity activity;
    PasswordsDao passwordsDao;

    public PasswordViewAdapter(Activity activity, List<Passwords> passwords){
        this.passwords = passwords;
        this.activity = activity;
    }

    public void setPasswords(List<Passwords> passwords) {
        this.passwords = passwords;
        notifyDataSetChanged();
    }

    @Override
    public PasswordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);
        PasswordViewHolder pvh = new PasswordViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PasswordViewHolder passwordViewHolder, final int position) {

        passwordViewHolder.item_title.setText(passwords.get(position).getTitle());
        passwordViewHolder.item_description.setText(passwords.get(position).getDescription());
        passwordViewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ViewPasswordActivity.class);
                intent.putExtra("PasswordId", String.valueOf(passwords.get(position).getId()));
                activity.startActivity(intent);
            }
        });
        passwordViewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(activity, passwordViewHolder.menuButton);
                popup.getMenuInflater().inflate(R.menu.recyclerivew_item_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int i = item.getItemId();
                        if (i == R.id.edit_item) {
                            //do something
                            Toast.makeText(activity, "Edit pressed", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (i == R.id.delete_item) {
                            //do something
                            passwordsDao = DaoManager.getPasswordsDao();
                            passwordsDao.delete(passwords.get(position));
                            passwords.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, passwords.size());
                            Toast.makeText(activity, "Delete pressed", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            return onMenuItemClick(item);
                        }
                    }
                });

                popup.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return passwords.size();
    }

    public static class PasswordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
        TextView item_title;
        TextView item_description;
        ImageButton menuButton;
        private ItemClickListener clickListener;

        PasswordViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_description = (TextView) itemView.findViewById(R.id.item_description);
            menuButton = (ImageButton) itemView.findViewById(R.id.imageButton);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
    }

    public interface ItemClickListener {
        void onClick(View view);
    }

    public void setFilter(List<Passwords> countryModels) {
        passwords = new ArrayList<>();
        passwords.addAll(countryModels);
        notifyDataSetChanged();
    }
}
