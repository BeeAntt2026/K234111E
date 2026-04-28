package com.trinhthanhan.k234111e;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
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
    }
    public void loginSystem(View view) {
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        if (username.equalsIgnoreCase("admin") && password.equals("123"))
        {
            txtMessage.setText(getString(R.string.str_login_successful));
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            txtMessage.setText(getString(R.string.str_login_failed));
        }
    }

    public void exitSystem(View view) {
        finish();
    }
}