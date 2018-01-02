package disco.unimib.it.polapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omarg on 28/12/2017.
 */

public class Cestino {
    private int livriempimento;
    private String tipo;

    Cestino(int livriempimento,String tipo){
        this.livriempimento=livriempimento;
        this.tipo=tipo;
    }

    public int getLivriempimento() {
        return livriempimento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setLivriempimento(int livriempimento) {
        this.livriempimento = livriempimento;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


}
