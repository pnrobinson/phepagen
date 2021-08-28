package org.monarchinitiative.phenogen.phenopacket;

import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.IndividualUtil.defaultMaleProband;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.InterpretationUtil.pathogenicHeterozygousHgvsInterpretation;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.MeasurementUtil.lowPlatelets;
import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil.ontologyClassFactory;

public class RareDiseaseMarfanLosartan extends PhenopacketFactory{

    private static final String PHENOPACKET_ID = "id-C";
    private static final String PROBAND_ID = "proband C";
    private static final OntologyClass disease = ontologyClassFactory("OMIM:154700 ", "Marfan syndrome");

    public RareDiseaseMarfanLosartan() {

        Individual proband = defaultMaleProband(PROBAND_ID, "P20Y");
        PhenotypicFeature aorticDilation = phenotypicFeature("HP:0002616", "Aortic root aneurysm");

        MedicalAction action = MedicalAction
                .newBuilder()
                .setTreatment(losartan())
                .build();
        phenopacket = Phenopacket.newBuilder()
                .setId(PHENOPACKET_ID)
                .setSubject(proband)
                .addPhenotypicFeatures(aorticDilation)
                .addMedicalActions(action)
                .setMetaData(metaData())
                .build();
    }



    Treatment losartan() {
        OntologyClass losartan = ontologyClassFactory("DrugCentral:1610", "losartan");
        OntologyClass administration = ontologyClassFactory("NCIT:C38288",  "Oral Route of Administration");
        OntologyClass bid = ontologyClassFactory( "NCIT:C64496", "Twice Daily");
        OntologyClass mg = ontologyClassFactory("UO:0000022","milligram");
        Quantity quantity = Quantity.newBuilder().setUnit(mg).setValue(30.0).build();
        TimeInterval timeInterval = TimeInterval.newBuilder()
                .setStart(parseIsoLocalDate("2020-03-20"))
                .setEnd(parseIsoLocalDate("2021-03-20"))
                .build();
        DoseInterval dosage = DoseInterval
                .newBuilder()
                .setQuantity(quantity)
                .setScheduleFrequency(bid)
                .setInterval(timeInterval)
                .build();
       return Treatment
                .newBuilder()
                .setAgent(losartan)
                .setRouteOfAdministration(administration)
                .addDoseIntervals(dosage)
                .setDrugType(DrugType.PRESCRIPTION)
                .build();
    }


    MetaData metaData() {
        return MetaData.newBuilder()
                .addResources(Resource.newBuilder()
                        .setId("hp")
                        .setName("human phenotype ontology")
                        .setNamespacePrefix("HP")
                        .setIriPrefix("http://purl.obolibrary.org/obo/HP_")
                        .setUrl("http://purl.obolibrary.org/obo/hp.owl")
                        .setVersion("2018-03-08")
                        .build())
                .addResources(Resource.newBuilder()
                        .setId("geno")
                        .setName("Genotype Ontology")
                        .setNamespacePrefix("GENO")
                        .setIriPrefix("http://purl.obolibrary.org/obo/GENO_")
                        .setUrl("http://purl.obolibrary.org/obo/geno.owl")
                        .setVersion("19-03-2018")
                        .build())
                .setCreatedBy("Peter R.")
                .setCreated(parseIsoLocalDate("2021-07-31"))
                .setPhenopacketSchemaVersion("2.0")
                .build();
    }

}
