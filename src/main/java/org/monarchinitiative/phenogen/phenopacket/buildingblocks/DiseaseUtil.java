package org.monarchinitiative.phenogen.phenopacket.buildingblocks;


import org.monarchinitiative.phenol.ontology.data.TermId;
import org.phenopackets.schema.v2.core.Disease;
import org.phenopackets.schema.v2.core.OntologyClass;

public class DiseaseUtil {



    public static Disease mcahs1() {
        TermId tid = TermId.of("OMIM:614080");
        String label = "Multiple Congenital Anomalies-hypotonia-seizures Syndrome 1";
        OntologyClass clz = OntologyClassUtil.factory(tid, label);
        return Disease.newBuilder().setTerm(clz).build();
    }

}
