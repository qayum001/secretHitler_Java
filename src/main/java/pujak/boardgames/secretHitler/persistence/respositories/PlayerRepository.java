package pujak.boardgames.secretHitler.persistence.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pujak.boardgames.secretHitler.persistence.dbModels.PlayerModel;

public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {
}
