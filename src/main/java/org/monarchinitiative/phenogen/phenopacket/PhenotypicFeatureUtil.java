package org.monarchinitiative.phenogen.phenopacket;

import org.phenopackets.schema.v2.core.Evidence;
import org.phenopackets.schema.v2.core.OntologyClass;
import org.phenopackets.schema.v2.core.PhenotypicFeature;
import org.phenopackets.schema.v2.core.TimeElement;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil.ontologyClassFactory;

public class PhenotypicFeatureUtil {

    public static OntologyClass fetalOnset = ontologyClassFactory("HP:0011461", "Fetal onset");


    public static PhenotypicFeature phenotypicFeatureWithEvidence(OntologyClass clz, Evidence evidence) {
        return PhenotypicFeature
                .newBuilder()
                .setType(clz)
                .addEvidence(evidence)
                .build();
    }

    public static PhenotypicFeature fetalOnsetFeature(OntologyClass clz, Evidence evidence) {
        TimeElement timeElement = TimeElement.newBuilder().setOntologyClass(fetalOnset).build();
        return PhenotypicFeature
                .newBuilder()
                .setType(clz)
                .setOnset(timeElement)
                .addEvidence(evidence)
                .build();
    }

}
