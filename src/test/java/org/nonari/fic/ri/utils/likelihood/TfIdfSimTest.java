package org.nonari.fic.ri.utils.likelihood;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TfIdfSimTest {

    @Test
    public void main() {
        final Collection coll = new Collection();
        coll.add("Revenue down by the second semester");
        coll.add("Revenue down by the second semester");

        final TfIdfSim tfIdfSim = new TfIdfSim(coll);
        tfIdfSim.tfIdf("revenue down");
    }

}