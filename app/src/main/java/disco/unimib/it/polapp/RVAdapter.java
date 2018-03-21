package disco.unimib.it.polapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by omarg on 28/12/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.BasketViewHolder> {

    private List<Trash> trashes;

    private static OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    RVAdapter(List<Trash> cestini) { this.trashes =cestini; }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        RVAdapter.mItemClickListener = mItemClickListener;
    }

    @Override
    public BasketViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview,viewGroup,false);
        BasketViewHolder phv = new BasketViewHolder(v);
        return phv;
    }

    @Override
    public void onBindViewHolder(BasketViewHolder BasketViewHolder, int i){
        Trash trash = trashes.get(i);
        BasketViewHolder.bindBasket(trash);
    }

    @Override
    public int getItemCount(){
        return trashes.size();
    }

    public static class BasketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Trash mTrash;
        private TextView tipo;
        private ImageView fotocestino;

        BasketViewHolder(View itemView){
            super(itemView);
            tipo=(TextView) itemView.findViewById(R.id.cestino_name);
            fotocestino=(ImageView) itemView.findViewById(R.id.cestino_photo);
            itemView.setOnClickListener(this);
        }

        public void bindBasket(Trash trash) {
            mTrash = trash;
            tipo.setText(mTrash.getTipo());
            if(trash.getTipo().equals(getContext().getString(R.string.cestinoindiff))){
                fotocestino.setImageResource(R.drawable.ic_delete_blue_24dp);
            }else if(trash.getTipo().equals(getContext().getString(R.string.cestinocarta))){
                fotocestino.setImageResource(R.drawable.ic_delete_red_24dp);
            }else if(trash.getTipo().equals(getContext().getString(R.string.cestinoplastica))){
                fotocestino.setImageResource(R.drawable.ic_delete_yellow_24dp);
            }else if (trash.getTipo().equals(getContext().getString(R.string.cestinovetro))){
                fotocestino.setImageResource(R.drawable.ic_delete_green_24dp);
            }



        }
        public Context getContext() {
            return itemView.getContext();
        }


        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}