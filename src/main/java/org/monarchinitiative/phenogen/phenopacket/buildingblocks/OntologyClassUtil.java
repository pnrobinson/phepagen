package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.monarchinitiative.phenol.ontology.data.TermId;

import org.phenopackets.schema.v2.core.OntologyClass;

public class OntologyClassUtil {

    public static OntologyClass ontologyClassFactory(TermId tid, String label) {
        return OntologyClass.newBuilder().setId(tid.getValue()).setLabel(label).build();
    }

    public static OntologyClass ontologyClassFactory(String termId, String label) {
        TermId tid = TermId.of(termId);
        return OntologyClass.newBuilder().setId(tid.getValue()).setLabel(label).build();
    }

    public final static OntologyClass heterozygous = ontologyClassFactory("GENO:0000135", "heterozygous");



}
