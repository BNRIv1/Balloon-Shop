package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Balloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalloonRepository extends JpaRepository<Balloon, Long> {

    List<Balloon> findBalloonByName(String text);
    List<Balloon> findBalloonByNameContaining(String text);
    void deleteBalloonByName(String name);
    List<Balloon> findAllByOrderByIdAsc();
}
