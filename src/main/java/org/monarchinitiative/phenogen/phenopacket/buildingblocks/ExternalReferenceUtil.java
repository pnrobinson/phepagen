package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.phenopackets.schema.v2.core.ExternalReference;

public class ExternalReferenceUtil {


    public static ExternalReference externalReference(String id, String description) {
        return ExternalReference.newBuilder().setId(id).setDescription(description).build();
    }

}
