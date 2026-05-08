package com.trinhthanhan.k234111e;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    /*
     * decalre all view as variables
     *  **/
    EditText editUsername, editPassword;
    TextView txtMessage;
    Button btnLogin, btnExit;
    CheckBox chkSaveInfor;
    String shared_pref_key="LoginInfor";
    RadioButton radAdmin, radEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chkSaveInfor), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void addViews() {
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        txtMessage = findViewById(R.id.txtMessage);
        btnLogin = findViewById(R.id.btnLogin);
        btnExit = findViewById(R.id.btnExit);
        chkSaveInfor=findViewById(R.id.checkBox);
        radAdmin=findViewById(R.id.radAdmin);
        radEmployee=findViewById(R.id.radEmployee);
    }
    public void loginSystem(View view) {
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        Boolean saved=false;
        if(chkSaveInfor.isChecked())
            saved=true;
        if (username.equalsIgnoreCase("admin") && password.equals("123")) {
            txtMessage.setText(getString(R.string.str_login_successful));
            //lưu thông tin đăng nhập
            SharedPreferences preferences = getSharedPreferences(shared_pref_key, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Username", username);
            editor.putString("Password", password);
            editor.putBoolean("Saved", saved);
            editor.apply();
            if (radAdmin.isChecked()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(LoginActivity.this, EmployeeManagementActivity.class);
                startActivity(intent);
            }
        }
        else {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }

    public void exitSystem(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(shared_pref_key, MODE_PRIVATE);
        String username = preferences.getString("Username", "");
        String password = preferences.getString("Password", "");
        boolean saved = preferences.getBoolean("Saved", false);
        if (saved) {
            editUsername.setText(username);
            editPassword.setText(password);
        }
        chkSaveInfor.setChecked(saved);
    }
}