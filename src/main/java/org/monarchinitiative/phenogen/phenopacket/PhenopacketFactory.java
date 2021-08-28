package org.monarchinitiative.phenogen.phenopacket;

import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v2.Phenopacket;
import org.phenopackets.schema.v2.core.*;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.monarchinitiative.phenogen.phenopacket.buildingblocks.OntologyClassUtil.ontologyClassFactory;

public abstract class PhenopacketFactory {

    /** version 2 phenopacket for the disease MCAHS1 */
    protected Phenopacket phenopacket;

    public Phenopacket phenopacket() {
        return this.phenopacket;
    }

    /**
     * Convenience function
     * @param cls
     * @param authorAssertion
     * @return
     */
    protected PhenotypicFeature phenotypicFeature(OntologyClass cls, Evidence authorAssertion) {
        return PhenotypicFeature.newBuilder().addEvidence(authorAssertion).setType(cls).build();
    }

    protected PhenotypicFeature phenotypicFeature(String termId, String label, Evidence authorAssertion) {
        OntologyClass clz = ontologyClassFactory(termId, label);
        return PhenotypicFeature.newBuilder().setType(clz).addEvidence(authorAssertion).build();
    }

    protected PhenotypicFeature phenotypicFeature(String termId, String label) {
        OntologyClass clz = ontologyClassFactory(termId, label);
        return PhenotypicFeature.newBuilder().setType(clz).build();
    }

    protected Quantity quantityOf(double value, OntologyClass unit) {
        return Quantity.newBuilder().setUnit(unit).setValue(value).build();
    }

    protected TimeInterval parseLocalDateInterval(String isoLocalDateStart, String isoLocalDateEnd) {
        return TimeInterval.newBuilder().setStart(parseIsoLocalDate(isoLocalDateStart)).setEnd(parseIsoLocalDate(isoLocalDateEnd)).build();
    }
    protected TimeElement parseLocalDateRange(String isoLocalDateStart, String isoLocalDateEnd) {
        return TimeElement.newBuilder()
                .setInterval(parseLocalDateInterval(isoLocalDateStart, isoLocalDateEnd))
                .build();
    }

    protected Timestamp parseIsoLocalDate(String isoLocalDate) {
        return timestampFrom(LocalDate.parse(isoLocalDate));
    }

    protected Timestamp timestampFrom(LocalDate localDateStart) {
        return Timestamp.newBuilder().setSeconds(localDateStart.atStartOfDay().toEpochSecond(ZoneOffset.UTC)).build();
    }
    protected  TimeElement parseLocalDate(String isoLocalDate) {
        return TimeElement.newBuilder()
                .setTimestamp(parseIsoLocalDate(isoLocalDate))
                .build();
    }
}
