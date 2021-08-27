package org.monarchinitiative.phenogen.phenopacket;

import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.IndividualUtil.defaultMaleProband;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.ontologyClass;

public class CancerSquamousCell extends PhenopacketFactory {
    private static final String PHENOPACKET_ID = "arbitrary.id";
    private static final String PROBAND_ID = "proband A";

    public CancerSquamousCell(){
        MetaData metaData = MetaDataUtil.defaultCancerMetadata();
        Individual proband = defaultMaleProband(PROBAND_ID, "P38Y");
        this.phenopacket =  Phenopacket.newBuilder()
                .setId(PHENOPACKET_ID)
                .setMetaData(metaData)
                .setSubject(proband)
                .addBiosamples(esophagusBiopsy())
                .addBiosamples(lymphNodeBiopsy())
                .addBiosamples(lungBiopsy())
                .addDiseases(esophagealCarcinoma())
                .build();
    }


    private Disease esophagealCarcinoma() {
        return Disease.newBuilder()
                .setTerm(ontologyClass("NCIT:C4024","Esophageal Squamous Cell Carcinoma"))
                .addClinicalTnmFinding(ontologyClass("NCIT:C48724", "T2 Stage Finding"))
                .addClinicalTnmFinding(ontologyClass("NCIT:C48706", "N1 Stage Finding"))
                .addClinicalTnmFinding(ontologyClass("NCIT:C48699", "M0 Stage Finding"))
                .build();
    }



    private Biosample esophagusBiopsy () {
        return Biosample.newBuilder()
                .setIndividualId(PROBAND_ID)
                .setId("sample2")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P49Y2M")))
                .setSampledTissue((ontologyClass("NCIT:C12389", "Esophagus")))
                //recurrence sample, esophagus (biopsy)
                .setTumorProgression(ontologyClass("NCIT:C4813", "Recurrent Malignant Neoplasm"))
                .setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build())
                .build();
    }

    private Biosample lymphNodeBiopsy() {
        return Biosample.newBuilder()
                .setIndividualId(PROBAND_ID)
                .setId("sample1")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P48Y3M")))
                //Biosample (lymph node biopsy)
                .setSampledTissue(ontologyClass("NCIT:C139196", "Esophageal Lymph Node"))
                .setTumorProgression(ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm"))
                //diagnosis: Squamous cell carcinoma of the esophagus, T2N1M0
                .setHistologicalDiagnosis(ontologyClass("NCIT:C4024", "Esophageal Squamous Cell Carcinoma"))
                //HPV-18 positive (cancer tissue)
                .addDiagnosticMarkers(ontologyClass("NCIT:C131711", "Human Papillomavirus-18 Positive"))
                .setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build())
                // diagnostic sample (tumor resection)
                .build();
    }

    private Biosample lungBiopsy() {
       return Biosample.newBuilder()
                .setIndividualId(PROBAND_ID)
                .setId("sample3")
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P50Y7M")))
                .setSampledTissue(ontologyClass("NCIT:C12468", "Lung"))
                .setTumorProgression(ontologyClass("NCIT:C3261", "Metastatic Neoplasm"))
                .setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build())
                .build();
    }

}
