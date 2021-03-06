package org.nonari.fic.ri.utils.likelihood;

import org.junit.jupiter.api.Test;

public class DirechletTest {

    @Test
    public void main() {
        final Collection coll = new Collection();
        coll.add("Tempus reports a profit but revenue is down");
        coll.add("Quorus Global narrows quarter loss but revenue decreases further");

        final Dirichlet tfIdfSim = new Dirichlet(coll);
        tfIdfSim.calc("revenue down", 0.5F, 0);
    }

}
