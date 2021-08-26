package org.monarchinitiative.phenogen.phenopacket;

import org.monarchinitiative.phenogen.phenopacket.buildingblocks.ExternalReferenceUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;



import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.IndividualUtil.defaultMaleProband;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.InterpretationUtil.pathogenicHeterozygousHgvsInterpretation;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil.*;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.evidenceWithEcoAuthorStatement;

/**
 * Adapted from Bao M, et al. COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report.
 * BMC Neurol. 2019 Feb 26;19(1):32.
 * PMID:30808312.
 * NM_001848.2(COL6A1):c.877G>A (p.Gly293Arg) Allele ID: 99797
 *
 */
public class RareDiseaseMCAHS1 {
    private static final String PHENOPACKET_ID = "id-A";
    private static final String PROBAND_ID = "proband A";
    private static final String PMID = "PMID:30808312";
    private static final String publication = "COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report";
    private static final Evidence authorAssertion = evidenceWithEcoAuthorStatement(PMID, publication);
    private static final OntologyClass disease = OntologyClassUtil.factory("OMIM:158810", "Bethlem myopathy 1");
    private static final String hgvsExpression = "NM_001848.2:c.877G>A";

    /** version 2 phenopacket for the disease MCAHS1 */
    final Phenopacket phenopacket;

    private RareDiseaseMCAHS1() {
        ExternalReference citation = ExternalReferenceUtil.externalReference(PMID, publication);
        MetaData metaData = MetaDataUtil.defaultRareDiseaseMetaData(citation);
        Individual proband = defaultMaleProband(PROBAND_ID, "P8D");
       Interpretation interpretation = pathogenicHeterozygousHgvsInterpretation(disease, "interpretation.id.1", PROBAND_ID, hgvsExpression);
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
                .addInterpretations(interpretation)
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
