package assets;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Note {
	/**
	 * Class variables
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Asset asset;
	
	private String noteText;
	
	/**
	 * Constructor
	 */
	private Note() { }

	/**
	 * Parameterized Constructor
	 * @param asset
	 * @param noteText
	 */
	public Note(final Asset asset, final String noteText) {
		this.asset = asset;
		this.noteText = noteText;
	}
	
	/**
	 * Getter for Id
	 * @return
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Getter for asset
	 * @return
	 */
	public Asset getAsset() {
		return asset;
	}
	
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	/**
	 * Getter for noteText
	 * @return
	 */
	public String getNoteText() {
		return noteText;
	}
}
