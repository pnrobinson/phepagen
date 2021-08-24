package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.ga4gh.vrsatile.v1.GeneDescriptor;
import org.monarchinitiative.phenol.ontology.data.TermId;
import org.phenopackets.schema.v2.core.OntologyClass;

public class GeneDescriptorBuilder {


    private static GeneDescriptor factory(String geneSymbol, String valueId) {
        return GeneDescriptor.newBuilder().setSymbol(geneSymbol).setValueId(valueId).build();
    }


    public static GeneDescriptor pign() {
        return factory( "PIGN","NCBIGene:23556");
    }




}
