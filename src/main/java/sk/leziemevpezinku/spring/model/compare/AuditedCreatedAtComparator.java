package sk.leziemevpezinku.spring.model.compare;

import sk.leziemevpezinku.spring.model.base.AuditedCreationEntityBase;

import java.util.Comparator;

public class AuditedCreatedAtComparator implements Comparator<AuditedCreationEntityBase> {

    @Override
    public int compare(AuditedCreationEntityBase a1, AuditedCreationEntityBase a2) {
        if (a1 == null && a2 == null) return 0;
        if (a1 == null) return -1;
        if (a2 == null) return 1;
        if (a1.getCreatedAt() == null) return -1;
        if (a2.getCreatedAt() == null) return 1;

        return a1.getCreatedAt().compareTo(a2.getCreatedAt());
    }
}
