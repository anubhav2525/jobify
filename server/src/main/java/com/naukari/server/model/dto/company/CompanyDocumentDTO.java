package com.naukari.server.model.dto.company;

import com.naukari.server.model.enums.FileType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDocumentDTO {
    @NotNull(message = "Document type is required")
    private FileType documentType;

    @NotNull(message = "Document is required")
    private MultipartFile file;
}
