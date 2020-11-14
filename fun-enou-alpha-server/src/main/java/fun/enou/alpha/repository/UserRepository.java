package fun.enou.alpha.repository;

import fun.enou.alpha.dto.dtodb.DtoDbUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<DtoDbUser, Long> {
    boolean existsByAccount(String account);
    Optional<DtoDbUser> findByAccountAndPassword(String account, String password);

    boolean existsByAccountAndPassword(String account, String encodedPwd);
}
