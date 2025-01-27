package com.solution.it.newsoft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.solution.it.newsoft.databinding.ActivityLoginBinding;
import com.solution.it.newsoft.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        binding.btnLogin.setOnClickListener(v -> {
            if (binding.etUsername.getText().toString().isEmpty()
                    || binding.etPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_LONG).show();
            } else {
                ProgressDialog progress = ProgressDialog.show(this, "", "Loading...", true);
                progress.show();
                viewModel.login(binding.etUsername.getText().toString(), binding.etPassword.getText().toString()).observe(this, login -> {
                    if (login != null) {
                        if (login.getStatus() != null && login.getStatus().getCode().equals("200")) {
                            Intent intent = new Intent(LoginActivity.this, ListingActivity.class);
                            startActivity(intent);
                        }
                        if (login.getStatus() != null)
                            Toast.makeText(this, login.getStatus().getMessage(), Toast.LENGTH_LONG).show();
                    } else Toast.makeText(this, "Please try again", Toast.LENGTH_LONG).show();
                    progress.cancel();
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
