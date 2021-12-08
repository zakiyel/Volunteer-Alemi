package kz.zhastar.volunteeralemi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText surname, name, email, password;
    private Button register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        surname = findViewById(R.id.surname);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void fail() {

    }

    public void registerUser() {
        final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
        pd.setMessage("Wait...");
        pd.show();

        final String email_str = email.getText().toString();
        final String password_str = password.getText().toString();
        final String name_str = name.getText().toString();
        final String surname_str = surname.getText().toString();

        // email or password field is empty
        if (TextUtils.isEmpty(email_str) || TextUtils.isEmpty(password_str)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        // password is too short
        if (password_str.length()<7) {
            Toast.makeText(this, "Password length must be at least 7 symbols", Toast.LENGTH_SHORT).show();
            return;
        }

        //creating new user in authenticaton
        mAuth.createUserWithEmailAndPassword(email_str,password_str)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Adding new info", Toast.LENGTH_SHORT).show();

                            //adding more information about the user in real time database
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String uid = firebaseUser.getUid();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("name",name_str);
                            hashMap.put("surname",surname_str);
                            hashMap.put("id",uid);
                            hashMap.put("image","https://firebasestorage.googleapis.com/v0/b/volunteeralemi.appspot.com/o/riri.jpeg?alt=media&token=44cc4f12-88f2-4250-a464-8c3dcdb13974");
                            hashMap.put("email",email_str);
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pd.dismiss();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Couldn't register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "Couldn't register, check your connection", Toast.LENGTH_SHORT).show();

                            FirebaseNetworkException e = (FirebaseNetworkException)task.getException();
                            Log.e("RegisterActivity", "Failed Registration", e);
                        }
                    }
                });



    }
}
