package com.example.AutoEcole.api.model.Inscription;

import com.example.AutoEcole.api.model.Document.DocumentDTO;
import com.example.AutoEcole.dal.domain.entity.*;
import com.example.AutoEcole.dal.domain.enum_.InscriptionStatut;
import com.example.AutoEcole.dal.domain.enum_.StageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInscriptionRequestBody {
        private Long userId;
        private Long stageId;
        private StageType stageType;
        private InscriptionStatut inscriptionStatut;
        private List<DocumentDTO> documents;
}


