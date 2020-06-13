package org.nonari.fic.ri.utils.likelihood;

import org.junit.jupiter.api.Test;

class TfIdfSimNormTest {

    @Test
    public void main() {
        final Collection coll = new Collection();
        coll.add("Se aproxima verano templado");
        coll.add("Se aproxima verano lluvioso");
        coll.add("Se aproxima otoño");

        final TfIdfSimNorm tfIdfSim = new TfIdfSimNorm(coll);
        tfIdfSim.tfIdf("verano templado");
    }

}