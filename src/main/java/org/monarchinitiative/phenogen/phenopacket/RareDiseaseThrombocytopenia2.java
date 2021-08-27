package org.monarchinitiative.phenogen.phenopacket;

import org.monarchinitiative.phenogen.phenopacket.buildingblocks.ExternalReferenceUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.IndividualUtil.defaultMaleProband;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.InterpretationUtil.pathogenicHeterozygousHgvsInterpretation;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.MeasurementUtil.lowPlatelets;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.evidenceWithEcoAuthorStatement;

public class RareDiseaseThrombocytopenia2 extends PhenopacketFactory{
    private static final String PHENOPACKET_ID = "id-B";
    private static final String PROBAND_ID = "family 10 proband";
    private static final String PMID = "PMID:21211618";
    private static final String publication = "Mutations in the 5' UTR of ANKRD26, the ankirin repeat domain 26 gene, cause an autosomal-dominant form of inherited thrombocytopenia, THC2";
    private static final Evidence authorAssertion = evidenceWithEcoAuthorStatement(PMID, publication);
    private static final OntologyClass disease = OntologyClassUtil.ontologyClassFactory("OMIM:188000", "Thrombocytopenia 2");
    private static final String hgvsExpression = "NM_014915.2:c.-128G>A";



    public RareDiseaseThrombocytopenia2() {
        ExternalReference citation = ExternalReferenceUtil.externalReference(PMID, publication);
        MetaData metaData = MetaDataUtil.defaultRareDiseaseMetaData(citation);
        Individual proband = defaultMaleProband(PROBAND_ID, "P20Y");
        Interpretation interpretation = pathogenicHeterozygousHgvsInterpretation(disease, "interpretation.id.2", PROBAND_ID, hgvsExpression);
        phenopacket = Phenopacket.newBuilder()
                .setId(PHENOPACKET_ID)
                .setSubject(proband)
                .addMeasurements(lowPlatelets())
                .addPhenotypicFeatures(excludedAbnormalPlateletSize())
                .addPhenotypicFeatures(brusing())
                .addInterpretations(interpretation)
                .setMetaData(metaData)
                .build();
    }



    private PhenotypicFeature excludedAbnormalPlateletSize() {
        OntologyClass clz = OntologyClassUtil.ontologyClassFactory("HP:0011876", "Abnormal platelet volume");
        return PhenotypicFeature
                .newBuilder()
                .setType(clz)
                .setExcluded(true)
                .addEvidence(authorAssertion)
                .build();
    }

    private PhenotypicFeature brusing() {
        OntologyClass clz = OntologyClassUtil.ontologyClassFactory("HP:0000978", "Bruising susceptibility");
        return PhenotypicFeature.newBuilder().addEvidence(authorAssertion).setType(clz).build();
    }



}
