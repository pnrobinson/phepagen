package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import com.google.protobuf.Timestamp;
import org.phenopackets.schema.v2.core.ExternalReference;
import org.phenopackets.schema.v2.core.MetaData;
import org.phenopackets.schema.v2.core.Resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MetaDataUtil {

    private final static String DEFAULT_PMID =  "PMID:33264411";
    private final static String DEFAULT_TITLE =  "The Human Phenotype Ontology in 2021";
    private final static ExternalReference DEFAULT_EXTERNAL_REFERENCE =
            ExternalReferenceUtil.externalReference(DEFAULT_PMID, DEFAULT_TITLE );


    private final static LocalDateTime timeNow = LocalDate.of(2021, 5, 14).atTime(10, 35);
    /**
     * Time when the MetaData was constructed.
     */
    private final static Timestamp timestamp =Timestamp.newBuilder()
            .setSeconds(timeNow.toEpochSecond(ZoneOffset.UTC))
            .build();

    private final MetaData metaData;

    private MetaDataUtil() {
        this(DEFAULT_EXTERNAL_REFERENCE);
    }

    private MetaDataUtil(ExternalReference citation) {
        this.metaData = MetaData.newBuilder()
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
                .addResources(Resource.newBuilder()
                        .setId("pubmed")
                        .setName("PubMed")
                        .setNamespacePrefix("PMID")
                        .setIriPrefix("https://www.ncbi.nlm.nih.gov/pubmed/")
                        .build())
                .setCreatedBy("Peter R.")
                .setCreated(timestamp)
                .setPhenopacketSchemaVersion("2.0")
                .addExternalReferences(citation)
                .build();
    }

    public static MetaData defaultRareDiseaseMetaData(ExternalReference externalReference) {
        MetaDataUtil mdatautil = new MetaDataUtil(externalReference);
        return mdatautil.metaData;
    }

}
