package com.wassa.suguba.app.service;

import com.wassa.suguba.app.entity.Compteur;
import com.wassa.suguba.app.repository.CompteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CompteurService {
    private final CompteurRepository compteurRepository;

    public CompteurService(CompteurRepository compteurRepository) {
        this.compteurRepository = compteurRepository;
    }

    public Integer getNumberMax() {
        Integer defaultCompteur = 1;
        Integer compteurToSave;
        System.out.println("tag: " + defaultCompteur);
        Integer nomberMax = compteurRepository.findMaxNumber();
        if(nomberMax != null) {
            System.out.println("tag != null");
            compteurToSave = nomberMax + 1;
            Compteur compteur = new Compteur();
            compteur.setNombre(compteurToSave);
            compteurRepository.save(compteur);
            System.out.println("compteurToSave " + compteurToSave);
            return  compteurToSave;
        } else {
            System.out.println("tag == null");
            compteurToSave = defaultCompteur;
            Compteur compteur = new Compteur();
            compteur.setNombre(compteurToSave);
            compteurRepository.save(compteur);
            return compteurToSave;
        }

    }
}
