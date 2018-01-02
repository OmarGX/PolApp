package disco.unimib.it.polapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class DetectedActivity extends AppCompatActivity {

    private List<Cestino> cestini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected);
        Bundle bundle=getIntent().getExtras();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setTitle(bundle.getString("titolo"));

        RecyclerView rv= (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm= new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        Initialize();

        RVAdapter adapter=new RVAdapter(cestini);
        rv.setAdapter(adapter);








    }

    private void Initialize(){
        cestini=new ArrayList<>();
        cestini.add(new Cestino(0,"Indifferenziato"));
        cestini.add(new Cestino(0,"Carta"));
        cestini.add(new Cestino(0,"Plastica"));
        cestini.add(new Cestino(0,"Vetro"));
    }
}
