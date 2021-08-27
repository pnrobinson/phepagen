package org.monarchinitiative.phenogen.phenopacket;

import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.Evidence;
import org.phenopackets.schema.v2.core.OntologyClass;
import org.phenopackets.schema.v2.core.PhenotypicFeature;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil.ontologyClassFactory;

public abstract class PhenopacketFactory {

    /** version 2 phenopacket for the disease MCAHS1 */
    protected Phenopacket phenopacket;

    public Phenopacket phenopacket() {
        return this.phenopacket;
    }

    /**
     * Convenience function
     * @param cls
     * @param authorAssertion
     * @return
     */
    protected PhenotypicFeature phenotypicFeature(OntologyClass cls, Evidence authorAssertion) {
        return PhenotypicFeature.newBuilder().addEvidence(authorAssertion).setType(cls).build();
    }

    protected PhenotypicFeature phenotypicFeature(String termId, String label, Evidence authorAssertion) {
        OntologyClass clz = ontologyClassFactory(termId, label);
        return PhenotypicFeature.newBuilder().setType(clz).addEvidence(authorAssertion).build();
    }

    protected PhenotypicFeature phenotypicFeature(String termId, String label) {
        OntologyClass clz = ontologyClassFactory(termId, label);
        return PhenotypicFeature.newBuilder().setType(clz).build();
    }
}
