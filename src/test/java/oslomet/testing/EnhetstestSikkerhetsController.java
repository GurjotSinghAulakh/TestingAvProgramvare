package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    /*------------------------------------- Hent Konti ------------------------------------*/

    // Tester sjekk LoggInn (Logget Inn - OK)
    @Test
    public void sjekkLoggInn_OK() {
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");

        // usikker
        session.setAttribute(any(), eq(" "));

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","HeiHeiHei");

        // assert
        assertEquals("OK", resultat);
    }

    // Tester sjekk LoggInn (Logget Inn - Feil)
    @Test
    public void sjekkLoggInn_Feil() {
        // arrange
         when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i personnummer eller passord");

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","HeiHeiHei");

        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Personnummer)
    @Test
    public void sjekkLoggInn_FeilPersonnummer() {
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i personnummer");

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("","HeiHeiHei");

        // assert
        assertEquals("Feil i personnummer", resultat);
    }

    // Tester sjekk LoggInn (Ikke Logget Inn - Feil Passord)
    @Test
    public void sjekkLoggInn_FeilPassord() {
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i passord");

        // act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678901","");

        // assert
        assertEquals("Feil i passord", resultat);
    }

    /*------------------------------------- Logg ut ------------------------------------*/

    /* Tester LoggUt (Ikke nødvendig siden denne metoden i
     sikkerhetscontrolleren er void ) */
    @Test
    public void LoggUt(){
        session.setAttribute("Innlogget", null);
        // ikke ferdig med
        sikkerhetsController.loggUt();
    }

    /*------------------------------------- LoggInn Admin ------------------------------------*/

    // Tester LoggInn Admin (Logget Inn - OK)
    @Test
    public void LoggInnAdmin_OK(){
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Logget inn");

        session.setAttribute("Innlogget", "Admin");

        // act
        String resultat = sikkerhetsController.loggInnAdmin("Admin","Admin");

        // assert
        assertEquals("Logget inn", resultat);
    }

    // Tester LoggInn Admin (Ikke Logget Inn - Feil )
    @Test
    public void LoggInnAdmin_Feil(){
        // arrange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Ikke logget inn");

        session.setAttribute("Innlogget", null);

        // act
        String resultat = sikkerhetsController.loggInnAdmin("","");
        // assert
        assertEquals("Ikke logget inn", resultat);

    }

    /*------------------------------------- Logget Inn ------------------------------------*/

    // Tester Logget Inn (Logget Inn - OK)
    @Test
    public void LoggetInn_OK(){
        // arrange
        session.setAttribute("Innlogget", "12345678901");

        // act
        String resultat = sikkerhetsController.loggetInn();

        // assert
        assertEquals("12345678901", resultat);
    }

    // Tester Logget Inn (Logget Inn - Feil)
    @Test
    public void LoggetInn_Feil(){
        // arrange
        session.setAttribute(null, null);

        // act
        String resultat = sikkerhetsController.loggetInn();

        // assert
        assertNull(resultat);
    }
}


