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

    private List<Cestino> cestini;

    private static OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    RVAdapter(List<Cestino> cestini) { this.cestini=cestini; }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        RVAdapter.mItemClickListener = mItemClickListener;
    }

    @Override
    public CestinoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview,viewGroup,false);
        CestinoViewHolder phv = new CestinoViewHolder(v);
        return phv;
    }

    @Override
    public void onBindViewHolder(CestinoViewHolder cestinoViewHolder, int i){
        Cestino cestino = cestini.get(i);
        cestinoViewHolder.bindCestino(cestino);
    }

    @Override
    public int getItemCount(){
        return cestini.size();
    }

    public static class CestinoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Cestino  mCestino;

        private CardView cv;
        private TextView tipo;
        private TextView indicazioni;
        private ImageView fotocestino;

        CestinoViewHolder(View itemView){
            super(itemView);
            cv=(CardView) itemView.findViewById(R.id.cv);
            tipo=(TextView) itemView.findViewById(R.id.cestino_name);
            indicazioni=(TextView) itemView.findViewById(R.id.indicazioni);
            fotocestino=(ImageView) itemView.findViewById(R.id.cestino_photo);
            itemView.setOnClickListener(this);
        }

        public void bindCestino(Cestino cestino) {
            mCestino = cestino;

            tipo.setText(mCestino.getTipo());
            indicazioni.setText("clicca per inserire il livello di riempimento");
            fotocestino.setImageResource(R.drawable.ic_delete_black_24dp);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}