import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.*;

import java.io.BufferedReader;
import java.io.StringReader;

public class TradeTest {
    @Test
    public void caseReader() {
        ITradeReader tr = new TradeReader();
        StringReader stringReader = new StringReader("Trade: {\n\"type\": BOND,\n\"price\": 10\n}");
        ITradeTypeReader ttReader = new TradeTypeCaseReader();
        ITrade actual = tr.read(new BufferedReader(stringReader), ttReader);
        ITrade expected = new Trade(TradeType.BOND, 10);
        assertEquals(actual, expected);
    }

    @Test
    public void valueReader() {
        ITradeReader tr = new TradeReader();
        StringReader stringReader = new StringReader("Trade: {\n\"type\": COMMODITY_SPOT,\n\"price\": 20\n}");
        ITradeTypeReader ttReader = new TradeTypeValueReader();
        ITrade actual = tr.read(new BufferedReader(stringReader), ttReader);
        ITrade expected = new Trade(TradeType.COMMODITY_SPOT, 20);
        assertEquals(actual, expected);
    }

    @Test
    public void factoryCreator() {
        ITradeReader tr = new TradeReaderFactory();
        StringReader stringReader = new StringReader("Trade: {\n\"type\": IR_SWAP,\n\"price\": 25\n}");
        ITradeTypeReader ttReader = new TradeTypeValueReader();
        ITrade actual = tr.read(new BufferedReader(stringReader), ttReader);
        assertEquals(actual.getTradeType(), TradeType.IR_SWAP);
        assertEquals(actual.getPrice(), 25);
    }
}
