package org.monarchinitiative.phenogen.phenopacket;

import org.monarchinitiative.phenogen.phenopacket.buildingblocks.ExternalReferenceUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.MetaDataUtil;
import org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil;
import org.monarchinitiative.phenol.ontology.data.TermId;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;


import static org.monarchinitiative.phenogen.phenopacket.PhenotypicFeatureUtil.*;
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
public class RareDiseaseMCAHS1 extends PhenopacketFactory {
    private static final String PHENOPACKET_ID = "id-A";
    private static final String PROBAND_ID = "proband A";
    private static final String PMID = "PMID:30808312";
    private static final String publication = "COL6A1 mutation leading to Bethlem myopathy with recurrent hematuria: a case report";
    private static final Evidence authorAssertion = evidenceWithEcoAuthorStatement(PMID, publication);
    private static final OntologyClass disease = OntologyClassUtil.ontologyClassFactory("OMIM:158810", "Bethlem myopathy 1");
    private static final String hgvsExpression = "NM_001848.2:c.877G>A";



    public RareDiseaseMCAHS1() {
        ExternalReference citation = ExternalReferenceUtil.externalReference(PMID, publication);
        MetaData metaData = MetaDataUtil.defaultRareDiseaseMetaData(citation);
        Individual proband = defaultMaleProband(PROBAND_ID, "P8D");
       Interpretation interpretation = pathogenicHeterozygousHgvsInterpretation(disease, "interpretation.id.1", PROBAND_ID, hgvsExpression);
        phenopacket = Phenopacket.newBuilder()
                .setId(PHENOPACKET_ID)
                .setSubject(proband)
                .addPhenotypicFeatures(ventricularSeptalDefect())
                .addPhenotypicFeatures(coarseFacialFeatures())
                .addPhenotypicFeatures(bilateralCryptorchidism())
                .addPhenotypicFeatures(polyhydramnios())
                .addPhenotypicFeatures(micropenis())
                .addPhenotypicFeatures(anonychia())
                .addPhenotypicFeatures(cerebellarVermisHypoplasia())
                .addPhenotypicFeatures(cataract())
                .addPhenotypicFeatures(dilatedFourthVentricle())
                .addPhenotypicFeatures(unilateralCleftLip())
                .addInterpretations(interpretation)
                .setMetaData(metaData)
                .build();
    }

    private PhenotypicFeature ventricularSeptalDefect() {
        OntologyClass clz = ontologyClassFactory("HP:0001629","Ventricular septal defect");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }

    private PhenotypicFeature  coarseFacialFeatures() {
        OntologyClass clz = ontologyClassFactory("HP:0000280", "Coarse facial features");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }
    private PhenotypicFeature  bilateralCryptorchidism() {
        OntologyClass clz = ontologyClassFactory("HP:0008689", "Bilateral cryptorchidism");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }

    private PhenotypicFeature  polyhydramnios() {
        OntologyClass clz = ontologyClassFactory("HP:0001561", "Polyhydramnios");
        return fetalOnsetFeature(clz, authorAssertion);
    }

    private PhenotypicFeature  micropenis() {
        OntologyClass clz = ontologyClassFactory("HP:0000054","Micropenis");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }

    private PhenotypicFeature  anonychia() {
        OntologyClass clz = ontologyClassFactory("HP:0001798", "Anonychia");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }

    private PhenotypicFeature  cerebellarVermisHypoplasia() {
        OntologyClass clz = ontologyClassFactory("HP:0001320", "Cerebellar vermis hypoplasia");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }

    private PhenotypicFeature  cataract() {
        OntologyClass clz = ontologyClassFactory("HP:0000518", "Cataract");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }

    private PhenotypicFeature  dilatedFourthVentricle() {
        OntologyClass clz = ontologyClassFactory("HP:0002198", "Dilated fourth ventricle");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }

    private PhenotypicFeature  unilateralCleftLip() {
        OntologyClass clz = ontologyClassFactory("HP:0100333", "Unilateral cleft lip");
        return phenotypicFeatureWithEvidence(clz, authorAssertion);
    }







}
