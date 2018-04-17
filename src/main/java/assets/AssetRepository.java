package assets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
	// Return the assets by the given name
	Optional<Asset> findByName(String name);
	Asset deleteByName(String name);
}
