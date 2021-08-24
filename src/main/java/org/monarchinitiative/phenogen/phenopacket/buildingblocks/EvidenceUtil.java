package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.monarchinitiative.phenol.ontology.data.TermId;
import org.phenopackets.schema.v2.core.Evidence;
import org.phenopackets.schema.v2.core.ExternalReference;
import org.phenopackets.schema.v2.core.OntologyClass;

public class EvidenceUtil {



    public static Evidence authorStatementEvidence(String pmid, String title) {
        TermId tid = TermId.of("ECO:0000033");
        String label = "author statement supported by traceable reference";
        OntologyClass evidenceCode = OntologyClass.newBuilder().setId(tid.getValue()).setLabel(label).build();
        return Evidence.newBuilder().
                setReference(ExternalReference.newBuilder().
                        setId(pmid).
                        setDescription(title).
                        build()).
                setEvidenceCode(evidenceCode)
                .build();
    }

}
