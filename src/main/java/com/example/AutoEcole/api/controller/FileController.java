package com.example.AutoEcole.api.controller;

import com.example.AutoEcole.bll.service.CloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*") // adapte si besoin
public class FileController {
}

//    private final CloudinaryService fileService;
//
//    public FileController(CloudinaryService fileService) {
//        this.fileService = fileService;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String url = fileService.uploadFile(file);
//            return ResponseEntity.ok(url);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur d'upload");
//        }
//    }
//}


//@RestController
//public class FileController {
//    private final FileService fileService;
//    private static final String UPLOAD_DIR = "uploads/";
//
//    public FileController(FileService fileService) {
//        this.fileService = fileService;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
//        files.forEach(file -> {
//            try {
//                // Traitez chaque fichier
//                // Par exemple, enregistrez le fichier ou effectuez des validations
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        return ResponseEntity.ok("Fichiers téléchargés avec succès");
//    }
//    @GetMapping("/files/{fileName:.+}")
//    public ResponseEntity<Resource> getFile(@PathVariable String fileName) throws IOException {
//        Resource file = fileService.getFileAsResource(fileName);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);
//    }
//}