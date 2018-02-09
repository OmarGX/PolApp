package disco.unimib.it.polapp;

/**
 * Created by omarg on 28/12/2017.
 */

public class Basket {
    private int mLivRiempimento;
    private String mTipo;

    public Basket(int livriempimento,String tipo){
        this.mLivRiempimento=livriempimento;
        this.mTipo=tipo;
    }

    public int getLivRiempimento() {
        return mLivRiempimento;
    }

    public String getTipo() {
        return mTipo;
    }

    public void setLivRiempimento(int livriempimento) {
        this.mLivRiempimento = livriempimento;
    }



}
