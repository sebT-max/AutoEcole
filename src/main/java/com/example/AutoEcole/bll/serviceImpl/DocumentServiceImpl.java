package com.example.AutoEcole.bll.serviceImpl;

import com.example.AutoEcole.api.model.Document.create.CreateDocumentRequestBody;
import com.example.AutoEcole.api.model.Document.create.CreateDocumentResponseBody;
import com.example.AutoEcole.bll.service.ActivityService;
import com.example.AutoEcole.bll.service.DocumentService;
import com.example.AutoEcole.dal.domain.entity.Activity;
import com.example.AutoEcole.dal.domain.entity.Document;
import com.example.AutoEcole.dal.domain.entity.Journey;
import com.example.AutoEcole.dal.domain.entity.User;
import com.example.AutoEcole.dal.repository.ActivityRepository;
import com.example.AutoEcole.dal.repository.DocumentRepository;
import com.example.AutoEcole.dal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepo;
    private final UserRepository userRepo;

    @Override
    public CreateDocumentResponseBody createActivity(CreateDocumentRequestBody request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Création de  réservation
        Document activity = new Document();

        documentRepo.save(activity);
        return new CreateDocumentResponseBody(
                "La création de l'activité s'est bien passée !")
    }
   @Override
    public List<Document> getAllDocuments(){

        return documentRepo.findAll();
    }

    @Override
    public Document getDocumentById(Long id) {
        Document activityById = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Voyage non trouvé"));
        return activityById;
    }

    @Override
    @Transactional
    public boolean update(Long id, Document document) {
        Document activityToUpdate = getDocumentById(id);
        try{
            documentRepo.save(activityToUpdate);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Document> activity = documentRepo.findById(id);
        if (activity.isPresent()) {
            documentRepo.deleteById(id);
            return true;
        }
        return false;  // Booking not found
    }
}
