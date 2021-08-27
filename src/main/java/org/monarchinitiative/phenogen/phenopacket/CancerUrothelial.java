package org.monarchinitiative.phenogen.phenopacket;

import com.google.protobuf.Timestamp;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;

import java.time.Instant;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.ontologyClass;

public class CancerUrothelial extends PhenopacketFactory {
    private static final String PHENOPACKET_ID = "arbitrary.id";
    private static final String PROBAND_ID = "patient1";
    private final String AGE_AT_BIOPSY = "P52Y2M";

    public CancerUrothelial() {
        MetaData metaData =  MetaDataUtil.defaultCancerMetadata();
        PhenotypicFeature hematuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000790","Hematuria"))
                .build();
        PhenotypicFeature dsyuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0100518","Dysuria"))
                .setSeverity(ontologyClass("HP:0012828","Severe"))
                .build();




        this.phenopacket = Phenopacket.newBuilder()
                .setId("example case")
                .setSubject(subject())
                .addPhenotypicFeatures(hematuria)
                .addPhenotypicFeatures(dsyuria)
                .addBiosamples(bladderBiosample())
                .addBiosamples(prostateBiosample())
                .addBiosamples(leftUreterBiosample())
                .addBiosamples(rightUreterBiosample())
                .addBiosamples(pelvicLymphNodeBiosample())
                .addDiseases(infiltratingUrothelialCarcinoma())
                .addFiles(createNormalGermlineHtsFile())
                .setMetaData(metaData)
                .build();
    }

    public File createNormalGermlineHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        return File.newBuilder()
                .setUri("file://data/genomes/germline_wgs.vcf.gz")
                .putIndividualToFileIdentifiers("example case", "NA12345")
                .putFileAttributes("genomeAssembly", "GRCh38")
                .putFileAttributes("fileFormat", "vcf")
                .putFileAttributes("description", "Matched normal germline sample")
                .build();
    }






    private Disease infiltratingUrothelialCarcinoma() {
        return Disease.newBuilder()
                .setTerm(ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma"))
                // Disease stage here is calculated based on the TMN findings
                .addDiseaseStage(ontologyClass("NCIT:C27971", "Stage IV"))
                // The tumor was staged as pT2b, meaning infiltration into the outer muscle layer of the bladder wall
                // pT2b Stage Finding (Code C48766)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48766", "pT2b Stage Finding"))
                //pN2 Stage Finding (Code C48750)
                // cancer has spread to 2 or more lymph nodes in the true pelvis (N2)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48750", "pN2 Stage Finding"))
                // M1 Stage Finding
                // the tumour has spread from the original site (Metastatic Neoplasm in lymph node - sample5)
                .addClinicalTnmFinding(ontologyClass("NCIT:C48700", "M1 Stage Finding"))
                .build();
    }

    private Individual subject() {
        return Individual.newBuilder()
                .setId(PROBAND_ID)
                .setSex(Sex.MALE)
                .setDateOfBirth(Timestamp.newBuilder()
                        .setSeconds(Instant.parse("1964-03-15T00:00:00Z").getEpochSecond()))
                .build();
    }


    private Biosample prostateBiosample() {
        String sampleId = "sample2";
        //prostate
        OntologyClass sampleType = ontologyClass("UBERON:0002367", "prostate gland");
        Biosample.Builder biosampleBuilder = biosampleBuilder(PROBAND_ID, sampleId, AGE_AT_BIOPSY, sampleType);
        OntologyClass prostateAcinarAdenocarcinoma = ontologyClass("NCIT:C5596", "Prostate Acinar Adenocarcinoma");
        biosampleBuilder.setHistologicalDiagnosis(prostateAcinarAdenocarcinoma);

        // A primary malignant neoplasm in a patient who has been already diagnosed with a primary malignant neoplasm in another anatomic site.
        OntologyClass secondary = ontologyClass("NCIT:C95606", "Second Primary Malignant Neoplasm");
        biosampleBuilder.setTumorProgression(secondary);

        // The Gleason scoring system is used to grade prostate cancer (1). The Gleason score is based on biopsy samples taken from the prostate.
        // Gleason 7: The tumor tissue is moderately differentiated
        OntologyClass gleason7 = ontologyClass("NCIT:C28091", "Gleason Score 7");
        biosampleBuilder.setTumorGrade(gleason7);
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build());
        return biosampleBuilder.build();
    }

    private Biosample leftUreterBiosample() {
        String sampleId = "sample3";
        OntologyClass sampleType = ontologyClass("UBERON:0001223", "left ureter");
        Biosample.Builder biosampleBuilder = biosampleBuilder(PROBAND_ID, sampleId, AGE_AT_BIOPSY, sampleType);
        OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");
        biosampleBuilder.setHistologicalDiagnosis(normalFinding);
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build());
        return biosampleBuilder.build();
    }

    private Biosample rightUreterBiosample() {
        String sampleId = "sample4";
        OntologyClass sampleType = ontologyClass("UBERON:0001222", "right ureter");
        Biosample.Builder biosampleBuilder = biosampleBuilder(PROBAND_ID, sampleId, AGE_AT_BIOPSY, sampleType);
        OntologyClass normalFinding = ontologyClass("NCIT:C38757", "Negative Finding");
        biosampleBuilder.setHistologicalDiagnosis(normalFinding);
        return biosampleBuilder.build();
    }





    private Biosample.Builder biosampleBuilder(String patientId, String sampleId, String age, OntologyClass sampleType) {
        return Biosample.newBuilder()
                .setIndividualId(patientId)
                .setId(sampleId)
                .setTimeOfCollection(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration(age)).build())
                .setSampledTissue(sampleType);
    }

    public File createSomaticHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        // Now create a File object
        return File.newBuilder()
                .setUri("file://data/genomes/urothelial_ca_wgs.vcf.gz")
                .putIndividualToFileIdentifiers("sample1", "BS342730")
                .putFileAttributes("genomeAssembly", "GRCh38")
                .putFileAttributes("fileFormat", "vcf")
                .putFileAttributes("description", "Urothelial carcinoma sample")
                .build();
    }


    public File createMetastasisHtsFile() {
        // first create a File
        // We are imagining there is a reference to a VCF file for a normal germline genome seqeunce
        return File.newBuilder()
                .setUri("file://data/genomes/metastasis_wgs.vcf.gz")
                .putIndividualToFileIdentifiers("sample5", "BS730275")
                .putFileAttributes("genomeAssembly", "GRCh38")
                .putFileAttributes("fileFormat", "vcf")
                .putFileAttributes("description", "lymph node metastasis sample")
                .build();
    }

    private Biosample bladderBiosample() {
        String sampleId = "sample1";
        // left wall of urinary bladder
        OntologyClass sampleType = ontologyClass("UBERON_0001256", "wall of urinary bladder");
        Biosample.Builder biosampleBuilder = biosampleBuilder(PROBAND_ID, sampleId, AGE_AT_BIOPSY, sampleType);
        // also want to mention the procedure, Prostatocystectomy (NCIT:C94464)
        //Infiltrating Urothelial Carcinoma (Code C39853)
        biosampleBuilder.setHistologicalDiagnosis(ontologyClass("NCIT:C39853", "Infiltrating Urothelial Carcinoma"));
        // A malignant tumor at the original site of growth
        biosampleBuilder.setTumorProgression(ontologyClass("NCIT:C84509", "Primary Malignant Neoplasm"));
        biosampleBuilder.addFiles(createSomaticHtsFile());
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C5189", "Radical Cystoprostatectomy")).build());
        return biosampleBuilder.build();
    }

    private Biosample pelvicLymphNodeBiosample() {
        String sampleId = "sample5";
        OntologyClass sampleType = ontologyClass("UBERON:0015876", "pelvic lymph node");
        Biosample.Builder biosampleBuilder = biosampleBuilder(PROBAND_ID, sampleId, AGE_AT_BIOPSY, sampleType);
        OntologyClass metastasis = ontologyClass("NCIT:C3261", "Metastatic Neoplasm");
        biosampleBuilder.setTumorProgression(metastasis);
        biosampleBuilder.addFiles(createMetastasisHtsFile());
        biosampleBuilder.setProcedure(Procedure.newBuilder().setCode(ontologyClass("NCIT:C15189", "Biopsy")).build());
        return biosampleBuilder.build();
    }

}
