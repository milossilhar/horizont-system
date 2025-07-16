package sk.leziemevpezinku.spring.service.model;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import sk.leziemevpezinku.spring.util.StringUtils;

@Log4j2
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailData {

    @NonNull
    private EmailType emailType;

    private String subjectAddon;

    private String subjectReplace;

    private String attachmentFilename;

    private String attachmentSource;

    @NonNull
    private String htmlBody;

    public String getEmailName() {
        return emailType.name();
    }

    public String getSubject() {
        if (StringUtils.notBlank(subjectReplace)) return subjectReplace;
        return emailType.getSubject() + (StringUtils.notBlank(subjectAddon) ? " - " + subjectAddon : "");
    }

    public boolean hasAttachment() {
        return StringUtils.notBlank(attachmentFilename) && StringUtils.notBlank(attachmentSource);
    }

    public Resource getAttachmentFile () {
        try {
            if (StringUtils.blank(attachmentSource)) return null;
            return new ClassPathResource(attachmentSource);
        } catch (Exception e) {
            log.error("Could not load attachment file: {}", attachmentSource, e);
            return null;
        }
    }
}
