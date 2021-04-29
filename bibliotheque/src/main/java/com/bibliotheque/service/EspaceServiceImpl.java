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

import java.util.ArrayList;
import java.util.List;

@Service
public class EspaceServiceImpl implements EspaceService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Override
    public User getUserById(long id)
    {

        return userRepository.findById(id);
    }

    /**
     * retourne une liste de pret selon l'id user et le statut
     * @param id_user
     * @return
     */
    @Override
    public List<Pret> getListPretByIdUser(long id_user)
    {

        Statut statut = statutRepository.findByNom("Valider");
        Statut statut1 = statutRepository.findByNom("Prolonger");
        Statut statut2 = statutRepository.findByNom("A Rendre");

        User user = userRepository.findById(id_user);

        List<Pret> list = pretRepository.findByUserAndStatut(user,statut);

        list.addAll(pretRepository.findByUserAndStatut(user,statut1));
        list.addAll(pretRepository.findByUserAndStatut(user,statut2));

        return list;

    }

    @Override
    public List<PretDTO> giveListDTO(List<Pret> list)
    {
        List<PretDTO> pretDTOList = new ArrayList<>();

        for(Pret pret : list)
        {

            pretDTOList.add(givePretDTO(pret));
        }

        return pretDTOList;
    }

    @Override
    public PretDTO givePretDTO(Pret pret)
    {
        PretDTO pretDTO = new PretDTO();

        String date_debut = pret.getDate_debut().toString();
        String date_fin = pret.getDate_fin().toString();
        String titre = pret.getExamplaire().getLivre().getTitre();

        pretDTO.setId(pret.getId());

        pretDTO.setDate_debut(date_debut);
        pretDTO.setDate_fin(date_fin);

        pretDTO.setUsername(pret.getUser().getUsername());

         pretDTO.setStatut(pret.getStatut().getNom());

        pretDTO.setEnabled(pret.getProlonger());
        pretDTO.setTitre(titre);

        System.out.println("\n pretDTO : " + pretDTO.toString());

        return pretDTO;
    }

    @Override
    public Pret getPretById(long id)
    {

        return pretRepository.findById(id);
    }
}
