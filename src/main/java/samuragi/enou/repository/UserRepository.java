package samuragi.enou.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import samuragi.enou.dto.dtodb.DtoDbUser;

import java.util.Optional;

public interface UserRepository extends CrudRepository<DtoDbUser, Long> {
    boolean existsByAccount(String account);
    Optional<DtoDbUser> findByAccountAndPassword(String account, String password);

    boolean existsByAccountAndPassword(String account, String encodedPwd);
}
