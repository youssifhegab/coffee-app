package com.example.youssifhegab.justandroid;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    boolean addChocolate;
    boolean addCream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI(findViewById(R.id.parent));


    }
    /**
     * reviewing the order summary for the customer before sending.
     */
    public void submitOrder(View view) {

        if (!(userName().trim().equals("")) && !(quantity == 0)) {
            displayMessage(message(quantity));
        } else {
            if (quantity == 0) {
                Toast.makeText(this, "you should order at least one coffee", Toast.LENGTH_SHORT).show();
                displayMessage("");
            } else {
                displayMessage("");
            }
        }
    }

    /**
     * sending an email of the order summary.
     */
    public void send(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject(quantity));
        intent.putExtra(Intent.EXTRA_TEXT, message(quantity) );

        if (intent.resolveActivity(getPackageManager()) != null &&
                !(userName().trim().equalsIgnoreCase("")) && !(quantity == 0)) {
            startActivity(intent);
        }else if (quantity == 0 )
            Toast.makeText(this, "you should order at least one coffee", Toast.LENGTH_SHORT).show();
    }

    /**
     * increasing the number of coffee ordered.
     */
    public void plusButton(View view){
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * decreasing the number of coffee ordered.
     */
    public void minusButton(View view){
        if(quantity == 0){
            quantity = 0;
            Toast.makeText(this, "you can't go less than ZERO", Toast.LENGTH_SHORT).show();
        }else{
            quantity--;
        }
        displayQuantity(quantity);
    }


    /**
     * setupUI() method is making the app when i touch anything except Edittext it will close
     * the soft keyboard
     */
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });

        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     *  displays the whole order summary
     **/
    private void displayMessage(String message){
        TextView textView = (TextView) findViewById(R.id.price_text_view);
        textView.setText("" +message);
    }

    /**
     * checking the whipped cream is added or not.
     */
    private String addCream(){
        CheckBox c = (CheckBox) findViewById(R.id.cream);
        if(c.isChecked()){
            addCream = true;
            return "\nWhipped cream is added";
        }else{
            addCream = false;
            return "";
        }

    }

    /**
     *  adding the price of the whipped cream which is 1$.
     */
    public int addingCreamePrice(boolean isChecked){
        int creamPrice = 0;
        if(isChecked == true){
            return creamPrice = 1;
        }else {
            return 0;
        }
    }

    /**
     * checking the chocolate is added or not.
     */
    private String addChocolate(){
        CheckBox c = (CheckBox) findViewById(R.id.choco);
        if(c.isChecked()){
            addChocolate = true;
            return "\nChocolate is added";
        }else{
            addChocolate = false;
            return "";
        }

    }
    /**
     *  adding the price of the chocolate which is 1$.
     */
    public int addingChocolatePrice(boolean isChecked){
        int chocoPrice = 0;
        if(isChecked == true){
            return chocoPrice = 1;
        }else {
            return 0;
        }
    }

    /**
     *  this is the edittext
     */
    private String userName(){
        final EditText editText = (EditText) findViewById(R.id.name);
        popUpNameMissing(editText);

        String userName = editText.getText().toString().trim();

        return userName;
    }

    /**
     * to make the pop-up message of the name missing
     */
    private void popUpNameMissing(final EditText e){
//        e.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                e.setError(null);
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                e.setError(null);
//
//            }
//        });
        if(e.getText().toString().trim().equalsIgnoreCase("")){
            e.setError("Enter UserName");
        }

    }

    /**
     *  hiding the keyboard when touching the screen
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    /**
     * email subject
     */
    public String emailSubject(int numOfCoffees){
        if (numOfCoffees == 1){
            return numOfCoffees + " coffee for " + userName();
        }else {
            return numOfCoffees + " coffees for " + userName();
        }
    }

    /**
     * making the order summary.
     */
    private String message(int num){
        String name ="Name: " + userName();
        String whipped = addCream();
        String choco =addChocolate();
        String quan = "quantity: " + num;
        int totalAmount = num*5 + addingChocolatePrice(addChocolate) + addingCreamePrice(addCream);
        String total = "Total: " + totalAmount + "$" ;
        String fullMessage = name + whipped + choco + "\n" +
                        quan + "\n" + total + "\n" + "Thank you!";
        return fullMessage;
    }

}