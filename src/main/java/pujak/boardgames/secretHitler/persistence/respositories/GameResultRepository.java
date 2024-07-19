package pujak.boardgames.secretHitler.persistence.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pujak.boardgames.secretHitler.persistence.dbModels.GameResultModel;

public interface GameResultRepository extends JpaRepository<GameResultModel, Long> {
}
