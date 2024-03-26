package scaffold.framework.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository ;

import scaffold.framework.demo.entity.PromotionModel ;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
}

