import com.example.AutoEcole.api.model.Devis.CreateDevisRequestBody;
import com.example.AutoEcole.api.model.Devis.CreateDevisResponseBody;
import com.example.AutoEcole.bll.service.DevisService;
import com.example.AutoEcole.bll.service.UserService;
import com.example.AutoEcole.dal.domain.entity.Devis;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/devis")

public class DevisController {
    private final DevisService devisService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateDevisResponseBody> createDevis(@RequestBody CreateDevisRequestBody request){
        return ResponseEntity.ok(devisService.createDevis(request));
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Devis> getAllDevis(){
        return devisService.getAllDevis();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Devis getPlanetById(@PathVariable Long id){
        return devisService.getDevisById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateBooking(
            @PathVariable Long id,
            @RequestBody @Valid CreateDevisRequestBody request) {

        Devis planet = devisService.getDevisById(id);
        if (planet == null) {
            return ResponseEntity.notFound().build();
        }
        // Crée la nouvelle entité Booking avec les nouvelles valeurs, en conservant l'utilisateur existant
        Boolean updatedPlanet = devisService.update(id,request.toEntity(request.entreprise()));

        return ResponseEntity.ok(updatedPlanet);
    }
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('OPERATOR')")
    public boolean deletePlanet(@PathVariable Long id){
        return devisService.delete(id);
    }

}

