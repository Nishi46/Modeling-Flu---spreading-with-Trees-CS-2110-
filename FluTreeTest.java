package a4;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/** @author david gries */
public class FluTreeTest {

    private static Network n;
    private static Person[] people;
    private static Person personA;
    private static Person personB;
    private static Person personC;
    private static Person personD;
    private static Person personE;
    private static Person personF;
    private static Person personG;
    private static Person personH;
    private static Person personI;
    private static Person personJ;
    private static Person personK;
    private static Person personL;

    /** */
    @BeforeClass
    public static void setup() {
        n= new Network();
        people= new Person[] { new Person("A", 0, n),
                new Person("B", 0, n), new Person("C", 0, n),
                new Person("D", 0, n), new Person("E", 0, n), new Person("F", 0, n),
                new Person("G", 0, n), new Person("H", 0, n), new Person("I", 0, n),
                new Person("J", 0, n), new Person("K", 0, n), new Person("L", 0, n)
        };
        personA= people[0];
        personB= people[1];
        personC= people[2];
        personD= people[3];
        personE= people[4];
        personF= people[5];
        personG= people[6];
        personH= people[7];
        personI= people[8];
        personJ= people[9];
        personK= people[10];
        people[10]= personK;
        personL= people[11];
    }

    /** * */
    @Test
    public void testBuiltInGetters() {
        FluTree st= new FluTree(personB);
        assertEquals("B", toStringBrief(st));
    }

    // A.sh(B, C) = A
    // A.sh(D, F) = B
    // A.sh(D, I) = B
    // A.sh(H, I) = H
    // A.sh(D, C) = A
    // B.sh(B, C) = null
    // B.sh(I, E) = B

    /** Create a FluTree with structure A[B[D E F[G[H[I]]]] C] <br>
     * This is the tree
     *
     * <pre>
     *            A
     *          /   \
     *         B     C
     *       / | \
     *      D  E  F
     *            |
     *            G
     *            |
     *            H
     *            |
     *            I
     * </pre>
     */
    private FluTree makeTree1() {
        FluTree dt= new FluTree(personA); // A
        dt.insert(personB, personA); // A, B
        dt.insert(personC, personA); // A, C
        dt.insert(personD, personB); // B, D
        dt.insert(personE, personB); // B, E
        dt.insert(personF, personB); // B, F
        dt.insert(personG, personF); // F, G
        dt.insert(personH, personG); // G, H
        dt.insert(personI, personH); // H, I
        return new FluTree(dt);
    }

    /** test a call on makeTree1(). */
    @Test
    public void testMakeTree1() {
        FluTree dt= makeTree1();
        assertEquals("A[B[D E F[G[H[I]]]] C]", toStringBrief(dt));
    }

    /** */
    @Test
    public void test1Insert() {
        FluTree st= new FluTree(personB);

        // Test insert in the root
        FluTree dt2= st.insert(personC, personB);
        assertEquals("B[C]", toStringBrief(st)); // test tree
        assertEquals(personC, dt2.rootPerson());  // test return value

        FluTree dt3= st.insert(personD, personC);
        assertEquals("B[C[D]]", toStringBrief(st));
        assertEquals(personD, dt3.rootPerson());

        FluTree dt4= st.insert(personE, personB);
        assertEquals("B[C[D] E]", toStringBrief(st));
        assertEquals(personE, dt4.rootPerson());

        try {
            st.insert(null, personC);
        } catch (IllegalArgumentException iae) {
        }
        try {
            st.insert(personC, null);
        } catch (IllegalArgumentException iae) {
        }
        try {
            st.insert(personC, personB);
        } catch (IllegalArgumentException iae) {
        }
        try {
            st.insert(personC, personI);
        } catch (IllegalArgumentException iae) {
        }
        FluTree dt5= st.insert(personG, personE);
        assertEquals("B[C[D] E[G]]", toStringBrief(st));
        assertEquals(personG, dt5.rootPerson());

        FluTree dt6= st.insert(personF, personC);
        assertEquals("B[C[D F] E[G]]", toStringBrief(st));
        assertEquals(personF, dt6.rootPerson());

        FluTree dt7= st.insert(personA, personE);
        assertEquals("B[C[D F] E[A G]]", toStringBrief(st));
        assertEquals(personA, dt7.rootPerson());

        FluTree dt8= st.insert(personH, personA);
        assertEquals("B[C[D F] E[A[H] G]]", toStringBrief(st));
        assertEquals(personH, dt8.rootPerson());

        FluTree dt9= st.insert(personI, personH);
        assertEquals("B[C[D F] E[A[H[I]] G]]", toStringBrief(st));
        assertEquals(personI, dt9.rootPerson());
    }

    /** */
    @Test
    public void test2size() {

        FluTree st= new FluTree(personB);
        assertEquals(1, st.size());
        st.insert(personC, personB);
        assertEquals(2, st.size());
        st.insert(personD, personC);
        assertEquals(3, st.size());
        st.insert(personE, personB);
        assertEquals(4, st.size());
        st.insert(personG, personE);
        assertEquals(5, st.size());
        st.insert(personF, personC);
        assertEquals(6, st.size());
        st.insert(personA, personE);
        assertEquals(7, st.size());
        st.insert(personH, personA);
        assertEquals(8, st.size());
        st.insert(personI, personH);
        assertEquals(9, st.size());

    }

    /** */
    @Test
    public void test3contains() {
        FluTree st= new FluTree(personB);
        assertEquals(true, st.contains(personB));
        st.insert(personC, personB);
        assertEquals(true, st.contains(personB));
        st.insert(personD, personC);
        assertEquals(true, st.contains(personC));
        st.insert(personE, personB);
        assertEquals(true, st.contains(personD));
        st.insert(personG, personE);
        assertEquals(true, st.contains(personC));
        st.insert(personF, personC);
        assertEquals(true, st.contains(personE));
        st.insert(personA, personE);
        assertEquals(true, st.contains(personF));
        st.insert(personH, personA);
        assertEquals(true, st.contains(personA));
        assertEquals(true, st.contains(personH));
        assertEquals(false, st.contains(personI));

    }

    /** */
    @Test
    public void test4depth() {
        FluTree st= new FluTree(personB);
        st.insert(personC, personB);
        st.insert(personD, personC);
        st.insert(personE, personB);
        st.insert(personG, personE);
        st.insert(personF, personC);
        st.insert(personA, personE);
        st.insert(personH, personA);
        st.insert(personI, personH);
        assertEquals(0, st.depth(personB));
        assertEquals(1, st.depth(personC));
        assertEquals(1, st.depth(personE));
        assertEquals(2, st.depth(personD));
        assertEquals(2, st.depth(personF));
        assertEquals(2, st.depth(personG));
        assertEquals(2, st.depth(personA));
        assertEquals(3, st.depth(personH));
        assertEquals(4, st.depth(personI));
    }

    /** */
    @Test
    public void test5WidthAtDepth() {
        FluTree st= new FluTree(personA);
        assertEquals(1, st.widthAtDepth(0));
        st.insert(personB, personA);
        st.insert(personC, personA);
        st.insert(personD, personB);
        st.insert(personE, personC);
        st.insert(personF, personC);
        st.insert(personG, personE);
        assertEquals(1, st.widthAtDepth(0));
        assertEquals(3, st.widthAtDepth(2));
        assertEquals(1, st.widthAtDepth(3));
        assertEquals(2, st.widthAtDepth(1));
        assertEquals(0, st.widthAtDepth(4));
        try {
            st.widthAtDepth(-1);
        } catch (IllegalArgumentException iae) {
        }
    }

    @Test
    public void test6FluRouteTo() {
        FluTree st= new FluTree(personB);
        List<Person> route= st.fluRouteTo(personB);
        assertEquals("[B]", getNames(route));
        st.insert(personC, personB);
        st.insert(personD, personC);
        st.insert(personE, personB);
        st.insert(personG, personE);
        st.insert(personF, personC);
        st.insert(personA, personE);
        st.insert(personH, personA);
        st.insert(personI, personH);
        assertEquals("[B, C]", getNames(st.fluRouteTo(personC)));
        assertEquals("[B, C, D]", getNames(st.fluRouteTo(personD)));
        assertEquals("[B, C, F]", getNames(st.fluRouteTo(personF)));
        assertEquals("[B, E, G]", getNames(st.fluRouteTo(personG)));
        assertEquals("[B, E, A, H, I]", getNames(st.fluRouteTo(personI)));
    }

    /** Return the names of Persons in sp, separated by ", " and delimited by [ ]. Precondition: No
     * name is the empty string. */
    private String getNames(List<Person> sp) {
        String res= "[";
        for (Person p : sp) {
            if (res.length() > 1) res= res + ", ";
            res= res + p.name();
        }
        return res + "]";
    }

    /** */
    @Test
    public void test7commonAncestor() {
        FluTree st= new FluTree(personB);
        st.insert(personC, personB);
        Person p= st.commonAncestor(personC, personC);
        assertEquals(personC, p);
        st.insert(personD, personC);
        st.insert(personE, personB);
        st.insert(personG, personE);
        st.insert(personF, personC);
        st.insert(personA, personE);
        st.insert(personH, personA);
        st.insert(personI, personH);
        assertEquals(null, st.commonAncestor(null, personA));
        assertEquals(null, st.commonAncestor(personB, null));
        assertEquals(null, st.commonAncestor(personC, personL));
        assertEquals(null, st.commonAncestor(personK, personE));
        assertEquals(personC, st.commonAncestor(personD, personF));

        assertEquals(null, st.commonAncestor(null, null));
    }

    /** */
    @Test
    public void test8equals() {
        FluTree treeB1= new FluTree(personB);
        FluTree treeB2= new FluTree(personB);
        assertEquals(true, treeB1.equals(treeB2));
    }

    // ===================================
    // ==================================

    /** Return a representation of this tree. This representation is: <br>
     * (1) the name of the Person at the root, followed by <br>
     * (2) the representations of the children <br>
     * . (in alphabetical order of the children's names). <br>
     * . There are two cases concerning the children.
     *
     * . No children? Their representation is the empty string. <br>
     * . Children? Their representation is the representation of each child, <br>
     * . with a blank between adjacent ones and delimited by "[" and "]". <br>
     * <br>
     * Examples: One-node tree: "A" <br>
     * root A with children B, C, D: "A[B C D]" <br>
     * root A with children B, C, D and B has a child F: "A[B[F] C D]" */
    public static String toStringBrief(FluTree t) {
        String res= t.rootPerson().name();

        Object[] childs= t.copyOfChildren().toArray();
        if (childs.length == 0) return res;
        res= res + "[";
        selectionSort1(childs);

        for (int k= 0; k < childs.length; k= k + 1) {
            if (k > 0) res= res + " ";
            res= res + toStringBrief((FluTree) childs[k]);
        }
        return res + "]";
    }

    /** Sort b --put its elements in ascending order. <br>
     * Sort on the name of the Person at the root of each FluTree.<br>
     * Throw a cast-class exception if b's elements are not FluTree */
    public static void selectionSort1(Object[] b) {
        int j= 0;
        // {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}
        // 0---------------j--------------- b.length
        // inv : b | sorted, <= | >= |
        // --------------------------------
        while (j != b.length) {
            // Put into p the index of smallest element in b[j..]
            int p= j;
            for (int i= j + 1; i != b.length; i++ ) {
                String bi= ((FluTree) b[i]).rootPerson().name();
                String bp= ((FluTree) b[p]).rootPerson().name();
                if (bi.compareTo(bp) < 0) {
                    p= i;

                }
            }
            // Swap b[j] and b[p]
            Object t= b[j];
            b[j]= b[p];
            b[p]= t;
            j= j + 1;
        }
    }

}
