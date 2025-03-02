package umc.spring.domain.Store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Store.data.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
