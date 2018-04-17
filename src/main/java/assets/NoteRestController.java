package assets;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/{assetId}/notes")
public class NoteRestController {
	private final NoteRepository noteRepository;
	private final AssetRepository assetRepository;
	
	/**
	 * Paramertized controller with lazy loaded instantiation
	 * @param noteRepository
	 * @param assetRepository
	 */
	@Autowired
	NoteRestController(NoteRepository noteRepository, AssetRepository assetRepository) {
		this.noteRepository = noteRepository;
		this.assetRepository = assetRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	Collection<Note> readNotes(@PathVariable String assetId) {
		// Make sure its a valid asset that exists
		this.validateAsset(assetId);
		return this.noteRepository.findByAssetName(assetId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String assetId, @RequestBody Note input) {
		this.validateAsset(assetId);

		return this.assetRepository
				.findByName(assetId)
				.map(asset -> {
					Note result = noteRepository.save(new Note(asset,
							input.getNoteText()));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getId()).toUri();

					return ResponseEntity.created(location).build();
				})
				.orElse(ResponseEntity.noContent().build());

	}

	
	@RequestMapping(method = RequestMethod.GET, value="/{noteId}")
	Optional<Note> readNote(@PathVariable String assetId, @PathVariable Long noteId) {
		// Make sure its a valid asset that exists
		this.validateAsset(assetId);
		
		return this.noteRepository.findById(noteId);
	}
	
	/**
	 * Utility function that checks if a given asset exists
	 * @param assetId
	 */
	private void validateAsset(String assetId) {
		this.assetRepository.findByName(assetId).orElseThrow(() -> new AssetNotFoundException(assetId));
	}
}
