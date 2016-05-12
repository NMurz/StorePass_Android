package com.password.kg.passwordbook;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;

/**
 * Created by Nurs on 17.02.2016.
 */
public class FloatingWindow extends Service {

    private WindowManager wm;
    private LinearLayout linearLayout;
    private Button closeBtn;
    private ImageButton closeWindow;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private EditText login;
    private ImageButton copyLogin;
    private EditText password;
    private ImageButton copyPassword;
    private ImageButton showPassword;
    private TextView loginTxtView;
    private TextView passwordTxtView;
    private String login_s;
    private String password_s;
    ClipboardManager clipboard;
    ClipData clip;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        login_s = intent.getStringExtra("Login");
        password_s = intent.getStringExtra("Password");
        password.setText(password_s);
        login.setText(login_s);

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        float margin = convertDpToPixel(8, getApplicationContext());
        llParameters.setMargins((int) margin, (int) margin, (int) margin, (int) margin);
        linearLayout.setBackgroundColor(Color.parseColor("#3F51B5"));
        linearLayout.setLayoutParams(llParameters);




        ViewGroup.LayoutParams editTextParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams imageButtonParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);


        closeWindow = new ImageButton(this);
        closeWindow.setImageResource(R.drawable.ic_close);
        closeWindow.setLayoutParams(rightGravityParams);
        closeWindow.setBackgroundColor(Color.parseColor("#3F51B5"));





        linearLayout1 = new LinearLayout(this);
        LinearLayout.LayoutParams llparams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llparams1.setMargins((int) margin, 0, 0, 0);
        linearLayout1.setLayoutParams(llparams1);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

        login = new EditText(this);
        login.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

        copyLogin = new ImageButton(this);
        copyLogin.setImageResource(R.drawable.ic_content_copy);
        copyLogin.setBackgroundColor(Color.parseColor("#3F51B5"));

        login.setLayoutParams(llp);
        copyLogin.setLayoutParams(imageButtonParams);



        linearLayout1.addView(login);
        linearLayout1.addView(copyLogin);

        linearLayout2 = new LinearLayout(this);
        linearLayout2.setLayoutParams(llparams1);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

        password = new EditText(this);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        copyPassword = new ImageButton(this);
        copyPassword.setImageResource(R.drawable.ic_content_copy);
        copyPassword.setBackgroundColor(Color.parseColor("#3F51B5"));

        password.setLayoutParams(llp);
        copyPassword.setLayoutParams(imageButtonParams);

        linearLayout2.addView(password);
        linearLayout2.addView(copyPassword);

        loginTxtView = new TextView(this);
        loginTxtView.setText("Login:");
        loginTxtView.setLayoutParams(llparams1);

        passwordTxtView = new TextView(this);
        passwordTxtView.setText("Password:");
        passwordTxtView.setLayoutParams(llparams1);

        login.setKeyListener(null);
        password.setKeyListener(null);

        float dpw = convertDpToPixel(200, getApplicationContext());
        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams((int)dpw, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        parameters.x = 0;
        parameters.y = 0;
        parameters.gravity = Gravity.CENTER;

        linearLayout.addView(closeWindow);
        linearLayout.addView(loginTxtView);
        linearLayout.addView(linearLayout1);
        linearLayout.addView(passwordTxtView);
        linearLayout.addView(linearLayout2);
        wm.addView(linearLayout, parameters);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {

            private WindowManager.LayoutParams updatedParameters = parameters;
            int x, y;
            float touchedX, touchedY;

            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = updatedParameters.x;
                        y = updatedParameters.y;

                        touchedX = event.getRawX();
                        touchedY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x = (int) (x + (event.getRawX() - touchedX));
                        updatedParameters.y = (int) (y + (event.getRawY() - touchedY));

                        wm.updateViewLayout(linearLayout, updatedParameters);

                    default:
                        break;
                }

                return false;
            }
        });

        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(linearLayout);
                stopSelf();
            }
        });

        copyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clip = ClipData.newPlainText("login", login_s);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(FloatingWindow.this, "Login copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        copyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clip = ClipData.newPlainText("password", password_s);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(FloatingWindow.this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
