package org.monarchinitiative.phenogen.phenopacket;

import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil.ontologyClassFactory;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.evidenceWithEcoAuthorStatement;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.PhenopacketUtil.ontologyClass;

/**
 *  https://casereports.onlinejacc.org/content/early/2020/05/21/j.jaccas.2020.04.001
 *  His medical history included ischemic cardiomyopathy, stage 3 chronic kidney disease, and obesity.
 *  // His post-LVAD complications include gastrointestinal bleeding, ventricular tachycardia, and right ventricular
 *         // (RV) dysfunction but no infectious complications.
 *         // He did not have diabetes or use tobacco.
 */
public class CovidExample extends PhenopacketFactory {

    private static final Disease cardiomyopathy = Disease.newBuilder().setTerm(ontologyClass("MONDO:0004994", "cardiomyopathy")).build();
    private static final OntologyClass stage3kidney = ontologyClassFactory("HP:0012625", "Stage 3 chronic kidney disease");
    private static final OntologyClass obesity = ontologyClassFactory("HP:0001513", "Obesity");
    private static final OntologyClass giBleeding = ontologyClassFactory("HP:0002239", "Gastrointestinal hemorrhage");
    private static final OntologyClass vtach = ontologyClassFactory("HP:0004756", "Ventricular tachycardia");
    private static final OntologyClass rvFailure = ontologyClassFactory("HP:0001708", "Right ventricular failure");
    private static final Disease notDiabetesMellitus = Disease.newBuilder()
            .setTerm(ontologyClass("MONDO:0005015", "diabetes mellitus"))
            .setExcluded(true)
            .build();


    private static final String PMID = "PMID: 32292915";
    private static final String publication = "The Imperfect Cytokine Storm: Severe COVID-19 With ARDS in a Patient on Durable LVAD Support";
    private static final Evidence authorAssertion = evidenceWithEcoAuthorStatement(PMID, publication);


    public CovidExample() {
        TimeElement initialSymptomsTime = parseLocalDate("2020-03-17");
        // 70-year-old male with a destination therapy HeartMate 3 (Abbott Laboratory, Lake Bluff, Illinois)
        // left ventricular assist device (LVAD) implanted in 2016 who developed fever, flank pain, and hematuria 3 days
        // after attending a party
        // His blood group was type A positive.
        PhenotypicFeature bloodGroupA = phenotypicFeature("HP:0032370", "Blood group A");
        PhenotypicFeature rhesusPositive = phenotypicFeature("NCIT:C76251", "Rh Positive Blood Group");
        PhenotypicFeature obesityPhenotype = PhenotypicFeature.newBuilder()
                .setType(obesity)
                .setOnset(initialSymptomsTime)
                .build();


        Individual patient = Individual.newBuilder().setId("P123542")
                .setSex(Sex.MALE)
                .setTimeAtLastEncounter(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration("P70Y")))
                .setVitalStatus(VitalStatus.newBuilder()
                        .setStatus(VitalStatus.Status.DECEASED)
                        .setTimeOfDeath(parseLocalDate("2020-03-28"))
                        .setCauseOfDeath(ontologyClass("MONDO:0100096", "COVID-19")))
                .build();

        MedicalAction lvadImplant = MedicalAction.newBuilder()
                .setProcedure(Procedure.newBuilder()
                        .setCode(ontologyClass("NCIT:C80473", "Left Ventricular Assist Device"))
                        .setPerformed(parseLocalDate("2016-01-01"))
                        .build())
                .build();
        PhenotypicFeature fever = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0001945", "Fever "))
                .setOnset(initialSymptomsTime)
                .build();
        PhenotypicFeature flankPain = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0030157", "Flank pain"))
                .setOnset(initialSymptomsTime)
                .build();
        PhenotypicFeature hematuria = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0000790", "Hematuria"))
                .setOnset(initialSymptomsTime)
                .build();
        PhenotypicFeature renalFailureStage3 = PhenotypicFeature.newBuilder()
                .setType(stage3kidney)
                .setOnset(initialSymptomsTime)
                .build();

        // He was tested for coronavirus disease 2019 (COVID-19), but he left against medical advice.
        // In the ensuing days, he continued to have fever, new onset myalgia, diarrhea, and dyspnea.
        TimeElement preHospitalisationDateRange = parseLocalDateRange("2020-03-18", "2020-03-20");

        PhenotypicFeature myalgia = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0003326", "Myalgia"))
                .setOnset(preHospitalisationDateRange)
                .build();

        PhenotypicFeature diarrhea = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0002014", "Diarrhea"))
                .setOnset(preHospitalisationDateRange)
                .build();

        PhenotypicFeature dyspnea = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("HP:0002094", "Dyspnea"))
                .setOnset(preHospitalisationDateRange)
                .build();

        // He returned to the emergency department and was in acute hypoxic respiratory failure requiring
        // supplemental oxygen to maintain peripheral oxygen saturation ≥94%.
        TimeElement returnToHospitalTime = parseLocalDate("2020-03-20");

        Disease covid19 = Disease.newBuilder()
                .setTerm(ontologyClass("MONDO:0100096", "COVID-19"))
                .setOnset(initialSymptomsTime)
                .build();

        PhenotypicFeature acuteRespiratoryFailure = PhenotypicFeature.newBuilder()
                .setType(ontologyClass("MONDO:0001208", "acute respiratory failure"))
                .setOnset(returnToHospitalTime)
                .build();

        MedicalAction nasalOxygenAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C722", "Oxygen"))
                        .setRouteOfAdministration(ontologyClass("NCIT:C38284", "Nasal Route of Administration"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-20", "2020-03-22"))
                                .setQuantity(quantityOf(2, ontologyClass("NCIT:C67388", "Liter per Minute")))
                                .build())
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-22", "2020-03-23"))
                                .setQuantity(quantityOf(50, ontologyClass("NCIT:C67388", "Liter per Minute")))
                                .build()))
                .build();

        MedicalAction hydroxychloroquineAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C557", "Hydroxychloroquine"))
                        // This was not specified in the original report, however we used these
                        // https://www.covid19treatmentguidelines.nih.gov/antiviral-therapy/chloroquine-or-hydroxychloroquine-with-or-without-azithromycin/
                        // 450 mg twice daily for 1 day, followed by 450 mg once daily for 4 days
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setQuantity(quantityOf(450, ontologyClass("NCIT:C28253", "mg")))
                                .setScheduleFrequency(ontologyClass("NCIT:C64496", "Twice Daily"))
                                .setInterval(parseLocalDateInterval("2020-03-20", "2020-03-20"))
                                .build())
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setQuantity(quantityOf(450, ontologyClass("NCIT:C28253", "mg")))
                                .setScheduleFrequency(ontologyClass("NCIT:C125004", "Once Daily"))
                                .setInterval(parseLocalDateInterval("2020-03-21", "2020-03-22"))
                                .build())
                        .build())
                .build();

        MedicalAction trachealIntubation = MedicalAction.newBuilder()
                .setProcedure(Procedure.newBuilder()
                        .setCode(ontologyClass("NCIT:C116648", "Tracheal Intubation"))
                        .setPerformed(parseLocalDate("2020-03-22"))
                        .build())
                .build();

        MedicalAction peepOxygenAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C722", "Oxygen"))
                        .setRouteOfAdministration(ontologyClass("NCIT:C50254", "Positive end Expiratory Pressure Valve Device"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-22", "2020-03-28"))
                                .setQuantity(quantityOf(14, ontologyClass("NCIT:C91060", "Centimeters of Water")))
                                .build()))
                .build();

        MedicalAction.Builder tocilizumabAdministered = MedicalAction.newBuilder()
                .setTreatment(Treatment.newBuilder()
                        .setAgent(ontologyClass("NCIT:C84217", "Tocilizumab"))
                        .addDoseIntervals(DoseInterval.newBuilder()
                                .setInterval(parseLocalDateInterval("2020-03-24", "2020-03-28"))
                                .build())
                        .build());

        Measurement initialBloodLymphocyteCount = Measurement.newBuilder()
                .setAssay(ontologyClass("NCIT:C113237", "Absolute Blood Lymphocyte Count"))
                .setValue(Value.newBuilder()
                        .setQuantity(Quantity.newBuilder()
                                .setUnit(ontologyClass("NCIT:C67245", "Thousand Cells"))
                                .setValue(1.4)
                                .setReferenceRange(ReferenceRange.newBuilder().setUnit(ontologyClass("NCIT:C67245", "Thousand Cells")).setHigh(4.5).setLow(1.0)))
                )
                .setTimeObserved(TimeElement.newBuilder().setInterval(TimeInterval.newBuilder().setStart(parseIsoLocalDate("2019-09-01")).setEnd(parseIsoLocalDate("2020-03-01"))))
                .build();

        Measurement hoD0bloodLymphocyteCount = Measurement.newBuilder()
                .setAssay(ontologyClass("LOINC:26474-7", "Lymphocytes [#/volume] in Blood"))
                .setValue(Value.newBuilder()
                        .setQuantity(Quantity.newBuilder()
                                .setUnit(ontologyClass("NCIT:C67245", "Thousand Cells"))
                                .setValue(0.7)
                                .setReferenceRange(ReferenceRange.newBuilder().setUnit(ontologyClass("NCIT:C67245", "Thousand Cells")).setHigh(4.5).setLow(1.0)))
                )
                .setTimeObserved(returnToHospitalTime)
                .build();

        /*
        $.metaData.created: is missing but it is required
	$.metaData.createdBy: is missing but it is required
	$.metaData.resources[0].iriPrefix: is missing but it is required
	$.metaData.resources[1].version: is missing but it is required
	$.metaData.resources[1].iriPrefix: is missing but it is required
	$.metaData.resources[2].version: is missing but it is required
	$.metaData.resources[2].iriPrefix: is missing but it is required
	$.metaData.resources[3].version: is missing but it is required
	$.metaData.resources[3].iriPrefix: is missing but it is required
	$.medicalActions[0].action: is missing but it is required
	$.medicalActions[0].procedure: is not defined in the schema and the schema does not allow additional properties
	$.medicalActions[1].action: is missing but it is required
	$.medicalActions[1].treatment: is not defined in the schema and the schema does not allow additional properties
	$.medicalActions[2].action: is missing but it is required
	$.medicalActions[2].treatment: is not defined in the schema and the schema does not allow additional properties
	$.medicalActions[3].action: is missing but it is required
	$.medicalActions[3].procedure: is not defined in the schema and the schema does not allow additional properties
	$.medicalActions[4].action: is missing but it is required
	$.medicalActions[4].treatment: is not defined in the schema and the schema does not allow additional properties
	$.medicalActions[5].action: is missing but it is required
	$.medicalActions[5].treatment: is not defined in the sc
         */



        phenopacket = Phenopacket.newBuilder()
                .setId("arbitrary.phenopacket.id")
                .setMetaData(covidMetaData())
                .setSubject(patient)
                .addPhenotypicFeatures(bloodGroupA)
                .addPhenotypicFeatures(rhesusPositive)
                .addPhenotypicFeatures(fever)
                .addPhenotypicFeatures(flankPain)
                .addPhenotypicFeatures(hematuria)
                .addPhenotypicFeatures(myalgia)
                .addPhenotypicFeatures(diarrhea)
                .addPhenotypicFeatures(dyspnea)
                .addPhenotypicFeatures(acuteRespiratoryFailure)
                .addMeasurements(initialBloodLymphocyteCount)
                .addMeasurements(hoD0bloodLymphocyteCount)
                .addMedicalActions(lvadImplant)
                .addMedicalActions(nasalOxygenAdministered)
                .addMedicalActions(hydroxychloroquineAdministered)
                .addMedicalActions(trachealIntubation)
                .addMedicalActions(peepOxygenAdministered)
                .addMedicalActions(tocilizumabAdministered)
                .addDiseases(notDiabetesMellitus)
                .addDiseases(cardiomyopathy)
                .addPhenotypicFeatures(renalFailureStage3)
                .addPhenotypicFeatures(obesityPhenotype)
                .addDiseases(covid19)
                .build();
    }


    private MetaData covidMetaData() {
        Timestamp curationTIme = parseIsoLocalDate("2021-08-17");
        Resource ncit = Resource.newBuilder()
                .setId("ncit")
                .setName("NCI Thesaurus OBO Edition")
                .setUrl("http://purl.obolibrary.org/obo/ncit.owl")
                .setVersion("http://purl.obolibrary.org/obo/ncit/releases/2019-11-26/ncit.owl")
                .setIriPrefix("http://purl.obolibrary.org/obo/NCIT_")
                .setNamespacePrefix("NCIT")
                .build();

        Resource mondo = Resource.newBuilder()
                .setId("mondo")
                .setName("Mondo Disease Ontology")
                .setUrl("http://purl.obolibrary.org/obo/mondo.obo")
                .setVersion("http://purl.obolibrary.org/obo/mondo/releases/2021-11-26/mondo.owl")
                .setIriPrefix("http://purl.obolibrary.org/obo/MONDO_")
                .setNamespacePrefix("MONDO")
                .build();

        ExternalReference article = ExternalReference.newBuilder()
                .setId("DOI:10.1016/j.jaccas.2020.04.001")
                .setReference("PMID:32292915")
                .setDescription("The Imperfect Cytokine Storm: Severe COVID-19 With ARDS in a Patient on Durable LVAD Support")
                .build();

        MetaData metaData = MetaData.newBuilder()
                .setPhenopacketSchemaVersion("2.0")
                .setCreatedBy("anonymous biocurator")
                .setCreated(curationTIme)
                .addResources(ncit)
                .addResources(mondo)
                .addExternalReferences(article)
                .build();
        return metaData;
    }




}
