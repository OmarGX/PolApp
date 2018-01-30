package disco.unimib.it.polapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

        final RVAdapter adapter=new RVAdapter(bidoni);
        rv.setAdapter(adapter);

        final FloatingActionButton FAB_SEND=(FloatingActionButton) findViewById(R.id.fab_send);

        final AlertDialog.Builder builder2=new AlertDialog.Builder(DetectedActivity.this);





        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, final int position) {
                builder2.setTitle(bidoni.get(position).getTipo())
                        .setItems(R.array.percentuali, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int [] values={0,25,50,75,100};
                                bidoni.get(position).setLivriempimento(values[which]);
                                adapter.notifyItemChanged(position);
                            }
                        });
                AlertDialog clicked=builder2.create();
                clicked.show();
            }
        });

         final AlertDialog.Builder builder3=new AlertDialog.Builder(DetectedActivity.this);

        FAB_SEND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isComplete=true;

                for (Cestino cestino:bidoni) {
                    if(cestino.getLivriempimento()==-1){
                        isComplete=false;
                    }
                }
                if(isComplete==false){
                    builder3.setTitle(R.string.errore).
                            setMessage(R.string.testoerrore).
                            setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog error=builder3.create();
                    error.show();
                } else{
                    builder3.setTitle(R.string.invionotifica).
                            setMessage(R.string.message).
                            setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder3.setNeutralButton(R.string.problem, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent openNotify=new Intent(DetectedActivity.this, NotifyActivity.class);
                                    startActivity(openNotify);
                                }
                            });
                    AlertDialog oknotify=builder3.create();
                    oknotify.show();




                }

                
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
