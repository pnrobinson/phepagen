package org.monarchinitiative.phenogen.phenopacket;

import org.monarchinitiative.phenogen.phenopacket.buildingblocks.ExternalReferenceUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;



import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.IndividualUtil.defaultMaleProband;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil.*;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.evidenceWithEcoAuthorStatement;

public class RareDiseaseMCAHS1 {
    private static final String PHENOPACKET_ID = "id-A";
    private static final String PROBAND_ID = "proband A";
    private static final String PMID = "PMID:30808312";
    private static final String publication = "COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report";
    private static final Evidence authorAssertion = evidenceWithEcoAuthorStatement(PMID, publication);

    /** version 2 phenopacket for the disease MCAHS1 */
    Phenopacket phenopacket;

    private RareDiseaseMCAHS1() {
        ExternalReference citation = ExternalReferenceUtil.externalReference(PMID, publication);
        MetaData metaData = MetaDataUtil.defaultRareDiseaseMetaData(citation);
        Individual proband = defaultMaleProband(PROBAND_ID, "P8D");
        phenopacket = Phenopacket.newBuilder()
                .setId(PHENOPACKET_ID)
                .setSubject(proband)
                .addPhenotypicFeatures(phenotypicFeature(ventricularSeptalDefect()))
                .addPhenotypicFeatures(phenotypicFeature(coarseFacialFeatures()))
                .addPhenotypicFeatures(phenotypicFeature(bilateralCryptorchidism()))
                .addPhenotypicFeatures(phenotypicFeature(polyhydramnios()))
                .addPhenotypicFeatures(phenotypicFeature(micropenis()))
                .addPhenotypicFeatures(phenotypicFeature(anonychia()))
                .addPhenotypicFeatures(phenotypicFeature(cerebellarVermisHypoplasia()))
                .addPhenotypicFeatures(phenotypicFeature(cataract()))
                .addPhenotypicFeatures(phenotypicFeature(dilatedFourthVentricle()))
                .addPhenotypicFeatures(phenotypicFeature(unilateralCleftLip()))
                .setMetaData(metaData)
                .build();
    }

    private PhenotypicFeature phenotypicFeature(OntologyClass cls) {
        return PhenotypicFeature.newBuilder().addEvidence(authorAssertion).setType(cls).build();
    }

    public static Phenopacket phenopacket() {
        RareDiseaseMCAHS1 mcahs1 = new RareDiseaseMCAHS1();
        return mcahs1.phenopacket;
    }



}
