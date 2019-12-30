import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.*;

import java.io.BufferedReader;
import java.io.StringReader;

public class TradeTest {
    @Test
    public void caseReader() {
        ITradeReader tr = new CaseTradeReader();
        StringReader stringReader = new StringReader("Trade: {\n\"type\": BOND,\n\"price\": 10\n}");
        ITrade actual = tr.read(new BufferedReader(stringReader));
        ITrade expected = new Trade(TradeType.BOND, 10);
        assertEquals(actual, expected);
    }

    @Test
    public void enumReader() {
        ITradeReader tr = new EnumTradeReader();
        StringReader stringReader = new StringReader("Trade: {\n\"type\": COMMODITY_SPOT,\n\"price\": 20\n}");
        ITrade actual = tr.read(new BufferedReader(stringReader));
        ITrade expected = new Trade(TradeType.COMMODITY_SPOT, 20);
        assertEquals(actual, expected);
    }
}
