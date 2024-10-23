package com.jobseekr.app.auth.models;

 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 import java.util.Date;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "certifications")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String issuingOrganization;
    private Date issueDate;
    private Date expirationDate;
    private boolean isPresentDate;
    private String credentialId;
    private String credentialUrl;
    private String description;
	public Certification(String name, String issuingOrganization, Date issueDate, Date expirationDate,
			boolean isPresentDate, String credentialId, String credentialUrl, String description) {
		super();
		this.name = name;
		this.issuingOrganization = issuingOrganization;
		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.isPresentDate = isPresentDate;
		this.credentialId = credentialId;
		this.credentialUrl = credentialUrl;
		this.description = description;
	}


}
