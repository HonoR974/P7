package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.ExamplaireRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    private ExamplaireRepository examplaireRepository;


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
        pret.setProlonger(false);
        pret.setEmail(false);
        pretRepository.save(pret);
        return pret;
    }

    @Override
    public PretDTO givePretDTO(Pret pret)
    {

        System.out.println("\n give pretDTO " +  pret.toString());
        PretDTO pretDTO = new PretDTO();

        String date_debut = pret.getDate_debut().toString();
        String date_fin = pret.getDate_fin().toString();
        String titre = pret.getExamplaire().getLivre().getTitre();

        pretDTO.setId(pret.getId());

        pretDTO.setDate_debut(date_debut);
        pretDTO.setDate_fin(date_fin);

        pretDTO.setUsername(pret.getUser().getUsername());

        //pretDTO.setId_examplaire(pret.getExamplaire().getId());
        pretDTO.setStatut(pret.getStatut().getNom());
        pretDTO.setEnabled(pret.getProlonger());
        pretDTO.setTitre(titre);

        if (pret.getImage() != null)
        {
            System.out.println("\n pret.getImage est different de null ");
            pretDTO.setTitreImage(pret.getImage().getName());
        }


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
        Pret pret = pretRepository.findById(id_pret);

        Statut statut = statutRepository.findByNom("Valider");
        pret.setStatut(statut);

        Examplaire examplaire = pret.getExamplaire();
        examplaire.setEmprunt(true);
        examplaireRepository.save(examplaire);

        Livre livre = examplaire.getLivre();
        pret.setImage(livre.getImage());

        pretRepository.save(pret);

        return pret;
    }

    @Override
    public Pret getPretById(long id_pret) {
        return pretRepository.findById(id_pret);
    }

    @Override
    public void finishPret(long id_pret)
    {
        Pret pret = pretRepository.findById(id_pret);

        Statut statut = statutRepository.findByNom("Fini");
        pret.setStatut(statut);

        Examplaire examplaire = pret.getExamplaire();
        examplaire.setEmprunt(false);

        examplaireRepository.save(examplaire);
        pretRepository.save(pret);
    }

    @Override
    public Pret prolongPret(long id_pret)
    {
        Pret pret = pretRepository.findById(id_pret);
        pret.setProlonger(true);

        LocalDate lastDate = pret.getDate_fin().plusDays(30);
        pret.setDate_fin(lastDate);

        pret.setEmail(false);

        pretRepository.save(pret);
        return pret;
    }
}
