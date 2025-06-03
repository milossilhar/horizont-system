package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.AppParam;

@Repository
public interface AppParamRepository extends ListCrudRepository<AppParam, String> { }
