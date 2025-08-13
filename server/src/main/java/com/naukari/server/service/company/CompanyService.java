package com.naukari.server.service.company;

import com.naukari.server.model.dto.company.CompanyDTO;
import com.naukari.server.model.dto.company.CompanyLogoDTO;
import com.naukari.server.model.dto.company.CompanyResponseDTO;
import com.naukari.server.model.dto.company.CompanyThumbnailDTO;
import com.naukari.server.utils.CustomResponse;

public interface CompanyService {
    // create company
    CustomResponse<CompanyResponseDTO> createCompany(Long userId,CompanyDTO company);

    // update company
    CustomResponse<CompanyResponseDTO> updateCompany(CompanyDTO company);

    // update logo
    CustomResponse<?> updateLogo(CompanyLogoDTO companyLogo);

    // update thumbnail
    CustomResponse<?> updateThumbnail(CompanyThumbnailDTO companyThumbnail);

//    CustomResponse<?> updateDocuments
}
