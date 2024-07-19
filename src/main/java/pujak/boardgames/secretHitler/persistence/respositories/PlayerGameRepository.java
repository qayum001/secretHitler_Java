package pujak.boardgames.secretHitler.persistence.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pujak.boardgames.secretHitler.persistence.dbModels.PlayerGameModel;

public interface PlayerGameRepository extends JpaRepository<PlayerGameModel, Long> {
}
