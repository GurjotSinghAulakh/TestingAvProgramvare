package oslomet.testing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {
    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    /*------------------------------------- Hent Alle Kunder ------------------------------------*/

    // Tester hent alle kunder (Logget Inn_OK)
    @Test
    public void hentAlleOK(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        Kunde kunde2 = new Kunde("12345678901", "Per",
                "Hansen", "Osloveien 82", "1234",
                "Drammen", "12345678", "HeiHei");

        List<Kunde> kundeList = new ArrayList<>();

        kundeList.add(kunde1);
        kundeList.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKunder()).thenReturn(kundeList);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kundeList, resultat);
    }

    // Tester hent alle kunder (Logget Inn_Feil)
    @Test
    public void hentAlleFeil(){
        // arrage
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    // Tester hent alle kunder (Ikke Logget Inn)
    @Test
    public void hentAlle_IkkeLoggetInn(){
        // arrage
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    /*------------------------------------- Lagre Kunde ------------------------------------*/

    // Tester lagre kunde (Logget Inn_OK)
    @Test
    public void lagreKundeOK(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde(kunde1)).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("OK", resultat);
    }

    // Tester lagre kunde (Logget Inn_Feil)
    @Test
    public void lagreKundeFeil(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270", "Oslo",
                "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    // Tester lagre kunde (Ikke Logget Inn)
    @Test
    public void lagreKunde_IkkeLoggetInn(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    // Tester lagreKunde LoggetInn Feil I Repo (Logget Inn_Feil)
    @Test
    public void lagreKunde_LoggetInn_Feil_I_Repo(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde(kunde1)).thenReturn("Feil");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Feil", resultat);
    }

    /*------------------------------------- Endre Kunde ------------------------------------*/

    // Tester endre kunde (Logget Inn_OK)
    @Test
    public void endreKundeOK(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(kunde1)).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertEquals("OK", resultat);
    }

    // Tester endre kunde (Logget Inn_Feil)
    @Test
    public void endreKundeFeil(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    // Tester endre kunde (Ikke Logget Inn)
    @Test
    public void endreKunde_IkkeLoggetInn(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    // Tester endreKunde LoggetInn Feil I Repo (Logget Inn_Feil)
    @Test
    public void endreKunde_LoggetInn_Feil_I_Repo(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene",
                "Jensen", "Askerveien 22", "3270",
                "Oslo","22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(kunde1)).thenReturn("Feil");

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertEquals("Feil", resultat);
    }

    /*------------------------------------- Slett Kunde ------------------------------------*/

    // Tester slett kunde (Logget Inn_OK)
    @Test
    public void slettKundeOK(){
        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("OK");

        // act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("OK", resultat);
    }

    // Tester slett kunde (Logget Inn_Feil)
    @Test
    public void slettKundeFeil(){
        // arrange
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("Feil");

        // act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("Feil", resultat);
    }

    // Tester slett kunde (Ikke Logget Inn)
    @Test
    public void slettKunde_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("Ikke logget inn", resultat);
    }
}
