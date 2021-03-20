package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Statut;
import com.bibliotheque.model.User;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class PretServiceImpl implements PretService
{

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private ExamplaireService examplaireService;

    @Autowired
    private SecurityService securityService;

    /**
     * Creer un pret avec l'id_examplaire et l'id de l'user grave au jwt
     * Recois l'id de l'examplaire
     * return un pret
     * @param id_examplaire
     * @return
     */
    @Override
    public Pret createPret(Long id_examplaire) {


        String username = securityService.getUsername();

        User user = userRepository.findByUsername(username);

        Statut statut = statutRepository.findByNom("En Creation");

        LocalDate localDate = LocalDate.now();
        LocalDate lastDate = localDate.plusDays(28);
        Pret pret = new Pret();


        pret.setUser(user);
        pret.setExamplaire(examplaireService.getExamplaireById(id_examplaire));


        pret.setStatut(statut);
        pret.setDate_debut(localDate);
        pret.setDate_fin(lastDate);
        pretRepository.save(pret);
        return pret;
    }

    @Override
    public PretDTO givePretDTO(Pret pret)
    {
        PretDTO pretDTO = new PretDTO();

        String date_debut = pret.getDate_debut().toString();
        String date_fin = pret.getDate_fin().toString();

        pretDTO.setId(pret.getId());


        pretDTO.setId_examplaire(pret.getExamplaire().getId());
        pretDTO.setUsername(pret.getUser().getUsername());

        pretDTO.setDate_debut(date_debut);
        pretDTO.setDate_fin(date_fin);

        pretDTO.setStatut(pret.getStatut().getNom());

        System.out.println("\n pretDTO : " + pretDTO.toString());

        return pretDTO;
    }

    /**
     * Valide le pret
     * @param id_pret
     * @return
     */
    @Override
    public Pret validePret(long id_pret)
    {

        Statut statut = statutRepository.findByNom("Valider");

        Pret pret = pretRepository.findById(id_pret);

        pret.setStatut(statut);

        pretRepository.save(pret);
        return pret;
    }
}
