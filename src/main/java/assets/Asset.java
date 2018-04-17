package assets;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Asset {
	/**
	 * Class variables
	 */
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String URI;
	
	@OneToMany(mappedBy = "asset")
	private Set<Note> notes = new HashSet<>();
	
	/**
	 * Constructor
	 */
	Asset() { } // Package Only
	
	/**
	 * Constructor
	 * @param name
	 * @param uri
	 */
	public Asset(final String name, final String uri) {
		this.name = name;
		this.URI = uri;
	}
	
	
	/**
	 * Getter for the id
	 * @return the asset id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Getter for the name
	 * @return the asset name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the uri
	 * @return the asset uri
	 */
	public String getURI() {
		return URI;
	}
	
	public void setURI(String uri) {
		this.URI = uri;
	}
	
	/**
	 * Getter for the notes
	 * @return the assets notes
	 */
	public Set<Note> getNotes() {
		return notes;
	}
}
