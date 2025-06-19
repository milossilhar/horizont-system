package sk.leziemevpezinku.spring.model.compare;

import sk.leziemevpezinku.spring.model.base.AuditedEntityBase;

import java.util.Comparator;

public class AuditedCreatedAtComparator implements Comparator<AuditedEntityBase> {

    @Override
    public int compare(AuditedEntityBase a1, AuditedEntityBase a2) {
        if (a1 == null && a2 == null) return 0;
        if (a1 == null) return -1;
        if (a2 == null) return 1;
        if (a1.getCreatedAt() == null && a2.getCreatedAt() == null) return 0;
        if (a1.getCreatedAt() == null) return -1;
        if (a2.getCreatedAt() == null) return 1;

        return a1.getCreatedAt().compareTo(a2.getCreatedAt());
    }
}
