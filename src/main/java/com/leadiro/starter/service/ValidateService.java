package com.leadiro.starter.service;

import com.leadiro.starter.service.validation.dto.PostalDetail;
import com.leadiro.starter.service.validation.dto.ValidationStatus;

public interface ValidateService {

	ValidationStatus<String> email(String email);
	ValidationStatus<PostalDetail> postalCode(String postalCode);
}
