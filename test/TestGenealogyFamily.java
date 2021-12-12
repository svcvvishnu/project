import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
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

    @Test
    public void testUseCase1() {
        PersonIdentity p = g.addPerson("First");
        PersonIdentity p1 = g.addPerson("Second");
        PersonIdentity p2 = g.addPerson("Third");
        g.recordChild(p, p1);
        g.recordChild(p1, p2);
        Assert.assertEquals(2, g.descendents(p, 3).size());
        Assert.assertEquals(2, g.descendents(p, 2).size());
        Assert.assertEquals(1, g.descendents(p, 1).size());

        Assert.assertEquals(0, g.ancestors(p, 3).size());
        Assert.assertEquals(1, g.ancestors(p2, 1).size());
        Assert.assertEquals(2, g.ancestors(p2, 2).size());
        Assert.assertEquals(2, g.ancestors(p2, 3).size());
    }

    @Test
    public void testFindPerson() {
        PersonIdentity p = g.addPerson("First");
        PersonIdentity p1 = g.addPerson("Second");

        Assert.assertEquals(p, g.findPerson("First"));
        Assert.assertEquals(p1, g.findPerson("Second"));
    }

    @Test(expected = RuntimeException.class)
    public void testFindPersonException() {
        g.findPerson("Second");
    }

    @Test
    public void testFindMedia() {
        FileIdentifier f = g.addMediaFile("FileLocation1");
        FileIdentifier f2 = g.addMediaFile("FileLocation2");

        Assert.assertEquals(f, g.findMediaFIle("FileLocation1"));
        Assert.assertEquals(f2, g.findMediaFIle("FileLocation2"));
    }

    @Test(expected = RuntimeException.class)
    public void testFindMediaException() {
        g.findMediaFIle("FileLocation");
    }

    @Test
    public void testFindName() {
        PersonIdentity p = g.addPerson("First");
        PersonIdentity p1 = g.addPerson("Second");

        Assert.assertEquals("First", g.findName(p));
        Assert.assertEquals("Second", g.findName(p1));
    }

    @Test(expected = RuntimeException.class)
    public void testFindNameException() {
        g.findName(new PersonIdentity(1111));
    }

    @Test
    public void testFindMediaFile() {
        FileIdentifier f = g.addMediaFile("FileLocation1");
        FileIdentifier f2 = g.addMediaFile("FileLocation2");

        Assert.assertEquals("FileLocation1", g.findMediaFile(f));
        Assert.assertEquals("FileLocation2", g.findMediaFile(f2));
    }

    @Test(expected = RuntimeException.class)
    public void testFindMediaFileException() {
        g.findMediaFile(new FileIdentifier(123));
    }

    @Test
    public void testNotesAndReferences() {
        PersonIdentity p = g.addPerson("First");
        g.recordNote(p, "Note1");
        g.recordReference(p, "Reference1");
        g.recordNote(p, "Note2");
        g.recordReference(p, "Reference2");
        g.recordReference(p, "Reference3");
        g.recordNote(p, "Note3");

        List<String> result = g.notesAndReferences(p);
        Assert.assertEquals(6, result.size());
        Assert.assertEquals("Note1", result.get(0));
        Assert.assertEquals("Reference1", result.get(1));
        Assert.assertEquals("Note2", result.get(2));
        Assert.assertEquals("Reference2", result.get(3));
        Assert.assertEquals("Reference3", result.get(4));
        Assert.assertEquals("Note3", result.get(5));
    }

    @Test
    public void testFindMediaByTag() {
        Map<String, String> attrs = new HashMap<>();
        FileIdentifier f = g.addMediaFile("FileLocation");
        attrs.put("date", "01-01-2019");
        g.recordMediaAttributes(f, attrs);
        g.tagMedia(f, "abc");

        FileIdentifier f1 = g.addMediaFile("FileLocation1");
        attrs.clear();
        attrs.put("date", "01-01-2020");
        g.recordMediaAttributes(f1, attrs);
        g.tagMedia(f1, "abc");

        FileIdentifier f2 = g.addMediaFile("FileLocation2");
        attrs.clear();
        attrs.put("date", "01-01-2021");
        g.recordMediaAttributes(f2, attrs);
        g.tagMedia(f2, "abc123");

        Assert.assertEquals(1, g.findMediaByTag("abc", "01-01-2018", "01-02-2019").size());
        Assert.assertEquals(1, g.findMediaByTag("abc", "01-01-2019", "01-02-2019").size());
        Assert.assertEquals(2, g.findMediaByTag("abc", "01-01-2018", "01-02-2022").size());
        Assert.assertEquals(0, g.findMediaByTag("abc11", "01-01-2018", "01-02-2019").size());

        Assert.assertEquals(1, g.findMediaByTag("abc", null, "01-02-2019").size());
        Assert.assertEquals(1, g.findMediaByTag("abc", null, "01-02-2019").size());
        Assert.assertEquals(0, g.findMediaByTag("abc", null, "01-02-2002").size());
        Assert.assertEquals(0, g.findMediaByTag("abc11", null, "01-02-2019").size());

        Assert.assertEquals(2, g.findMediaByTag("abc", "01-01-2018", null).size());
        Assert.assertEquals(1, g.findMediaByTag("abc", "01-01-2020", null).size());
        Assert.assertEquals(0, g.findMediaByTag("abcd", "01-01-2021", null).size());

        Assert.assertEquals(2, g.findMediaByTag("abc", null, null).size());
        Assert.assertEquals(1, g.findMediaByTag("abc123", null, null).size());
        Assert.assertEquals(0, g.findMediaByTag("abc12w", null, null).size());

    }

    @Test
    public void testFindMediaByLocation() {
        Map<String, String> attrs = new HashMap<>();
        FileIdentifier f = g.addMediaFile("FileLocation");
        attrs.put("date", "01-01-2019");
        attrs.put("Location", "Location1");
        g.recordMediaAttributes(f, attrs);
        g.tagMedia(f, "abc");

        FileIdentifier f1 = g.addMediaFile("FileLocation1");
        attrs.clear();
        attrs.put("date", "01-01-2020");
        attrs.put("Location", "Location1");
        g.recordMediaAttributes(f1, attrs);
        g.tagMedia(f1, "abc");

        FileIdentifier f2 = g.addMediaFile("FileLocation2");
        attrs.clear();
        attrs.put("date", "01-01-2021");
        attrs.put("Location", "Location2");
        g.recordMediaAttributes(f2, attrs);
        g.tagMedia(f2, "abc123");

        Assert.assertEquals(1, g.findMediaByLocation("Location1", "01-01-2018", "01-02-2019").size());
        Assert.assertEquals(1, g.findMediaByLocation("Location1", "01-01-2019", "01-02-2019").size());
        Assert.assertEquals(2, g.findMediaByLocation("Location1", "01-01-2018", "01-02-2022").size());
        Assert.assertEquals(0, g.findMediaByLocation("abc11", "01-01-2018", "01-02-2019").size());

        Assert.assertEquals(1, g.findMediaByLocation("Location1", null, "01-02-2019").size());
        Assert.assertEquals(1, g.findMediaByLocation("Location1", null, "01-02-2019").size());
        Assert.assertEquals(0, g.findMediaByLocation("Location1", null, "01-02-2002").size());
        Assert.assertEquals(0, g.findMediaByLocation("abc11", null, "01-02-2019").size());

        Assert.assertEquals(2, g.findMediaByLocation("Location1", "01-01-2018", null).size());
        Assert.assertEquals(1, g.findMediaByLocation("Location1", "01-01-2020", null).size());
        Assert.assertEquals(0, g.findMediaByLocation("abcd", "01-01-2021", null).size());

        Assert.assertEquals(2, g.findMediaByLocation("Location1", null, null).size());
        Assert.assertEquals(1, g.findMediaByLocation("Location2", null, null).size());
        Assert.assertEquals(0, g.findMediaByLocation("abc12w", null, null).size());

    }

    @After
    public void clean() {
        //g.cleanData();
    }

}
