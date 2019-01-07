package com.example.dwalshe.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Log Tags
    private static final String TAG = "MainActivity";

    // Bunlde Key Constants
    final private String HISTORY = "historyContent";
    final private String INPUT = "inputContent";
    final private String OPERATION = "operationContent";

    // Layout Variables
    private EditText result;
    private EditText userInput;
    private TextView displayOperation;
    private TextView history;

    // Variables to hold operands and type of calculations
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link Layout IDs
        result = findViewById(R.id.result);
        userInput = findViewById(R.id.userInput);
        displayOperation = findViewById(R.id.textViewOperator);
        history = findViewById(R.id.textViewHistory);


        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDecimalPoint);

        //Create button arraylist for easy access and assignment of listeners
        ArrayList<Button> buttonArray = new ArrayList<>();
        buttonArray.add(button0);
        buttonArray.add(button1);
        buttonArray.add(button2);
        buttonArray.add(button3);
        buttonArray.add(button4);
        buttonArray.add(button5);
        buttonArray.add(button6);
        buttonArray.add(button7);
        buttonArray.add(button8);
        buttonArray.add(button9);
        buttonArray.add(buttonDot);

        //Create operation arraylist for accessing operation keys
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonEquals = findViewById(R.id.buttonEquals);

        ArrayList<Button> operationArray = new ArrayList<>();
        operationArray.add(buttonPlus);
        operationArray.add(buttonMinus);
        operationArray.add(buttonMultiply);
        operationArray.add(buttonDivide);
        operationArray.add(buttonEquals);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                userInput.append(b.getText().toString());
            }
        };
        for (int i = 0; i < buttonArray.size(); i++) {
            buttonArray.get(i).setOnClickListener(buttonListener);
        }

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String op = b.getText().toString();
                String value = userInput.getText().toString();
                if (value.length() != 0) {
                    performOperation(value, op);
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };
        for (int i = 0; i < operationArray.size(); i++) {
            operationArray.get(i).setOnClickListener(operationListener);
        }
    }

    private void performOperation(String value, String operation) {
        if (operand1 == null) {
            operand1 = Double.valueOf(value);
        } else {
            operand2 = Double.valueOf(value);

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "+":
                    history.append(operand1 + " + " + operand2 + " = ");
                    operand1 += operand2;
                    history.append(operand1 + "\n");
                    break;
                case "*":
                    history.append(operand1 + " * " + operand2 + " = ");
                    operand1 *= operand2;
                    history.append(operand1 + "\n");
                    break;
                case "-":
                    history.append(operand1 + " - " + operand2 + " = ");
                    operand1 -= operand2;
                    history.append(operand1 + "\n");
                    break;
                case "/":
                    if (operand2 == 0) {
                        operand1 = 0.0;
                        history.append("DIV by zero not allowed!" + "\n");
                    } else {
                        history.append(operand1 + " / " + operand2 + " = ");
                        operand1 /= operand2;
                        history.append(operand1 + "\n");
                    }
                    break;
                default:
                    operand1 = operand2;
                    break;
            }
        }
        result.setText(operand1.toString());
        userInput.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(HISTORY, history.getText().toString());
        outState.putString(OPERATION, pendingOperation);
        outState.putString(INPUT, userInput.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: out");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);
        history.setText(savedInstanceState.getString(HISTORY));
        displayOperation.setText(savedInstanceState.getString(OPERATION));
        userInput.setText(savedInstanceState.getString(INPUT));
        Log.d(TAG, "onRestoreInstanceState: out");
    }
}
