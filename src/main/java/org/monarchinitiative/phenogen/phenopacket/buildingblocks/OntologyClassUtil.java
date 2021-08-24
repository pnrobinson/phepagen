package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.monarchinitiative.phenol.ontology.data.TermId;

import org.phenopackets.schema.v2.core.OntologyClass;

public class OntologyClassUtil {

    public static OntologyClass factory(TermId tid, String label) {
        return OntologyClass.newBuilder().setId(tid.getValue()).setLabel(label).build();
    }

    public static OntologyClass ventricularSeptalDefect() {
        TermId tid = TermId.of("HP:0001629");
        String label = "Ventricular septal defect";
        return factory(tid, label);
    }

    public static OntologyClass coarseFacialFeatures() {
        TermId tid = TermId.of("HP:0000280");
        String label = "Coarse facial features";
        return factory(tid, label);
    }
    public static OntologyClass bilateralCryptorchidism() {
        TermId tid = TermId.of("HP:0008689");
        String label = "Bilateral cryptorchidism";
        return factory(tid, label);
    }

    public static OntologyClass polyhydramnios() {
        TermId tid = TermId.of("HP:0001561");
        String label = "Polyhydramnios";
        return factory(tid, label);
    }

    public static OntologyClass micropenis() {
        TermId tid = TermId.of("HP:0000054");
        String label = "Micropenis";
        return factory(tid, label);
    }

    public static OntologyClass anonychia() {
        TermId tid = TermId.of("HP:0001798");
        String label = "Anonychia";
        return factory(tid, label);
    }

    public static OntologyClass cerebellarVermisHypoplasia() {
        TermId tid = TermId.of("HP:0001320");
        String label = "Cerebellar vermis hypoplasia";
        return factory(tid, label);
    }

    public static OntologyClass cataract() {
        TermId tid = TermId.of("HP:0000518");
        String label = "Cataract";
        return factory(tid, label);
    }

    public static OntologyClass dilatedFourthVentricle() {
        TermId tid = TermId.of("HP:0002198");
        String label = "Dilated fourth ventricle";
        return factory(tid, label);
    }

    public static OntologyClass unilateralCleftLip() {
        TermId tid = TermId.of("HP:0100333");
        String label = "Unilateral cleft lip";
        return factory(tid, label);
    }



}
