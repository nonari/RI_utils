package org.nonari.fic.ri.utils.likelihood;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TfIdfSimTest {

    @Test
    public void main() {
        final Collection coll = new Collection();
        coll.add("scary green crocodile");
        coll.add("scary green big");
        coll.add("small crocodile");

        final TfIdfSim tfIdfSim = new TfIdfSim(coll);
        tfIdfSim.tfIdf("big green crocodile");
    }

}