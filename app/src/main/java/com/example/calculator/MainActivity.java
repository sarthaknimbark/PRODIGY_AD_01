package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    private TextView data;
    private TextView result;
    private ScriptEngine scriptEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.data_tv);
        result = findViewById(R.id.res_tv);

        MaterialButton c = findViewById(R.id.btn_c);
        MaterialButton openBracket = findViewById(R.id.btn_openbracket);
        MaterialButton closeBracket = findViewById(R.id.btn_closebracket);
        MaterialButton minus = findViewById(R.id.btn_sub);
        MaterialButton seven = findViewById(R.id.btn_seven);
        MaterialButton eight = findViewById(R.id.btn_eight);
        MaterialButton nine = findViewById(R.id.btn_nine);
        MaterialButton division = findViewById(R.id.btn_div);
        MaterialButton four = findViewById(R.id.btn_four);
        MaterialButton five = findViewById(R.id.btn_five);
        MaterialButton six = findViewById(R.id.btn_six);
        MaterialButton multiplication = findViewById(R.id.btn_mul);
        MaterialButton one = findViewById(R.id.btn_one);
        MaterialButton two = findViewById(R.id.btn_two);
        MaterialButton three = findViewById(R.id.btn_three);
        MaterialButton plus = findViewById(R.id.btn_add);
        MaterialButton ac = findViewById(R.id.btn_ac);
        MaterialButton zero = findViewById(R.id.btn_zero);
        MaterialButton point = findViewById(R.id.btn_float);
        MaterialButton equal = findViewById(R.id.btn_ans);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setButtonListeners(c, openBracket, closeBracket, minus, seven, eight, nine, division, four, five, six, multiplication, one, two, three, plus, ac, zero, point, equal);

        scriptEngine = new ScriptEngineManager().getEngineByName("rhino");
    }

    private void setButtonListeners(MaterialButton... buttons) {
        for (MaterialButton button : buttons) {
            button.setOnClickListener(this::onClick);
        }
    }

    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        String btnText = btn.getText().toString();

        if (btnText.equals("C")) {
            data.setText("");
            result.setText("");
        } else if (btnText.equals("AC")) {
            data.setText("");
            result.setText("0");
            return;
        } else if (btnText.equals("=")) {
            String dataText = data.getText().toString();
            String res = evaluateExpression(dataText);
            result.setText(res);
        } else {
            if (btnText.equals("×")) {
                btnText = "*";
            } else if (btnText.equals("÷")) {
                btnText = "/";
            }
            data.append(btnText);
        }
    }


    private String evaluateExpression(String expression) {
        try {
            Object result = scriptEngine.eval(expression);
            String resultString = result.toString();
            if (resultString.endsWith(".0")) {
                resultString = resultString.replace(".0", "");
            }
            return resultString;
        } catch (ScriptException e) {
            e.printStackTrace();
            return "Err";
        }
    }
}
