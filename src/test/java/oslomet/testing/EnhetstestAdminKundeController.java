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


    @Test
    public void hentAlleOK(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Oslo","22224444", "HeiHei");
        Kunde kunde2 = new Kunde("12345678901", "Per", "Hansen", "Osloveien 82", "1234","Drammen", "12345678", "HeiHei");

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

    @Test
    public void hentAlleFeil(){

        // arrage
        //denne setningen er den som gir error av mockito.
        when(repository.hentAlleKunder()).thenReturn(null);

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);

    }



    @Test
    public void lagreKundeOK(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Oslo","22224444", "HeiHei");

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);


        // assert
        assertEquals("OK", resultat);
    }


    @Test
    public void lagreKundeFeil(){

        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Oslo","22224444", "HeiHei");

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Feil");

        // act

        String resultat = adminKundeController.lagreKunde(kunde1);


        // assert
        assertEquals("Feil", resultat);

    }


    @Test
    public void endreKundeOK(){

        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Oslo","22224444", "HeiHei");

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertEquals("OK", resultat);
    }


    @Test
    public void endreKundeFeil(){
        // arrage
        Kunde kunde1 = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Oslo","22224444", "HeiHei");

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        // act
        String resultat = adminKundeController.endre(kunde1);

        // assert
        assertEquals("Feil", resultat);


    }


    @Test
    public void slettKundeOK(){

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("OK");

        // act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKundeFeil(){

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKunde(anyString())).thenReturn("Feil");

        // act
        String resultat = adminKundeController.slett("01010110523");

        // assert
        assertEquals("Feil",resultat);

    }



}
