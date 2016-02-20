package hacksilesia.europejskiednidziedzictwa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String zagadka = "", ans1 = "", ans2 = "", ans3 = "";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                zagadka = extras.getString("ZAGADKA");
                ans1 = extras.getString("ANS1");
                ans2 = extras.getString("ANS2");
                ans3 = extras.getString("ANS3");
            }
        } else {
            zagadka = (String) savedInstanceState.getSerializable("ZAGADKA");
            ans1 = (String) savedInstanceState.getSerializable("ANS1");
            ans2 = (String) savedInstanceState.getSerializable("ANS2");
            ans3 = (String) savedInstanceState.getSerializable("ANS3");
        }
        TextView zv = (TextView)findViewById(R.id.zagadka);
        zv.setText(zagadka);
        Button ans1v = (Button)findViewById(R.id.ans1);
        Button ans2v = (Button)findViewById(R.id.ans2);
        Button ans3v = (Button)findViewById(R.id.ans3);
        ans1v.setText(ans1);
        ans2v.setText(ans2);
        ans3v.setText(ans3);
    }

    public void button1Clicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("edittextvalue", 1);
        setResult(RESULT_OK, intent);
        finish();
    }
    public void button2Clicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("edittextvalue",2);
        setResult(RESULT_OK, intent);
        finish();
    }
    public void button3Clicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("edittextvalue",3);
        setResult(RESULT_OK, intent);
        finish();
    }

}
