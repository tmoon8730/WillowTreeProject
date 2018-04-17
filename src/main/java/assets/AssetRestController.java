package assets;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/")
public class AssetRestController {
	private final AssetRepository assetRepository;
	private final NoteRepository noteRepository;
	
	@Autowired
	AssetRestController(AssetRepository assetRepository, NoteRepository noteRepository) {
		this.assetRepository = assetRepository;
		this.noteRepository = noteRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Collection<Asset> allAssets() {
		return this.assetRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{assetId}")
	Optional<Asset> specificAsset(@PathVariable String assetId){
		this.validateAsset(assetId);
		return this.assetRepository.findByName(assetId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@RequestBody Asset input) {

		try {
			input.setURI(input.getURI());
			Asset result = assetRepository.save(input);
			input.getNotes().forEach(note -> {
				note.setAsset(input);
				noteRepository.save(note);
			});
			
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(result.getId()).toUri();
			
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/{assetId}")
	ResponseEntity<?> delete(@PathVariable String assetId) {
		this.validateAsset(assetId);
		try {
			Optional<Asset> deleteAsset = this.assetRepository.findByName(assetId);
			if(deleteAsset.isPresent()) {
				deleteAsset.get().getNotes().forEach(note -> {
					this.noteRepository.delete(note);
				});
				this.assetRepository.delete(deleteAsset.get());
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.noContent().build();
		}
	}
	
	/**
	 * Utility function that checks if a given asset exists
	 * @param assetId
	 */
	private void validateAsset(String assetId) {
		this.assetRepository.findByName(assetId).orElseThrow(() -> new AssetNotFoundException(assetId));
	}

}
