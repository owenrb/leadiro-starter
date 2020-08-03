package com.leadiro.starter.service.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostalDetail {
	 private String postcode;
	 private Integer quality;
	 private Long eastings;
	 private Long northings;
     private String country;
     @JsonProperty("nhs_ha") private String nhsHa; 
     private Long longitude;
     private Long latitude;
     @JsonProperty("european_electoral_region") private String europeanElectoralRegion;
     @JsonProperty("primary_care_trust")  private String primaryCareTrust;   
     private String region;
     private String lsoa;
     private String msoa;
     private String incode;
     private String outcode;
     @JsonProperty("parliamentary_constituency") private String parliamentaryConstituency;
     @JsonProperty("admin_district") private String adminDistrict;
     private String parish; 
     @JsonProperty("admin_county") private String adminCounty;
     @JsonProperty("admin_ward") private String adminWard; 
     private String ced;
     private String ccg;
     private String nuts;
}
