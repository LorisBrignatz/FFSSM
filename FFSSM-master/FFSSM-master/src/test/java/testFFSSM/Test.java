package testFFSSM;

import FFSSM.*;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


public class Test {
    Club NageMinot;
    Site Teunoipas, Alésaute;
    Moniteur Patoche;
    Plongee Ricard, Pastis;
    Plongeur Dédé, Philou;
    Licence licenceDédé, licencePhilou;

    public void setUp(){
        NageMinot = new Club(Patoche, "NageMinot", "0607080910");
        Teunoipas = new Site("Teunoipas", "Allez faire trempette");
        Alésaute = new Site("Alésaute", "Nager avec les petits poissons");
        Patoche = new Moniteur("51", "Lapoche", "Patoche", "24 rue du pastaga", "0745896521", LocalDate.of(1985, 5, 17), 5, 5648);
        Ricard = new Plongee(Teunoipas, Patoche, LocalDate.of(2022, 12, 13), 2, 30);
        Pastis = new Plongee(Alésaute, Patoche, LocalDate.of(2020, 12, 13), 5, 45);
        Dédé = new Plongeur("47", "Lamire", "Loukas", "51 rue du Ricard", "0654125632", LocalDate.of(2002, 11, 7), 1);
        Philou = new Plongeur("568", "Pichard", "Nicolas", "52 rue de la Boiteuse", "0787962314", LocalDate.of(2000, 5, 30), 2);
        licenceDédé = new Licence(Dédé, "51452", LocalDate.of(2022, 5, 1), NageMinot);
        licencePhilou = new Licence(Philou, "65895", LocalDate.of(2016, 5, 17), NageMinot);
    }

    @org.junit.jupiter.api.Test
    public void testOrganisePlongee(){
        NageMinot.organisePlongee(Ricard);
        assertEquals(Ricard, NageMinot.plongeeOragnisees.get(0),
                "La plongée n'a pas été ajoutée");
    }

    @org.junit.jupiter.api.Test
    public void testAjouteParticipant(){
        Ricard.ajouteParticipant(Dédé);
        assertEquals(Dédé, Ricard.listeParticipants.get(0),
                "Le plongeur n'a pas été ajouté à la plongée");
    }

    @org.junit.jupiter.api.Test
    public void testEstValide(){
        assertTrue(licenceDédé.estValide(LocalDate.now()), "La licence n'est pas valide");
    }

    @org.junit.jupiter.api.Test
    public void testEmployeurActuel(){
        Patoche.nouvelleEmbauche(NageMinot, LocalDate.of(2022, 1, 1));
        assertEquals(Patoche.employeurActuel().get() , NageMinot, "L'employeur actuel est le club NageMinot");
    }

    @org.junit.jupiter.api.Test
    public void testTerminerEmbauche(){
        Patoche.nouvelleEmbauche(NageMinot, LocalDate.of(2022, 1, 1));
        Patoche.embauche.terminer(LocalDate.now());
        assertTrue(Patoche.embauche.estTerminee(), "L'embauche de Patoche est terminée");
    }

    @org.junit.jupiter.api.Test
    public void testEstConforme(){
        Dédé.ajouteLicence("51452", LocalDate.of(2022, 5, 1), NageMinot);
        Philou.ajouteLicence("65895", LocalDate.of(2016, 5, 17), NageMinot);
        Ricard.ajouteParticipant(Dédé);
        Ricard.ajouteParticipant(Philou);
        assertFalse(Ricard.estConforme(),  "La plongée n'est pas conforme car la licence de Philou ne l'est pas");
    }

    @org.junit.jupiter.api.Test
    public void testAjouteLicence(){
        Philou.ajouteLicence("65895", LocalDate.now(), NageMinot);
        assertEquals(Philou.licence.getDelivrance(), LocalDate.now(), "L'attribution de la licence a rencontré une erreur");
    }

    @org.junit.jupiter.api.Test
    public void testplongeesNonConformes(){
        Dédé.ajouteLicence("51452", LocalDate.of(2022, 7, 15), NageMinot);
        Philou.ajouteLicence("65895", LocalDate.of(2022, 8, 2), NageMinot);
        Ricard.ajouteParticipant(Dédé);
        Pastis.ajouteParticipant(Philou);
        NageMinot.organisePlongee(Ricard);
        NageMinot.organisePlongee(Pastis);
        assertTrue(NageMinot.plongeesNonConformes().contains(Pastis),
                "La plongée Pastis ne doit pas être conforme");
    }

    @org.junit.jupiter.api.Test
    public void testNouvelleEmbauche(){
        Patoche.nouvelleEmbauche(NageMinot, LocalDate.of(2022, 1, 1));
        assertTrue(Patoche.emplois().contains(Patoche.embauche));
    }

}
