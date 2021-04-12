package com.bibliotheque.service;

import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BatchServiceImpl implements BatchService{

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ExamplaireRepository examplaireRepository;

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
            pretBatchDTO.setEnvoieEmail(pret.getEmail());

            list.add(pretBatchDTO);
        }

        return list;
    }

    /**
     * Verifie le statut avant de le sauvegarder
     * @param prets
     * @return
     */
    private void verfication(List<PretBatchDTO> prets)
    {
        List<Pret> list = new ArrayList<>();
        Pret pret = new Pret();
        Statut statut = new Statut();

        for (PretBatchDTO pretBatch : prets)
        {
            long id = pretBatch.getId();
            pret =  pretRepository.findById(id);

            //si ils ont un statut different on le save
            if (! pret.getStatut().getNom().equals(pretBatch.getStatut()))
            {

                statut = statutRepository.findByNom(pretBatch.getStatut());
                pret.setStatut(statut);
            }


            pretRepository.save(pret);
        }
    }

    @Override
    public List<PretBatchDTO> getPretEnCours() throws IOException {


        Livre livre = livreRepository.findById(1);


        File file = new File("/resources/static/EtSi.jpg");
        byte[] picInBytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        livre.setImage(picInBytes);

        livreRepository.save(livre);

        Statut statut = statutRepository.findByNom("Valider");
        Statut statut1 = statutRepository.findByNom("Prolonger");
        Statut statut2 = statutRepository.findByNom("A Rendre");


        List<Pret> list = pretRepository.findByStatut(statut);

        list.addAll(pretRepository.findByStatut(statut1));
        list.addAll(pretRepository.findByStatut(statut2));

        return convertForBatch(list);
    }

    @Override
    public void sendPretEnCours(Map<Integer,PretBatchDTO> map)
    {
        List<PretBatchDTO> list = new ArrayList<>();

        for (Map.Entry mapentry : map.entrySet()) {
            System.out.println("clé: "+mapentry.getKey()
                    + " | valeur: " + mapentry.getValue());

            list.add((Integer) mapentry.getKey(),(PretBatchDTO) mapentry.getValue());
        }

        verfication(list);

    }
}
