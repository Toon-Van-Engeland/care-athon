package be.pxl.g_karate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class OpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_button, menu);
        MenuItem item = menu.findItem(R.id.add_exercise_button);
        item.setOnMenuItemClickListener((view) -> {
            Intent intent = new Intent(OpenActivity.this, CreateActivity.class);
            startActivity(intent);
            return true;
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void easy_click(View view) {
        Intent intent = new Intent(OpenActivity.this, MainActivity.class);
        intent.putExtra("iconEnabled", true);
        intent.putExtra("colorEnabled", true);
        startActivity(intent);
    }

    public void medium_click(View view) {
        Intent intent = new Intent(OpenActivity.this, MainActivity.class);
        intent.putExtra("iconEnabled", false);
        intent.putExtra("colorEnabled", true);
        startActivity(intent);
    }

    public void difficult_click(View view) {
        Intent intent = new Intent(OpenActivity.this, MainActivity.class);
        intent.putExtra("iconEnabled", false);
        intent.putExtra("colorEnabled", false);
        startActivity(intent);
    }
}
