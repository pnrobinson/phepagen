package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v2.core.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MeasurementUtil {


    private static final OntologyClass loincPlatelets =
            OntologyClassUtil.ontologyClassFactory("LOINC:26515-7","Platelets [#/volume] in Blood");
    private static final OntologyClass cellsPerMicroliter =
            OntologyClassUtil.ontologyClassFactory("UO:0000316", "cells per microliter");
    private static final ReferenceRange plateletsRange =
            ReferenceRange.newBuilder().setUnit(loincPlatelets).setLow(150000.0).setHigh(450000.0).build();
    private final static LocalDateTime dateIn2020 = LocalDate.of(2020, 10, 01).atTime(10, 54, 02);
    private final static Timestamp timestamp =Timestamp.newBuilder()
            .setSeconds(dateIn2020.toEpochSecond(ZoneOffset.UTC))
            .build();

    public static Value quantitativeValue(OntologyClass unit, double value, ReferenceRange referenceRange) {
        Quantity quantity = Quantity.newBuilder()
                .setUnit(unit)
                .setValue(value)
                .setReferenceRange(referenceRange)
                .build();
        return Value.newBuilder().setQuantity(quantity).build();
    }

    public static Measurement quantitativeAssay(OntologyClass assay,
                                                OntologyClass unit,
                                                double quantity,
                                                ReferenceRange range,
                                                TimeElement timeElement) {
        Value value = Value.newBuilder()
                .setQuantity(Quantity.newBuilder()
                        .setValue(quantity)
                        .setUnit(unit)
                        .setReferenceRange(range))
                .build();
        return Measurement.newBuilder()
                .setAssay(assay)
                .setValue(value)
                .setTimeObserved(timeElement)
                .build();
    }

    public static Measurement lowPlatelets() {
        TimeElement timeElement = TimeElement.newBuilder().setTimestamp(timestamp).build();
        return quantitativeAssay(loincPlatelets, cellsPerMicroliter, 24000.0, plateletsRange, timeElement);
    }




    /*
    measurement:
    assay:
        id: "LOINC:26515-7"
        label: "Platelets [#/volume] in Blood"
    value:
        quantity:
            unit:
                id: "UO:0000316"
                label: "cells per microliter"
            value: 24000.0
    referenceRange:
        unit:
            id: "UO:0000316"
            label: "cells per microliter"
        low: 150000.0
        high: 450000.0
    timeObserved:
        timestamp: "2020-10-01T10:54:20.021Z"

     */
}
