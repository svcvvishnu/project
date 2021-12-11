import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class TestGenealogyFamily {

    Genealogy g;

    @Before
    public void setup() {
        g = new Genealogy();
        g.cleanData();
    }

    @Test
    public void testAddPerson() {
        PersonIdentity p = g.addPerson("First");
        Assert.assertEquals("First",g.findName(p));
    }

    @Test(expected = RuntimeException.class)
    public void TestDuplicatePerson() {
        g.addPerson("First");
        g.addPerson("First");
    }

    @Test
    public void testRecordAttribute() {
        PersonIdentity p = g.addPerson("First");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("DOB", "01/01/2001");
        attributes.put("DOD", "12/31/2001");
        attributes.put("Gender", "Male");
        Assert.assertTrue(g.recordAttributes(p, attributes));
    }

    @After
    public void clean() {
        //g.cleanData();
    }

}
