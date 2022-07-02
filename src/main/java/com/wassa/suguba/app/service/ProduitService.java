package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Files;
import com.wassa.suguba.app.entity.Produit;
import com.wassa.suguba.app.repository.FilesRepository;
import com.wassa.suguba.app.repository.ProduitRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final UploadFileService uploadFileService;
    private final FilesRepository filesRepository;

    public ProduitService(ProduitRepository produitRepository, UploadFileService uploadFileService, FilesRepository filesRepository) {
        this.produitRepository = produitRepository;
        this.uploadFileService = uploadFileService;
        this.filesRepository = filesRepository;
    }


    public Map<String, Object> saveProduit(Produit produit, List<MultipartFile> files) {
        try {
            System.err.println("file size: " + files.size());
            Produit produitSaved = produitRepository.save(produit);
            files.forEach(multipartFile -> {
                try {
                    System.err.println("filename: " + multipartFile.getOriginalFilename());
                    Files files1 = new Files();
                    files1.setProduit(produitSaved);
                    files1.setPath(uploadFileService.uploadFile(multipartFile, UploadPath.PRODUIT_DOWNLOAD_LINK));
                    files1.setName(multipartFile.getOriginalFilename());
                    files1.setType(multipartFile.getContentType());
                    filesRepository.save(files1);
                } catch (IOException e) {
                    System.err.println(e);
                    e.printStackTrace();
                }
            });
            return Response.success(produitSaved, "Produit enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la produit.");
        }
    }

    public Map<String, Object> updateProduitWithoutFile(Produit produit) {
        try {
            Optional<Produit> produitOptional = produitRepository.findById(produit.getId());
            if (produitOptional.isPresent()) {
                Produit produitSaced = produitRepository.save(produit);
                return Response.success(produitSaced, "Produit modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Produit n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la produit.");
        }
    }

    public Map<String, Object> getProduitsByPage(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Produit> produits = produitRepository.findAll(paging);
            return Response.success(produits, "Liste des produits.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getProduits() {
        try {
            List<Produit> produits = produitRepository.findAll();
            return Response.success(produits, "Liste des produits.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> getProduitsByCategorie(Long catId, int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> produits = produitRepository.findAllByCategorieId(catId, paging);
            return Response.success(produits, "Liste des produits.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }

    public Map<String, Object> deepSearch(String searchTerm, int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Produit> produits1 = produitRepository.deepSearchDescription(searchTerm, paging);
            Page<Produit> produits2 = produitRepository.deepSearchReference(searchTerm, paging);
            Page<Produit> produits3 = produitRepository.deepSearchCategorieName(searchTerm, paging);
            Page<Produit> produits4 = produitRepository.deepSearchName(searchTerm, paging);
            Page<Produit> produits5 = produitRepository.deepSearchPrix(searchTerm, paging);


            List<Produit> arrayList1 = produits1.getContent();
            List<Produit> arrayList2 = produits2.getContent();
            List<Produit> arrayList3 = produits3.getContent();
            List<Produit> arrayList4 = produits4.getContent();
            List<Produit> arrayList5 = produits5.getContent();

            List<Produit> resultatsSearch = new ArrayList<>();

            resultatsSearch.addAll(arrayList1);
            resultatsSearch.addAll(arrayList2);
            resultatsSearch.addAll(arrayList3);
            resultatsSearch.addAll(arrayList4);
            resultatsSearch.addAll(arrayList5);

            return Response.success(resultatsSearch, "Résultats obtenus pour le mot: " + searchTerm);

        } catch (Exception e) {
            return Response.error(e, "Erreur de la recherche, veuillez réessayer plus tard.");
        }
    }
}
