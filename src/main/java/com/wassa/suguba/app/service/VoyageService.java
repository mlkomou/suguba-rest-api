package com.wassa.suguba.app.service;

import com.wassa.suguba.app.constante.UploadPath;
import com.wassa.suguba.app.entity.Aeroport;
import com.wassa.suguba.app.entity.City;
import com.wassa.suguba.app.entity.Country;
import com.wassa.suguba.app.entity.Voyage;
import com.wassa.suguba.app.payload.AeroportPayload;
import com.wassa.suguba.app.payload.UpdateStatut;
import com.wassa.suguba.app.repository.AeroportRepository;
import com.wassa.suguba.app.repository.CityRepository;
import com.wassa.suguba.app.repository.CountryRepository;
import com.wassa.suguba.app.repository.VoyageRepository;
import com.wassa.suguba.authentification.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;
    private final UploadFileService uploadFileService;
    private final SendEmailService sendEmailService;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AeroportRepository aeroportRepository;

    public VoyageService(VoyageRepository voyageRepository, UploadFileService uploadFileService, SendEmailService sendEmailService, CountryRepository countryRepository, CityRepository cityRepository, AeroportRepository aeroportRepository) {
        this.voyageRepository = voyageRepository;
        this.uploadFileService = uploadFileService;
        this.sendEmailService = sendEmailService;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.aeroportRepository = aeroportRepository;
    }

    public Map<String, Object> saveVoyage(Voyage voyage, MultipartFile photo) {
        try {
            voyage.setPath(uploadFileService.uploadFile(photo, UploadPath.VOYAGE_DOWNLOAD_LINK));
            Voyage voyageSaced = voyageRepository.save(voyage);
            if (voyage.getMail() != null) {
                String message = "Votre demande de voyage est en cours de traitement, nous vous contacterons pour la suite. Merci d'avoir choisi SUGUBA.";
                sendEmailService.sendEmailVoyage(voyage.getMail(), "DEMANDE VOYAGE", voyageSaced.getId(), voyageSaced.getPhone(), message);
            }
            return Response.success(voyageSaced, "Voyage enregistrée.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la voyage.");
        }
    }

    public Map<String, Object> updateVoyageWithFile(Voyage voyage, MultipartFile photo) {
        try {
            Optional<Voyage> voyageOptional = voyageRepository.findById(voyage.getId());
            if (voyageOptional.isPresent()) {
                voyage.setPath(uploadFileService.uploadFile(photo, UploadPath.VOYAGE_DOWNLOAD_LINK));
                Voyage voyageSaced = voyageRepository.save(voyage);
                return Response.success(voyageSaced, "Voyage modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Voyage n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la voyage.");
        }
    }

    public Map<String, Object> updateVoyageWithoutFile(Voyage voyage) {
        try {
            Optional<Voyage> voyageOptional = voyageRepository.findById(voyage.getId());
            if (voyageOptional.isPresent()) {
                Voyage voyageSaced = voyageRepository.save(voyage);
                return Response.success(voyageSaced, "Voyage modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Voyage n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la voyage.");
        }
    }

    public Map<String, Object> getVoyagesByPage(int page, int size) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Voyage> voyages = voyageRepository.findAll(paging);
            return Response.success(voyages, "Liste des voyages.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
    public Map<String, Object> updateStatut(UpdateStatut updateStatut) {
        try {
            Optional<Voyage> commandeOptional = voyageRepository.findById(updateStatut.getCommandeId());
            if (commandeOptional.isPresent()) {
                Voyage commande = commandeOptional.get();
                commande.setLastModifiedAt(LocalDateTime.now());
                commande.setStatut(updateStatut.getStatut());
                Voyage commandeSaced = voyageRepository.save(commande);
                return Response.success(commandeSaced, "Commande modifiée.");
            }
            return Response.error(new HashMap<>(), "Cette Commande n'existe pas.");
        } catch (Exception e) {
            return Response.error(e, "Erreur d'enregistrement de la commande.");
        }
    }

    public Map<String, Object> getVoyages() {
        try {
            List<Voyage> voyages = voyageRepository.findAll();
            return Response.success(voyages, "Liste des voyages.");
        } catch (Exception e) {
            return Response.error(e, "Erreur de la récuperation de liste.");
        }
    }
    public Map<String, Object> saveAeroport(List<AeroportPayload> areroprtFinals) {
        System.err.println("airport length: " + areroprtFinals.size());
        try {
            AtomicReference<Aeroport> aeroportFinalSaved = new AtomicReference<>(new Aeroport());
            areroprtFinals.forEach(areroprtFinal -> {
                Optional<Country> countryOptional = countryRepository.findByNameEn(areroprtFinal.getCity().getCountry().getNameEn());
                if (countryOptional.isPresent()) {
//                    Country country = countryOptional.get();
                    Optional<City> cityOptional = cityRepository.findByNameEn(areroprtFinal.getCity().getNameEn());
                    if (cityOptional.isPresent()) {
                        Optional<Aeroport> aeroportOptional = aeroportRepository.findByNameEn(areroprtFinal.getNameEn());
                        if (aeroportOptional.isPresent()) {
                            // airport is present
                            //do nothing
                        } else {
                            Aeroport aeroport = new Aeroport();
                            aeroport.setActive(areroprtFinal.getActive());
                            aeroport.setCity(cityOptional.get());
                            aeroport.setAcceptingComplaints(areroprtFinal.getAcceptingComplaints());
                            aeroport.setElevationFeet(areroprtFinal.getElevationFeet());
                            aeroport.setIata(areroprtFinal.getIata());
                            aeroport.setLatitude(areroprtFinal.getLatitude());
                            aeroport.setLongitude(areroprtFinal.getLongitude());
                            aeroport.setNameEn(areroprtFinal.getNameEn());
                            aeroport.setWebsite(areroprtFinal.getWebsite());
                            aeroport.setNameFr(areroprtFinal.getNameFr());
                            aeroport.setWikipediaLink(areroprtFinal.getWikipediaLink());
                            Aeroport aeroportSaved = aeroportRepository.save(aeroport);
                            aeroportFinalSaved.set(aeroportSaved);

                        }
                    } else {
                        City city = new City();
                        Aeroport aeroport = new Aeroport();
                        city.setCountry(countryOptional.get());
                        city.setNameEn(areroprtFinal.getCity().getNameEn());
                        city.setNameFr(areroprtFinal.getCity().getNameFr());
                        City citySaved = cityRepository.save(city);

                        aeroport.setActive(areroprtFinal.getActive());
                        aeroport.setCity(citySaved);
                        aeroport.setAcceptingComplaints(areroprtFinal.getAcceptingComplaints());
                        aeroport.setElevationFeet(areroprtFinal.getElevationFeet());
                        aeroport.setIata(areroprtFinal.getIata());
                        aeroport.setLatitude(areroprtFinal.getLatitude());
                        aeroport.setLongitude(areroprtFinal.getLongitude());
                        aeroport.setNameEn(areroprtFinal.getNameEn());
                        aeroport.setWebsite(areroprtFinal.getWebsite());
                        aeroport.setNameFr(areroprtFinal.getNameFr());
                        aeroport.setWikipediaLink(areroprtFinal.getWikipediaLink());
                        Aeroport aeroportSaved = aeroportRepository.save(aeroport);
                        aeroportFinalSaved.set(aeroportSaved);

                    }
                } else {
                    Country country = new Country();
                    country.setNameEn(areroprtFinal.getCity().getCountry().getNameEn());
                    country.setNameFr(areroprtFinal.getCity().getCountry().getNameFr());
                    Country countrySaved = countryRepository.save(country);

                    City city = new City();
                    Aeroport aeroport = new Aeroport();
                    city.setCountry(countrySaved);
                    city.setNameEn(areroprtFinal.getCity().getNameEn());
                    city.setNameFr(areroprtFinal.getCity().getNameFr());
                    City citySaved = cityRepository.save(city);

                    aeroport.setActive(areroprtFinal.getActive());
                    aeroport.setCity(citySaved);
                    aeroport.setAcceptingComplaints(areroprtFinal.getAcceptingComplaints());
                    aeroport.setElevationFeet(areroprtFinal.getElevationFeet());
                    aeroport.setIata(areroprtFinal.getIata());
                    aeroport.setLatitude(areroprtFinal.getLatitude());
                    aeroport.setLongitude(areroprtFinal.getLongitude());
                    aeroport.setNameEn(areroprtFinal.getNameEn());
                    aeroport.setWebsite(areroprtFinal.getWebsite());
                    aeroport.setNameFr(areroprtFinal.getNameFr());
                    aeroport.setWikipediaLink(areroprtFinal.getWikipediaLink());
                   Aeroport aeroportSaved = aeroportRepository.save(aeroport);
                    aeroportFinalSaved.set(aeroportSaved);
                }
            });
            return Response.success(aeroportFinalSaved, "Aéroport enregistré");
        } catch (Exception e) {
            System.err.println(e);
            return Response.error(e, "Erreur d'enregistrement de l'aéroport");
        }
    }

    public Map<String, Object> getAirports(int page, int size) {
        try {
            Sort defaultSort = Sort.by(Sort.Direction.DESC, "createdAt");
            Pageable paging = PageRequest.of(page, size, defaultSort);
            Page<Aeroport> aeroports = aeroportRepository.findAll(paging);
          return  Response.success(aeroports, "Liste des aéroprts");
        } catch (Exception e) {
          return   Response.error(e, "Erreur de récuperation");
        }
    }

    public Map<String, Object> searchAirports(String terme) {
        try {
//            Pageable paging = PageRequest.of(page, size);
            List<Aeroport> aeroports = aeroportRepository.realtimeSearch(terme);
            return  Response.success(aeroports, "Liste des aéroprts");
        } catch (Exception e) {
            System.err.println(e);
            return  Response.error(e, "Erreur de récuperation");
        }
    }
}
