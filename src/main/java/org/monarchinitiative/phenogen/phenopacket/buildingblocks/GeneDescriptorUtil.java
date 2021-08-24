package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.ga4gh.vrsatile.v1.GeneDescriptor;

public class GeneDescriptorUtil {


    private static GeneDescriptor factory(String geneSymbol, String valueId) {
        return GeneDescriptor.newBuilder().setSymbol(geneSymbol).setValueId(valueId).build();
    }


    public static GeneDescriptor pign() {
        return factory( "PIGN","NCBIGene:23556");
    }




}
