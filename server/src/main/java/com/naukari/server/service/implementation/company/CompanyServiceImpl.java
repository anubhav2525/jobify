package com.naukari.server.service.implementation.company;

import com.naukari.server.exception.CustomExceptions;
import com.naukari.server.model.dto.company.CompanyDTO;
import com.naukari.server.model.dto.company.CompanyLogoDTO;
import com.naukari.server.model.dto.company.CompanyResponseDTO;
import com.naukari.server.model.dto.company.CompanyThumbnailDTO;
import com.naukari.server.model.dto.user.UserResponseDTO;
import com.naukari.server.model.entity.company.Company;
import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.ResponseStatus;
import com.naukari.server.model.enums.VerificationStatus;
import com.naukari.server.repository.company.CompanyDocumentRepo;
import com.naukari.server.repository.company.CompanyRecruiterRepo;
import com.naukari.server.repository.company.CompanyRepo;
import com.naukari.server.service.company.CompanyService;
import com.naukari.server.service.implementation.user.UserServiceImpl;
import com.naukari.server.utils.CustomResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private final CompanyRepo companyRepo;

    @Autowired
    private final CompanyRecruiterRepo companyRecruiterRepo;

    @Autowired
    private final CompanyDocumentRepo companyDocumentRepo;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final UserServiceImpl userService;

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    public CompanyServiceImpl(CompanyRepo companyRepo, CompanyRecruiterRepo companyRecruiterRepo, CompanyDocumentRepo companyDocumentRepo, ModelMapper modelMapper, UserServiceImpl userService) {
        this.companyRepo = companyRepo;
        this.companyRecruiterRepo = companyRecruiterRepo;
        this.companyDocumentRepo = companyDocumentRepo;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public CustomResponse<CompanyResponseDTO> createCompany(Long userId, CompanyDTO companyDTO) {
        try {
            logger.info("Creating company with name: {}", companyDTO.getCompanyName());

            // Check if company already exists
            if (companyRepo.existsByNameIgnoreCase(companyDTO.getCompanyName().trim())) {
                throw new CustomExceptions.CompanyAlreadyExistsException("Company with name '" + companyDTO.getCompanyName() + "' already exists");
            }

            // Verify the user is already exists or not by email who is registering the company
            CustomResponse<UserResponseDTO> userResponse = userService.getUserById(userId);
            User user = userService.convertToUser(userResponse.getData());

            if (userResponse.getStatus().equals(ResponseStatus.SUCCESS))
                throw new CustomExceptions.CompanyAlreadyExistsException("Company with email '" + userResponse.getData().getEmail() + "' already exists"); // check user is already register or not for company account

            // Create new company
            Company company = new Company();
            company.setUser(user);
            company.setCompanyName(companyDTO.getCompanyName().trim());
            company.setLegalName(companyDTO.getLegalName().trim());
            company.setCompanySlug(company.getCompanySlug().trim().toLowerCase());
            company.setDescription(companyDTO.getDescription().trim());
            company.setIndustry(companyDTO.getIndustry());
            company.setWebsiteUrl(companyDTO.getWebsiteUrl());
            company.setCompanySize(companyDTO.getCompanySize());
            company.setAddress(companyDTO.getAddress());
            company.setFoundedYear(companyDTO.getFoundedYear());
            company.setActive(true);
            company.setSocial(companyDTO.getSocial());
            company.setVerificationStatus(VerificationStatus.PENDING);
            company.setCreatedAt(LocalDateTime.now());
            company.setUpdatedAt(LocalDateTime.now());

            Company savedCompany = companyRepo.save(company);
            logger.info("Company created successfully with ID: {}", savedCompany.getId());

            CompanyResponseDTO responseDTO = convertToResponseDTO(savedCompany);
            return new CustomResponse<>(ResponseStatus.CREATED, "Company created successfully", responseDTO);
        } catch (CustomExceptions.CompanyAlreadyExistsException | CustomExceptions.InvalidCompanyDataException e) {
            logger.warn("Company creation failed: {}", e.getMessage());
            return new CustomResponse<>(ResponseStatus.ALREADY_EXISTS, e.getMessage(), null);
        } catch (Exception e) {
            logger.error("Unexpected error during company creation: {}", e.getMessage(), e);
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR, "Failed to create company: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<CompanyResponseDTO> updateCompany(CompanyDTO company) {
        return null;
    }

//    @Override
//    public CustomResponse<CompanyResponseDTO> updateCompany(CompanyDTO companyDTO) {
//        try {
//            logger.info("Updating company with ID: {}", companyDTO.getId());
//
//            if (companyDTO.getId() == null) {
//                throw new CustomExceptions.InvalidCompanyDataException("Company ID is required for update");
//            }
//
//            Optional<Company> existingCompany = companyRepo.findById(companyDTO.getId());
//            if (existingCompany.isEmpty()) {
//                throw new CustomExceptions.CompanyNotFoundException("Company not found with ID: " + companyDTO.getId());
//            }
//
//            Company oldCompany = existingCompany.get();
//
//            // Check for duplicate name (excluding current company)
//            if (!oldCompany.getCompanyName().equalsIgnoreCase(companyDTO.getCompanyName()) &&
//                    companyRepo.existsByNameIgnoreCaseAndIdNot(companyDTO.getCompanyName().trim(), companyDTO.getId())) {
//                throw new CustomExceptions.CompanyAlreadyExistsException("Another company with name '" + companyDTO.getCompanyName() + "' already exists");
//            }
//
//            // Update company fields
//            oldCompany.setName(companyDTO.getName().trim());
//            oldCompany.setDescription(companyDTO.getDescription().trim());
//            oldCompany.setIndustry(companyDTO.getIndustry());
//            oldCompany.setWebsite(companyDTO.getWebsite());
//            oldCompany.setPhone(companyDTO.getPhone().trim());
//            oldCompany.setAddress(companyDTO.getAddress());
//            oldCompany.setCity(companyDTO.getCity());
//            oldCompany.setState(companyDTO.getState());
//            oldCompany.setCountry(companyDTO.getCountry());
//            oldCompany.setZipCode(companyDTO.getZipCode());
//            oldCompany.setEmployeeSize(companyDTO.getEmployeeSize());
//            oldCompany.setFoundedYear(companyDTO.getFoundedYear());
//            oldCompany.setUpdatedAt(LocalDateTime.now());
//
//            Company updatedCompany = companyRepo.save(oldCompany);
//            logger.info("Company updated successfully with ID: {}", updatedCompany.getId());
//
//            CompanyResponseDTO responseDTO = convertToResponseDTO(updatedCompany);
//            return new CustomResponse<>(ResponseStatus.SUCCESS, "Company updated successfully", responseDTO);
//
//        } catch (CompanyNotFoundException | CompanyAlreadyExistsException | InvalidCompanyDataException e) {
//            logger.warn("Company update failed: {}", e.getMessage());
//            return new CustomResponse<>(ResponseStatus.NOT_FOUND, e.getMessage(), null);
//        } catch (Exception e) {
//            logger.error("Unexpected error during company update: {}", e.getMessage(), e);
//            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR, "Failed to update company: " + e.getMessage(), null);
//        }
//    }

    @Override
    public CustomResponse<?> updateLogo(CompanyLogoDTO companyLogo) {
        return null;
    }

    @Override
    public CustomResponse<?> updateThumbnail(CompanyThumbnailDTO companyThumbnail) {
        return null;
    }

    // model mapper
    private CompanyResponseDTO convertToResponseDTO(Company company) {
        return this.modelMapper.map(company, CompanyResponseDTO.class);
    }
}
