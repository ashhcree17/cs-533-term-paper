package abac.spring.data.neo4j.pairing;

import com.graphaware.test.unit.GraphUnit;
import abac.spring.data.neo4j.BaseTest;
import abac.spring.data.neo4j.domain.User;
import abac.spring.data.neo4j.domain.UserAttribute;
import org.junit.Test;

public class PairingTest extends BaseTest {
    @Test
    public void testSaveFirstUserAttribute() {
        //Check that the first pairing in the graph is created correctly
        User u1 = new User();
        User u2 = new User();
        UserAttribute ua = new UserAttribute("role:researcher");
        ua.addUser(u1);
        ua.addUser(u2);
        u1.addUserAttribute(ua);
        u2.addUserAttribute(ua);

        String userSubgraph =
"create (u1:User),(u2:User),(u1)-[:ASSIGN]->(ua:User {type: 'role:researcher'}),(ua)<-[:ASSIGN]-(u2)";

        GraphUnit.assertSameGraph(getGraphDb(), userSubgraph);
    }

    @Test
    public void testSavePairingWithExistingIngredient() {
//        Ingredient ing1 = new Ingredient("Lemon");
//        Ingredient ing2 = new Ingredient("Garlic");
//        Pairing pairing = new Pairing();
//        pairing.setFirstIngredient(ing1);
//        pairing.setSecondIngredient(ing2);
//        pairing.setAffinity(Affinity.TRIED_TESTED);
//        pairing.save();
//
//        String ingredientSubgraph = "create (i1:Ingredient {name: 'Lemon'}), (i2:Ingredient {name: 'Garlic'}), (i3:Ingredient {name: 'Chicken'})," +
//                " (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.6, allAffinities:[0.6]})-[:hasIngredient]->(i2) , " +
//                "(i2)<-[:hasIngredient]-(p2:Pairing {affinity: 0.45, allAffinities:[0.45]})-[:hasIngredient]->(i3) " +
//                " merge (i1)-[:pairsWith]-(i2) merge (i3)-[:pairsWith]-(i2)";
//
//        GraphUnit.assertSameGraph(getGraphDb(), ingredientSubgraph);


    }

    @Test
    public void testUpdatePairing() {
//        Ingredient ing1 = new Ingredient("Lemon");
//        Ingredient ing2 = new Ingredient("Garlic");
//        Pairing pairing = new Pairing();
//        pairing.setFirstIngredient(ing1);
//        pairing.setSecondIngredient(ing2);
//        pairing.setAffinity(Affinity.EXTREMELY_GOOD);
//        pairing.save();
//
//        String ingredientSubgraph = "create (i1:Ingredient {name: 'Lemon'}), (i2:Ingredient {name: 'Garlic'}), (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.6, allAffinities:[0.6,0.45]}), (p)-[:hasIngredient]->(i2) merge (i1)-[:pairsWith]-(i2)";
//
//        GraphUnit.assertSubgraph(getGraphDb(), ingredientSubgraph);

    }
}
