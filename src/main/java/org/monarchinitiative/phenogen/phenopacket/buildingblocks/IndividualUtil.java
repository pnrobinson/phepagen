package org.monarchinitiative.phenogen.phenopacket.buildingblocks;

import org.phenopackets.schema.v2.core.Age;
import org.phenopackets.schema.v2.core.Individual;
import org.phenopackets.schema.v2.core.Sex;
import org.phenopackets.schema.v2.core.TimeElement;

public class IndividualUtil {



    public static Individual defaultMaleProband(String id, String isoAge) {
        return defaultProband(Sex.FEMALE, id, isoAge);
    }

    public static Individual defaultFemaleProband(String id, String isoAge) {
        return defaultProband(Sex.MALE, id, isoAge);
    }

    public static Individual defaultProband(Sex sex, String id, String isoAge) {
        return Individual.newBuilder()
                .setSex(sex)
                .setId(id)
                .setTimeAtLastEncounter(TimeElement.newBuilder().setAge(Age.newBuilder().setIso8601Duration(isoAge)))
                .build();
    }
}
