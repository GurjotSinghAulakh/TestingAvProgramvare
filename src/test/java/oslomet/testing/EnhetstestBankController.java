package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    /*------ Hent Transaksjoner ------*/

    // Tester hent Transaksjoner (Logget Inn - OK)
    @Test
    public void hentTransaksjoner_LoggetInn_OK() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon betaling2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);

        Konto konti = new Konto("01010110523", "105010123456",
                720, "Lønnskonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(konti);

        // act
        Konto resultat = bankController.hentTransaksjoner("", "", "");

        // assert
        assertEquals(konti, resultat);
    }

    // Tester hent Transaksjoner (Ikke Logget Inn)
    @Test
    public void hentTransaksjoner_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner("", "", "");

        // assert
        assertNull(resultat);
    }


    /*------ Hent Konti ------*/

    // Tester hent Konti (Logget Inn - OK)
    @Test
    public void hentKonti_LoggetInn_OK()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("12345678901", "105010123456",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("12345678901", "105010123456",
                1000, "Lønnskonto", "NOK", null);

        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("12345678901");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    // Tester hent Konti (Ikke Logget Inn)
    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }


    /*------ Hent Saldi ------*/

    // Tester hent Saldi (Logget Inn - OK)
    @Test
    public void hentSaldi_LoggetInn_OK() {
        // arrange
        List<Konto> konti = new ArrayList<>();

        Konto konto1 = new Konto("12345678901", "105010123456",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("12345678901", "105010123456",
                1000, "Lønnskonto", "NOK", null);

        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    // Tester hent Saldi (Ikke Logget Inn)
    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }


    /*------ Registrer Betaling ------*/

    // Tester registrer Betaling (Logget Inn - OK)
    @Test
    public void registrerBetaling_LoggetInn_OK() {
        // arrange
        Transaksjon betalinger = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(betalinger)).thenReturn("OK");

        // act
        String resultat = bankController.registrerBetaling(betalinger);

        // assert
        assertEquals("OK", resultat);
    }

    // Tester registrer Betaling (Ikke Logget Inn)
    @Test
    public void registrerBetaling_IkkeLoggetInn() {
        // arrange
        Transaksjon betalinger = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.registrerBetaling(betalinger);

        // assert
        assertNull(resultat);
    }


    /*------ Hent Betaling ------*/

    // Tester hent Betalinger (Logget Inn - OK)
    @Test
    public void hentBetalinger_LoggetInn_OK() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon betaling2 = new Transaksjon(2, "20102012345",
                400.4, "2015-03-20", "Skagen", "1",
                "105010123456");

        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(transaksjoner, resultat);
    }

    // Tester hent Betalinger (Ikke Logget Inn)
    @Test
    public void hentBetalinger_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }


    /*------ Utfør Betaling ------*/

    // Tester utfor Betalinger (Logget Inn - OK)
    @Test
    public void utforBetaling_LoggetInn_OK() {
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling1 = new Transaksjon(1, "20102012345",
                100.5, "2015-03-15", "Fjordkraft", "1",
                "105010123456");

        Transaksjon betaling2 = new Transaksjon(2, "20102012345",
                400.4,"2015-03-20", "Skagen","1",
                "105010123456");

        transaksjoner.add(betaling1);
        transaksjoner.add(betaling2);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.utforBetaling(anyInt())).thenReturn("OK");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(transaksjoner.get(0).getTxID());

        // assert
        assertEquals(transaksjoner, resultat);
    }

    // Tester utfor Betalinger (Ikke Logget Inn)
    @Test
    public void utforBetaling_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(12);

        // assert
        assertNull(resultat);
    }


    /*------ Hent Kunde Info ------*/

    // Tester utfor Betalinger (Logget Inn - OK)
    @Test
    public void hentKundeInfo_LoggetInn_OK() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    // Tester utfor Betalinger (Ikke Logget Inn)
    @Test
    public void hentKundeInfo_IkkeloggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }


    /*------ Endre Kunde Info ------*/

    // Tester Endre KundeInfo (Logget Inn - OK)
    @Test
    public void endreKundeInfo_LoggetInn_OK() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    // Tester Endre KundeInfo (Ikke Logget Inn)
    @Test
    public void endreKundeInfo_IkkeLoggetInn() {
        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertNull(resultat);
    }
}
















