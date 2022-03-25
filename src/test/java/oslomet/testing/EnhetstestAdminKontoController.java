package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
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


    /*------ Hent Alle Konto Informasjon ------*/

    // Tester hent alle Konto (Logget Inn - OK)
    @Test
    public void hentAlleKonti_LoggetInn_OK(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        List<Konto> kontoList = new ArrayList<>();

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        kontoList.add(konto1);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentAlleKonti()).thenReturn(kontoList);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(kontoList, resultat);
    }

    // Tester hent alle Konto (Ikke Logget Inn)
    @Test
    public void hentAlleKonti_IkkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // assert
        assertNull(resultat);
    }

    /*------ Registrer Konto ------*/

    // Tester registrer konto (Logget Inn - OK)
    @Test
    public void registrerKonto_LoggetInn_OK(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto(konto1)).thenReturn("OK");

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertEquals("OK", resultat);
    }

    // Tester registrer konto (Ikke Logget Inn)
    @Test
    public void registrerKonto_IkkeLoggetInn(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.registrerKonto(konto1);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }

    /*------ Endre Konto ------*/

    // Tester endre konto (Logget Inn - OK)
    @Test
    public void endreKonto_LoggetInn_OK(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");
        // act
        String resultat = adminKontoController.endreKonto(konto1);

        // assert
        assertEquals("OK", resultat);
    }

    // Tester endre konto (Ikke Logget Inn)
    @Test
    public void endreKonto_IkkeLoggetInn(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.endreKonto(konto1);

        // assert
        assertEquals("Ikke innlogget", resultat);
    }

    /*------ Slett Konto ------*/

    // Tester slett konto (Logget Inn - OK)
    @Test
    public void slettKonto_LoggetInn_OK(){
        //arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.slettKonto(anyString())).thenReturn("OK");

        // act
        String resultat = adminKontoController.slettKonto(konto1.getKontonummer());

        // assert
        assertEquals("OK", resultat);
    }

    // Tester slett konto (Ikke Logget Inn)
    @Test
    public void slettKonto_IkkeLoggetInn(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon transaksjon1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon transaksjon2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(transaksjon1);
        transaksjoner.add(transaksjon2);

        Konto konto1 = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKontoController.slettKonto(konto1.getKontonummer());

        // assert
        assertEquals("Ikke innlogget", resultat);
    }
}






