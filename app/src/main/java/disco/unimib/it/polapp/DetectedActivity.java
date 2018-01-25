package disco.unimib.it.polapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DetectedActivity extends AppCompatActivity {

    private List<Cestino> bidoni= new ArrayList<>();





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

        RVAdapter adapter=new RVAdapter(bidoni);
        rv.setAdapter(adapter);





        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Log.d("ciao", bidoni.get(position).getTipo());
                final AlertDialog.Builder builder2=new AlertDialog.Builder(DetectedActivity.this);
                builder2.setTitle(bidoni.get(position).getTipo())
                        .setItems(R.array.percentuali, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int [] values={0,25,50,75,100};
                                Log.d("ciao",getResources().getStringArray(R.array.percentuali)[which]);
                                bidoni.get(position).setLivriempimento(values[which]);
                            }
                        });
                AlertDialog clicked=builder2.create();
                clicked.show();
            }
        });








    }

    private void Initialize(){
        bidoni.add(new Cestino(-1,"Indifferenziato"));
        bidoni.add(new Cestino(-1,"Carta"));
        bidoni.add(new Cestino(-1,"Plastica"));
        bidoni.add(new Cestino(-1,"Vetro"));
    }
}
