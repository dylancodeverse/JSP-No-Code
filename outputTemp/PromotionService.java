package scaffold.framework.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;

import scaffold.framework.demo.entity.Promotion;

import scaffold.framework.demo.repository.PromotionRepository;

@Service

public class PromotionService {

    private PromotionRepository promotionRepository;

    private PromotionRepositoryRepository promotionRepositoryRepository;

    public PromotionRepositoryService(PromotionRepositoryRepository promotionRepositoryRepository) {
        this.promotionRepositoryRepository = promotionRepositoryRepository;
    }

    public PromotionRepository save(PromotionRepository promotionRepository) {
        return promotionRepositoryRepository.save(promotionRepository);
    }

    public List<PromotionRepository> findAll() {
        return promotionRepositoryRepository.findAll();
    }

    public PromotionRepository findById(Long id) {
        return promotionRepositoryRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        promotionRepositoryRepository.deleteById(id);
    }
}
