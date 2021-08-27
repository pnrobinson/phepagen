package org.monarchinitiative.phenogen.phenopacket;

import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.IndividualUtil.defaultMaleProband;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.evidenceWithEcoAuthorStatement;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.ontologyClass;

public class CancerAML extends PhenopacketFactory {
    private static final String PHENOPACKET_ID = "arbitrary.id";
    private static final String PROBAND_ID = "proband A";



    public CancerAML() {
        MetaData metaData = MetaDataUtil.defaultCancerMetadata();
        Individual proband = defaultMaleProband(PROBAND_ID, "P8Y");
        Disease disease = Disease.newBuilder().setTerm(ontologyClass("NCIT:C3171", "Acute Myeloid Leukemia")).build();
        this.phenopacket =  Phenopacket.newBuilder()
                .setId(PHENOPACKET_ID)
                .setMetaData(metaData)
                .setSubject(proband)
                .addBiosamples(biopsy())
                .addDiseases(disease)
                .build();
    }



    private static Biosample biopsy() {
        return Biosample.newBuilder()
                .setId("SAMN05324082")
                .setIndividualId("SAMN05324082-individual")
                .setDescription("THP-1; 6 hours; DMSO; Replicate 1")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P1Y")))
                .setTaxonomy(ontologyClass("NCBITaxon:9606", "Homo sapiens"))
                .setSampledTissue(ontologyClass("UBERON:0000178", "peripheral blood"))
                .setHistologicalDiagnosis(ontologyClass("EFO:0000221", "Acute Monocytic Leukemia"))
                .addPhenotypicFeatures(PhenotypicFeature.newBuilder().setType(ontologyClass("EFO:0001253", "THP-1")).build())
                .addPhenotypicFeatures(PhenotypicFeature.newBuilder().setType(ontologyClass("BTO:0000214", "cell culture")).build())
                .addPhenotypicFeatures(PhenotypicFeature.newBuilder().setType(ontologyClass("CL:0000576", "monocyte")).build())
                .build();
    }


}
