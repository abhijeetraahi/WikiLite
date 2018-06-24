package com.raahi.wikilite.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.raahi.wikilite.R;
import com.raahi.wikilite.util.HttpUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private AutoCompleteTextView searchField;
    private Button btnSearch;
    private TextView errorMessage;
    private ArrayAdapter<String> aAdapter;
    private List<String> suggestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchField = findViewById(R.id.actv_search);
        btnSearch = findViewById(R.id.btn_search);
        errorMessage = findViewById(R.id.tv_error_msg);

        btnSearch.setOnClickListener(this);
        searchField.setOnEditorActionListener(this);
        searchField.setOnFocusChangeListener(this);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = searchField.getText().toString().trim();
                new getSuggestionResult().execute(newText);
            }
        });
        suggestionList = new ArrayList<String>();
    }

    private boolean validate() {
        boolean valid = true;
        String input = searchField.getText().toString();

        if (input.isEmpty()) {
            String error_message = "";
            error_message = "Input field is empty";
            errorMessage.setText(error_message);
            errorMessage.setVisibility(View.VISIBLE);
            return false;
        }
        errorMessage.setVisibility(View.GONE);
        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                hideKeyboard(v);
                searchField.clearFocus();
                if (!validate()) {
                    return;
                }
                String input = searchField.getText().toString().trim().replaceAll(" ", "+");
                openResultActivity(input);
                break;
        }
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        // handle back button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        dialog.dismiss();
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            hideKeyboard(v);
            btnSearch.performClick();
            return true;
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard(v);
        } else {
            suggestionList = new ArrayList<>();
            errorMessage.setVisibility(View.GONE);
        }
    }

    class getSuggestionResult extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... key) {
            String newText = key[0];
            newText = newText.trim();
            newText = newText.replace(" ", "+");
            try {
                HttpUtil httpUtil = new HttpUtil("http://en.wikipedia.org/w/api.php?action=opensearch&search=" + newText + "&limit=8&namespace=0&format=json", "GET", null, null);
                String jsonresponse = httpUtil.getStringResponse();
                suggestionList = new ArrayList<String>();
                JSONArray jArray = new JSONArray(jsonresponse);
                for (int i = 0; i < jArray.getJSONArray(1).length(); i++) {
                    String SuggestKey = jArray.getJSONArray(1).getString(i);
                    suggestionList.add(SuggestKey);
                }
            } catch (Exception e) {
                Log.d(TAG + " Error", e.getMessage());
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    aAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.suggestion_item, suggestionList);
                    searchField.setAdapter(aAdapter);
                    aAdapter.notifyDataSetChanged();
                }
            });

            return null;
        }
    }

    private void openResultActivity(String searchQuery) {
        Intent intent = new Intent(HomeActivity.this, ResultsActivity.class);
        intent.putExtra("searchQuery", searchQuery);
        startActivity(intent);
    }
}
