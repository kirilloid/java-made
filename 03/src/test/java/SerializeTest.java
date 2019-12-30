import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.*;

import java.util.LinkedList;
import java.util.List;

public class SerializeTest {
    final static String json = String.join("\n"
        , "{"
        , "  \"firstName\": \"Иван\","
        , "  \"lastName\": \"Иванов\","
        , "  \"address\": {"
        , "    \"city\": \"Москва\","
        , "    \"postalCode\": \"101101\""
        , "  },"
        , "  \"phoneNumbers\": ["
        , "    \"123-1234-523\","
        , "    \"432-23-232-23\""
        , "  ]"
        , "}");

    final static String xml = String.join("\n"
        , "<Person>"
        , "  <firstName>Иван</firstName>"
        , "  <lastName>Иванов</lastName>"
        , "  <address>"
        , "    <city>Москва</city>"
        , "    <postalCode>101101</postalCode>"
        , "  </address>"
        , "  <phoneNumbers>"
        , "    <1>123-1234-523</1>"
        , "    <2>432-23-232-23</2>"
        , "  </phoneNumbers>"
        , "</Person>");

    private Person createPerson() {
        Address address = new Address("Москва", "101101");
        List<String> phones = new LinkedList<>();
        phones.add("123-1234-523");
        phones.add("432-23-232-23");
        return new Person("Иван", "Иванов", address, phones);
    }
    
    @Test
    public void jsonTest() {
        Person person = createPerson();
        Serializer jsonSerializer = new JSONSerializer();
        assertEquals(jsonSerializer.serialize(person), json);
    }

    @Test
    public void xmlTest() {
        Person person = createPerson();
        Serializer xmlSerializer = new XMLSerializer();
        assertEquals(xmlSerializer.serialize(person), xml);
    }
}

// DTO
class Person {
    Person(String firstName, String lastName, Address address, List<String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
    }
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final List<String> phoneNumbers;
}

class Address {
    Address(String city, String postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }
    private final String city;
    private final String postalCode;
}

