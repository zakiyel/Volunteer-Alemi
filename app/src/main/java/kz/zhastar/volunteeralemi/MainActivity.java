package kz.zhastar.volunteeralemi;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new SuggestionsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


    }
}
