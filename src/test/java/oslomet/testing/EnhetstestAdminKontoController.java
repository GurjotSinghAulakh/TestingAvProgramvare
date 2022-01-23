package oslomet.testing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    /*------------------------------------- Hent Alle Konto Informasjon ------------------------------------*/

    // Tester hent AllKonti (Logget Inn - OK)

    @Test
    public void hentAlleKonti_LoggetInn_OK(){
        // arrage
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);

        List<Konto> kontoList = new ArrayList<>();

        kontoList.add(konto1);
        kontoList.add(konto2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentAlleKonti()).thenReturn(kontoList);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(kontoList, resultat);
    }

    // Tester hent AllKonti (Logget Inn - Feil)

    @Test
    public void hentAlleKonti_LoggetInn_Feil() {

        // arrage
        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.hentAlleKonti()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    // Tester hent AllKonti (Ikke Logget Inn)
    @Test
    public void hentAlleKonti_IkkeLoggetInn(){

        // arrage
        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.hentAlleKonti()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);

    }

    /*------------------------------------- Registrer Konto ------------------------------------*/


    // Tester registrer konto  (Logget Inn - OK)
    @Test
    public void registrerKontoOK(){
        // arrage
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertEquals("OK", resultat);

    }

    // Tester registrer konto  (Logget Inn - Feil)
    @Test
    public void registrerKontoFeil(){
        // arrage
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto(any(Konto.class))).thenReturn("Feil");

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertEquals("Feil", resultat);

    }

    // Tester registrer konto  (Ikke Logget Inn)
    @Test
    public void registrerKonto_IkkeLoggetInn(){
        // arrage
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.registrerKonto(any(Konto.class))).thenReturn("Ikke innlogget");

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertEquals("Ikke innlogget", resultat);

    }


    /*------------------------------------- Endre Konto ------------------------------------*/

    // Tester endre konto  (Logget Inn - OK)

    @Test
    public void endreKonto_OK(){
        // arrage
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");


        // act
        String resultat = adminKontoController.endreKonto(konto1);

        // assert
        assertEquals("OK", resultat);

    }

    // Tester endre konto  (Logget Inn - Feil)
    @Test
    public void endreKontoFeil(){
        // arrage
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.endreKonto(any(Konto.class))).thenReturn("Feil");

        // act
        String resultat = adminKontoController.endreKonto(konto1);

        // assert
        assertEquals("Feil", resultat);
    }

    // Tester endre konto  (Ikke Logget Inn)
    @Test
    public void endreKonto_IkkeLoggetInn(){
        // arrage
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.endreKonto(any(Konto.class))).thenReturn("Ikke innlogget");

        // act
        String resultat = adminKontoController.endreKonto(konto1);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }


    /*------------------------------------- Slett Konto ------------------------------------*/

    // Tester slett konto  (Logget Inn - OK)
    @Test
    public void slettKontoOK(){
        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.slettKonto(anyString())).thenReturn("OK");


        // act
        String resultat = adminKontoController.slettKonto("105010123456");

        // assert
        assertEquals("OK", resultat);

    }


    // Tester slett konto  (Logget Inn - Feil)
    @Test
    public void slettKontoFeil(){
        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.slettKonto(anyString())).thenReturn("Feil");

        // act
        String resultat = adminKontoController.slettKonto("105010123456");


        // assert
        assertEquals("Feil", resultat);

    }

    // Tester slett konto  (Ikke Logget Inn)
    @Test
    public void slettKonto_IkkeLoggetInn(){
        // denne skal være med, uten den så får vi en error for at man "ikke er logget inn".
        when(sjekk.loggetInn()).thenReturn(null);

        when(repository.slettKonto(anyString())).thenReturn("Ikke innlogget");

        // act
        String resultat = adminKontoController.slettKonto("105010123456");


        // assert
        assertEquals("Ikke innlogget", resultat);

    }



}