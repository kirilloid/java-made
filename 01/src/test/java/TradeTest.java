import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.Trade;
import ru.mail.TradeReader;
import ru.mail.TradeType;

import java.io.BufferedReader;
import java.io.StringReader;

public class TradeTest {
    @Test
    public void evaluatesExpression() {
        TradeReader tr = new TradeReader();
        Trade actual = tr.read(new BufferedReader(new StringReader("Trade: {\n\"type\": BOND,\n\"price\": 10\n}")));
        Trade expected = new Trade(TradeType.BOND, 10);
        assertEquals(actual, actual);
    }
}
