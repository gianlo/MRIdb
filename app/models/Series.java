package models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Series extends DomainModel {

	public String series_no;
	public String series_iuid;
	public String station_name;
	public String series_desc;
	public String series_custom1;
	public String modality;
	@ManyToOne
	@JoinColumn(name="study_fk")
	public Study study;
	@OneToMany(mappedBy = "series")
	public Set<Instance> instances;

	public String toDownloadString() {
		return String.format("%s_%s_%s", study.toDownloadString(), series_no, series_custom1).replaceAll("\\W+", "");
	}

	public String toClipboardString() {
		return String.format("%s of %s", series_custom1, study.toClipboardString());
	}

}
