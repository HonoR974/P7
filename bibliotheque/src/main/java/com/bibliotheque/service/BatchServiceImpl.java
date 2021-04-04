package com.bibliotheque.service;

import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Statut;
import com.bibliotheque.model.User;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService{

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Return tout les pret a rendre
     * @return
     */
    @Override
    public List<PretBatchDTO> getPretRetard() {

        Statut statut = statutRepository.findByNom("A Rendre");

        List<Pret> list = pretRepository.findByStatut(statut);


        return convertForBatch(list);
    }


    /**
     * Convertion de la liste des prets
     * @param prets
     * @return
     */
    private List<PretBatchDTO> convertForBatch(List<Pret> prets)
    {
        List<PretBatchDTO> list = new ArrayList<>();


        for (Pret pret : prets)
        {
            System.out.println("\n pret " + pret.getId());

            PretBatchDTO pretBatchDTO = new PretBatchDTO();

            pretBatchDTO.setId(pret.getId());
            pretBatchDTO.setDate_debut(pret.getDate_debut().toString());
            pretBatchDTO.setDate_fin(pret.getDate_fin().toString());
            pretBatchDTO.setStatut(pret.getStatut().getNom());
            pretBatchDTO.setUsername(pret.getUser().getUsername());
            pretBatchDTO.setEmail(pret.getUser().getEmail());
            pretBatchDTO.setTitre(pret.getExamplaire().getLivre().getTitre());

            list.add(pretBatchDTO);
        }

        return list;
    }
}
