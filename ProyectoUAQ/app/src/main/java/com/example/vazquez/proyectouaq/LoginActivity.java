package com.example.vazquez.proyectouaq;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.animation.ViewAnimation;
import com.example.vazquez.proyectouaq.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    //private ActivitySignInBinding mBinding;
    private ActivityLoginBinding mBinding;

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnReset;
    private TextView inputText;
    private FrameLayout btnLogin;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnLogin = (FrameLayout) findViewById(R.id.btn_login);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        inputText = (TextView) findViewById(R.id.text);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "¡Ingrese su correo electrónico!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "¡Ingrese contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MenuMain.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
    public void load(View view) {
        animateButtonWidth();

        fadeOutTextAndShowProgressDialog();

        nextAction();
    }

    private void fadeOutTextAndShowProgressDialog() {
        inputText.animate().alpha(0f)
                .setDuration(250)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        showProgressDialog();
                    }
                })
                .start();
    }

    private void animateButtonWidth() {
        ValueAnimator anim = ValueAnimator.ofInt(btnLogin.getMeasuredWidth(), getFabWidth());

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = btnLogin.getLayoutParams();
                layoutParams.width = val;
                btnLogin.requestLayout();
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    private void showProgressDialog() {
       progressBar.setAlpha(1f);
       progressBar
                .getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(VISIBLE);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                revealButton();

                fadeOutProgressDialog();

                //delayedStartNextActivity();

            }
        }, 2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealButton() {
        progressBar.setElevation(0f);

        btnLogin.setVisibility(VISIBLE);

        int cx = btnLogin.getWidth();
        int cy = btnLogin.getHeight();


        int x = (int) (getFabWidth() / 2 + progressBar.getX());
        int y = (int) (getFabWidth() / 2 + progressBar.getY());

        float finalRadius = Math.max(cx, cy) * 1.2f;

        final Animator reveal = ViewAnimationUtils
                .createCircularReveal(btnLogin, x, y, getFabWidth(), finalRadius);

        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset(animation);
//                finish();
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private void reset(Animator animation) {
                super.onAnimationEnd(animation);
                btnLogin.setVisibility(VISIBLE);
                inputText.setVisibility(VISIBLE);
                inputText.setAlpha(1f);
                btnLogin.setElevation(4f);
                ViewGroup.LayoutParams layoutParams = btnLogin.getLayoutParams();
                layoutParams.width = (int) (getResources().getDisplayMetrics().density * 300);
                btnLogin.requestLayout();
            }
        });

        reveal.start();
    }

    private void fadeOutProgressDialog() {
        progressBar.animate().alpha(0f).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        }).start();
    }

    private void delayedStartNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, MenuMain.class));

            }
        }, 100);
    }

    private int getFabWidth() {
        return (int) getResources().getDimension(R.dimen.fab_size);
    }



}