package sk.leziemevpezinku.spring.service.base;

public interface CreateService<DTO, CREATE_DTO> {

    DTO create(CREATE_DTO dto);
}
