package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetsController {
    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhetsController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private MockHttpSession session;

    @Before
    public void initSession(){
        Map<String,Object> attributes = new HashMap<String,Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());

    }

    /*------ Sjekk LoggInn ------*/

    // Tester sjekk LoggInn (Logget Inn - OK)
    @Test
    public void sjekkLoggInn_LoggetInn_OK() {
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901",
                "HeiHei");

        // assert
        assertEquals("OK", resultat);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Personnummer Eller Passord)
    @Test
    public void sjekkLoggInn_FeilPersonnummerEllerPassord() {
        // arrange
         when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn(
                 "Feil i personnummer eller passord");

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678905",
                "HeiHeiHei");

        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Med Regex Personnummer)
    @Test
    public void sjekkLoggInn_FeilMedRegexPersonnummer() {
        // act
        // Kort Personnummer
        String resultat1 = sikkerhetsController.sjekkLoggInn("12345",
                "HeiHei");

        // Langt Personnummer
        String resultat2 = sikkerhetsController.sjekkLoggInn("12345678901234567890",
                "HeiHei");

        // Symbol/tegn som Personnummer
        String resultat3 = sikkerhetsController.sjekkLoggInn("//%%$$##__()",
                "HeiHei");

        // Bokstaver som Personnummer
        String resultat4 = sikkerhetsController.sjekkLoggInn("DuErKul",
                "HeiHei");

        // assert
        assertEquals("Feil i personnummer", resultat1);
        assertEquals("Feil i personnummer", resultat2);
        assertEquals("Feil i personnummer", resultat3);
        assertEquals("Feil i personnummer", resultat4);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Med Regex Passord)
    @Test
    public void sjekkLoggInn_FeilMedRegexPassord() {
        // act
        // Tomt Passord
        String resultat1 = sikkerhetsController.sjekkLoggInn("12345678901",
                "");

        // For langt Passord
        String resultat2 = sikkerhetsController.sjekkLoggInn("12345678901",
                "HeiDuErKul1234567891234567890123456");

        // For kort Passord
        String resultat3 = sikkerhetsController.sjekkLoggInn("12345678901",
                "Du1");

        // assert
        assertEquals("Feil i passord", resultat1);
        assertEquals("Feil i passord", resultat2);
        assertEquals("Feil i passord", resultat3);
    }


    /*------ Logg ut ------*/

    /* Tester LoggUt (Ikke nødvendig siden denne metoden i
     sikkerhetscontrolleren er void ) */
    @Test
    public void LoggUt(){
        //arrange
        session.setAttribute("Innlogget", null);
        sikkerhetsController.loggUt();

        //act
        String resultat = sikkerhetsController.loggetInn();

        //assert
        assertNull(resultat);
    }


    /*------ LoggInn Admin ------*/

    // Tester LoggInn Admin (Logget Inn - OK)
    @Test
    public void LoggInnAdmin_LoggetInn_OK(){
        // arrange
        session.setAttribute("Innlogget", "Admin");

        // act
        String resultat = sikkerhetsController.loggInnAdmin("Admin","Admin");

        // assert
        assertEquals("Logget inn", resultat);
    }

    // Tester LoggInn Admin (Ikke Logget Inn - Feil)
    @Test
    public void LoggInnAdmin_IkkeLoggetInn(){
        // arrange
        session.setAttribute("Innlogget", null);

        // act
        String resultat = sikkerhetsController.loggInnAdmin("","");
        // assert
        assertEquals("Ikke logget inn", resultat);

    }


    /*------ Logget Inn ------*/

    // Tester Logget Inn (Logget Inn - OK)
    @Test
    public void LoggetInn_LoggetInn_OK(){
        // arrange
        session.setAttribute("Innlogget", "12345678901");

        // act
        String resultat = sikkerhetsController.loggetInn();

        // assert
        assertEquals("12345678901", resultat);
    }

    // Tester Logget Inn (Logget Inn - Feil)
    @Test
    public void LoggetInn_IkkeLoggetInn(){
        // arrange
        session.setAttribute(null, null);

        // act
        String resultat = sikkerhetsController.loggetInn();

        // assert
        assertNull(resultat);
    }
}


