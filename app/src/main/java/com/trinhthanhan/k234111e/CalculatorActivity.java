package com.trinhthanhan.k234111e;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {
    EditText edtFormular;
    Button btnDel, btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        addView();
        addEvent();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.chkSaveInfor), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addEvent() {
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String formular = edtFormular.getText().toString();
                if (formular.equals("Lỗi")) {
                    edtFormular.setText("");
                    return;
                }
                String new_formular = "";
                if (formular.length() > 1) {
                    new_formular = formular.substring(0, formular.length() - 1);
                }
                edtFormular.setText(new_formular);
                edtFormular.setSelection(edtFormular.getText().length());
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String formular = edtFormular.getText().toString();
                if (formular.isEmpty()) return;
                try {
                    double result = eval(formular);
                    if (result == (long) result) {
                        edtFormular.setText(String.format("%d", (long) result));
                    } else {
                        edtFormular.setText(String.valueOf(result));
                    }
                } catch (Exception e) {
                    edtFormular.setText("Lỗi");
                }
                edtFormular.setSelection(edtFormular.getText().length());
            }
        });
    }
    private void addView() {
        edtFormular = findViewById(R.id.edtFormular);
        btnDel = findViewById(R.id.btnDel);
        btnCalculate = findViewById(R.id.btnCalculate);
    }
    public void processInputData(View view) {
        Button btn = (Button) view;
        String new_value = btn.getText().toString();
        String current_value = edtFormular.getText().toString();
        if (current_value.equals("Lỗi")) {
            current_value = "";
        }
        String lasted_value = current_value + new_value;
        edtFormular.setText(lasted_value);
        edtFormular.setSelection(edtFormular.getText().length());
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Lỗi cú pháp: " + (char) ch);
                return x;
            }
            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // phép cộng
                    else if (eat('-')) x -= parseTerm(); // phép trừ
                    else return x;
                }
            }
            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // phép nhân
                    else if (eat('/')) x /= parseFactor(); // phép chia
                    else return x;
                }
            }
            double parseFactor() {
                if (eat('+')) return parseFactor(); // dấu cộng phía trước
                if (eat('-')) return -parseFactor(); // dấu trừ phía trước
                double x;
                int startPos = this.pos;
                if (eat('(')) { // ngoặc đơn
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // số tự nhiên
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Lỗi cú pháp: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }
}