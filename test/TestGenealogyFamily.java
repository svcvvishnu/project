import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;


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

    @Test
    public void testAddPersonSpecialCharacters() {
        PersonIdentity p = g.addPerson("First123");
        Assert.assertEquals("First123",g.findName(p));

        p = g.addPerson("First_%$");
        Assert.assertEquals("First_%$",g.findName(p));

        p = g.addPerson("First Name");
        Assert.assertEquals("First Name",g.findName(p));
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
        attributes.put("DOB", "01-01-2001");
        attributes.put("DOD", "12-31-2001");
        attributes.put("Gender", "Male");
        Assert.assertTrue(g.recordAttributes(p, attributes));
    }

    @Test
    public void testRecordAttributeOccupation() {
        PersonIdentity p = g.addPerson("First");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("Occupation", "XYZ");
        Assert.assertTrue(g.recordAttributes(p, attributes));

        attributes.put("Occupation", "XYZ123");
        Assert.assertTrue(g.recordAttributes(p, attributes));

        attributes.put("Occupation", "XYZ_/");
        Assert.assertTrue(g.recordAttributes(p, attributes));

        attributes.put("Occupation", "XYZ ABC");
        Assert.assertTrue(g.recordAttributes(p, attributes));
    }


    @Test
    public void testAncestorsAndDescendents() {
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
        Assert.assertTrue(g.recordNote(p, "Note1"));
        Assert.assertTrue(g.recordReference(p, "Reference1"));
        Assert.assertTrue(g.recordNote(p, "Note2"));
        Assert.assertTrue(g.recordReference(p, "Reference2"));
        Assert.assertTrue(g.recordReference(p, "Reference3"));
        Assert.assertTrue(g.recordNote(p, "Note3"));

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
    public void testMediaRecordAttrsCity() {
        Map<String, String> attrs = new HashMap<>();
        FileIdentifier f = g.addMediaFile("FileLocation");
        attrs.put("City", "ABC");
        Assert.assertTrue(g.recordMediaAttributes(f, attrs));

        attrs.put("City", "ABC121");
        Assert.assertTrue(g.recordMediaAttributes(f, attrs));

        attrs.put("City", "ABC_");
        Assert.assertTrue(g.recordMediaAttributes(f, attrs));

        attrs.put("City", "ABC XYX");
        Assert.assertTrue(g.recordMediaAttributes(f, attrs));

    }

        @Test
    public void testFindMediaByTag() {
        Map<String, String> attrs = new HashMap<>();
        FileIdentifier f = g.addMediaFile("FileLocation");
        attrs.put("Date", "01-01-2019");
        Assert.assertTrue(g.recordMediaAttributes(f, attrs));
        Assert.assertTrue(g.tagMedia(f, "abc"));

        FileIdentifier f1 = g.addMediaFile("FileLocation1");
        attrs.clear();
        attrs.put("Date", "01-01-2020");
        Assert.assertTrue(g.recordMediaAttributes(f1, attrs));
        Assert.assertTrue(g.tagMedia(f1, "abc"));

        FileIdentifier f2 = g.addMediaFile("FileLocation2");
        attrs.clear();
        attrs.put("Date", "01-01-2021");
        Assert.assertTrue(g.recordMediaAttributes(f2, attrs));
        Assert.assertTrue(g.tagMedia(f2, "abc123"));

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
        attrs.put("Date", "01-01-2019");
        attrs.put("Location", "Location1");
        g.recordMediaAttributes(f, attrs);
        g.tagMedia(f, "abc");

        FileIdentifier f1 = g.addMediaFile("FileLocation1");
        attrs.clear();
        attrs.put("Date", "01-01-2020");
        attrs.put("Location", "Location1");
        g.recordMediaAttributes(f1, attrs);
        g.tagMedia(f1, "abc");

        FileIdentifier f2 = g.addMediaFile("FileLocation2");
        attrs.clear();
        attrs.put("Date", "01-01-2021");
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

    @Test
    public void testFindBiologicalFamilyMedia() {
        PersonIdentity p1 = g.addPerson("Person1");
        PersonIdentity p2 = g.addPerson("Person2");
        PersonIdentity p3 = g.addPerson("Person3");

        FileIdentifier f1 = g.addMediaFile("FileLocation");
        FileIdentifier f2 = g.addMediaFile("FileLocation1");
        FileIdentifier f3 = g.addMediaFile("FileLocation2");

        Map<String, String> attrs = new HashMap<>();
        attrs.put("Date", "01-01-2019");
        g.recordMediaAttributes(f1, attrs);

        attrs.clear();
        attrs.put("Date", "01-01-2020");
        g.recordMediaAttributes(f2, attrs);

        attrs.clear();
        attrs.put("Date", "01-01-2021");
        g.recordMediaAttributes(f3, attrs);

        List<PersonIdentity> people = new ArrayList<>();
        people.add(p1);
        Assert.assertTrue(g.peopleInMedia(f1, people));

        people.clear();
        people.add(p1);
        people.add(p2);
        Assert.assertTrue(g.peopleInMedia(f2, people));

        people.clear();
        people.add(p1);
        people.add(p2);
        people.add(p3);
        Assert.assertTrue(g.peopleInMedia(f3, people));

        Assert.assertTrue(g.recordChild(p3, p1));
        Assert.assertTrue(g.recordChild(p3,p2));
        List<FileIdentifier> result = g.findBiologicalFamilyMedia(p3);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(f1, result.get(0));
        Assert.assertEquals(f2, result.get(1));
    }

    @Test
    public void testBiologicalRelation() {
        PersonIdentity A = g.addPerson("A");
        PersonIdentity B = g.addPerson("B");
        PersonIdentity C = g.addPerson("C");
        PersonIdentity D = g.addPerson("D");
        PersonIdentity E = g.addPerson("E");
        PersonIdentity F = g.addPerson("F");
        PersonIdentity G = g.addPerson("G");
        PersonIdentity H = g.addPerson("H");
        PersonIdentity I = g.addPerson("I");
        PersonIdentity J = g.addPerson("J");
        PersonIdentity K = g.addPerson("K");
        PersonIdentity L = g.addPerson("L");
        PersonIdentity M = g.addPerson("M");

        g.recordPartnering(J, M);

        Assert.assertTrue(g.recordChild(J,D));
        Assert.assertTrue(g.recordChild(J,I));

        Assert.assertTrue(g.recordChild(D,A));
        Assert.assertTrue(g.recordChild(D,B));
        Assert.assertTrue(g.recordChild(D,C));

        Assert.assertTrue(g.recordChild(I,G));
        Assert.assertTrue(g.recordChild(I,H));

        Assert.assertTrue(g.recordChild(G,E));
        Assert.assertTrue(g.recordChild(G,F));

        Assert.assertTrue(g.recordChild(M,I));
        Assert.assertTrue(g.recordChild(M,L));

        Assert.assertTrue(g.recordChild(L,K));


        Assert.assertEquals(new BiologicalRelation(0,0), g.findRelation(A, B));
        Assert.assertEquals(new BiologicalRelation(1,1), g.findRelation(A, E));
        Assert.assertEquals(new BiologicalRelation(-1,3), g.findRelation(E, J));
        Assert.assertEquals(new BiologicalRelation(0,1), g.findRelation(F, H));
        Assert.assertEquals(new BiologicalRelation(-10,-10), g.findRelation(C, K));
    }

    @Test
    public void testMarriage() {
        PersonIdentity p1 = g.addPerson("Partner1");
        PersonIdentity p2 = g.addPerson("Partner2");

        PersonIdentity p3 = g.addPerson("Child1");

        Assert.assertTrue(g.recordChild(p1,p3));
        Assert.assertFalse(g.recordChild(p2, p3));
        g.recordPartnering(p1,p2);

        Assert.assertTrue(g.recordChild(p2, p3));
        Assert.assertTrue(g.recordDissolution(p1,p2));
        Assert.assertFalse(g.recordChild(p2, p3));
        Assert.assertFalse(g.recordDissolution(p1, p2));
    }
    @After
    public void clean() {
        //g.cleanData();
    }

}
