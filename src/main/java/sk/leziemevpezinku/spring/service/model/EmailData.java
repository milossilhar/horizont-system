package sk.leziemevpezinku.spring.service.model;

import lombok.*;
import org.springframework.core.io.ClassPathResource;
import sk.leziemevpezinku.spring.util.StringUtils;

import java.io.File;

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

    public File getAttachmentFile () {
        try {
            if (StringUtils.blank(attachmentSource)) return null;
            return new ClassPathResource(attachmentSource).getFile();
        } catch (Exception e) {
            return null;
        }
    }
}
