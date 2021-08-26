package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.ga4gh.vrsatile.v1.Expression;
import org.ga4gh.vrsatile.v1.VariationDescriptor;
import org.phenopackets.schema.v2.core.*;

public class InterpretationUtil {

    public static VariationDescriptor heterozygousHgvsVariant(String hgvs) {
        OntologyClass heterozygous = OntologyClassUtil.heterozygous;
        return VariationDescriptor.newBuilder()
                .addExpressions(Expression.newBuilder().setSyntax("hgvs").setValue(hgvs))
                .setAllelicState(heterozygous)
                .build();
    }

    public static VariantInterpretation pathogenicVariantInterpretation(VariationDescriptor variationDescriptor) {
        return VariantInterpretation.newBuilder()
                .setAcmgPathogenicityClassification(AcmgPathogenicityClassification.PATHOGENIC)
                .setVariationDescriptor(variationDescriptor).build();
    }

    public static GenomicInterpretation pathogenicHeterozygousHgvs(String id, String hgvs) {
        VariationDescriptor variationDescriptor = heterozygousHgvsVariant(hgvs);
        VariantInterpretation variantInterpretation = pathogenicVariantInterpretation(variationDescriptor);
        return GenomicInterpretation.newBuilder()
                .setSubjectOrBiosampleId(id)
                .setVariantInterpretation(variantInterpretation)
                .setInterpretationStatus(GenomicInterpretation.InterpretationStatus.CONTRIBUTORY)
                .build();
    }


    public static Interpretation pathogenicHeterozygousHgvsInterpretation(OntologyClass disease,
                                                                               String id,
                                                                               String subjectId,
                                                                               String hgvs) {
        GenomicInterpretation genomicInterpretation = pathogenicHeterozygousHgvs(subjectId, hgvs);
        Diagnosis diagnosis = Diagnosis.newBuilder()
                .setDisease(disease)
                .addGenomicInterpretations(genomicInterpretation)
                .build();
        return Interpretation.newBuilder()
                .setId(id)
                .setProgressStatus(Interpretation.ProgressStatus.SOLVED)
                .setDiagnosis(diagnosis)
                .build();
    }

}
