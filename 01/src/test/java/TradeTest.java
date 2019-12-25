import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.*;

import java.io.BufferedReader;
import java.io.StringReader;

public class TradeTest {
    @Test
    public void caseReader() {
        TradeReader tr = new TradeReader();
        StringReader stringReader = new StringReader("Trade: {\n\"type\": BOND,\n\"price\": 10\n}");
        ITradeTypeReader ttReader = new TradeTypeCaseReader();
        Trade actual = tr.read(new BufferedReader(stringReader), ttReader);
        Trade expected = new Trade(TradeType.BOND, 10);
        assertEquals(actual, expected);
    }

    @Test
    public void valueReader() {
        TradeReader tr = new TradeReader();
        StringReader stringReader = new StringReader("Trade: {\n\"type\": COMMODITY_SPOT,\n\"price\": 20\n}");
        ITradeTypeReader ttReader = new TradeTypeValueReader();
        Trade actual = tr.read(new BufferedReader(stringReader), ttReader);
        Trade expected = new Trade(TradeType.COMMODITY_SPOT, 20);
        assertEquals(actual, expected);
    }
}
