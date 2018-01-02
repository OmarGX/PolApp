package disco.unimib.it.polapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.TextRecognizer;

import java.util.List;

/**
 * Created by omarg on 28/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CestinoViewHolder> {


    public static class CestinoViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tipo;
        TextView indicazioni;
        static ImageView fotocestino;


        CestinoViewHolder(View itemView){
            super(itemView);
            cv=(CardView) itemView.findViewById(R.id.cv);
            tipo=(TextView) itemView.findViewById(R.id.cestino_name);
            indicazioni=(TextView) itemView.findViewById(R.id.indicazioni);
            fotocestino=(ImageView) itemView.findViewById(R.id.cestino_photo);


        }





    }
    List<Cestino> cestini;

    RVAdapter(List<Cestino> cestini){
        this.cestini=cestini;
    }

    @Override
    public int getItemCount(){
        return cestini.size();
    }

    @Override
    public CestinoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview,viewGroup,false);
        CestinoViewHolder phv=new CestinoViewHolder(v);
        return phv;
    }

    @Override
    public void onBindViewHolder(CestinoViewHolder cestinoViewHolder, int i){
        cestinoViewHolder.tipo.setText(cestini.get(i).getTipo());
        cestinoViewHolder.indicazioni.setText("clicca per inserire il livello di riempimento");
        CestinoViewHolder.fotocestino.setImageResource(R.drawable.ic_delete_black_24dp);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


}
