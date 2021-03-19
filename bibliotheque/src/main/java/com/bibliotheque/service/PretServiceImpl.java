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

        System.out.println("\n createPret service" );

        String username = securityService.getUsername();

        System.out.println("\n user : " + username );

        User user = userRepository.findByUsername(username);

        System.out.println("\n user : " + user.toString() );

        Pret pret = new Pret();

        pret.setUser(user);
        pret.setExamplaire(examplaireService.getExamplaireById(id_examplaire));

        Statut statut = statutRepository.findByNom("En Creation");
        pret.setStatut(statut);

        pretRepository.save(pret);
        return pret;
    }

    @Override
    public PretDTO givePretDTO(Pret pret)
    {
        PretDTO pretDTO = new PretDTO();

        pretDTO.setId(pret.getId());


        pretDTO.setId_examplaire(pret.getExamplaire().getId());
        pretDTO.setId_user(pret.getUser().getId());

        pretDTO.setDate_debut(pret.getDate_debut());
        pretDTO.setDate_fin(pret.getDate_fin());

        pretDTO.setStatut(pret.getStatut().getNom());

        System.out.println("\n pretDTO : " + pretDTO.toString());

        return pretDTO;
    }
}
