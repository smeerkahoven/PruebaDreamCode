package com.dreamcode.PruebaDreamCode;

import com.dreamcode.controller.SquareTradeController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SquareTradeControllerTest {

    private SquareTradeController tradeController;

    void create(){
        tradeController = new SquareTradeController();
    }
    @Test
    void testCreateRootCategory(){

        create();

        tradeController.createCategory("Furniture");
        tradeController.createCategory("Electronics");
        tradeController.createCategory("HomeAppliances");

        assertThat(tradeController.getCategories().size()).isEqualTo(3);
    }


    @Test
    void testGetKeyWord(){

        create();
        tradeController.createCategory("Furniture");
        tradeController.createCategory("Electronics");
        tradeController.createCategory("HomeAppliances");

        tradeController.createSubcategory("HomeAppliances", "Major Appliances");
        tradeController.createSubcategory("HomeAppliances", "Minor Appliances");
        tradeController.createSubcategory("HomeAppliances", "Lawn&Garden");

        tradeController.addKeyword("Lawn&Garden","Lawn");
        tradeController.addKeyword("Lawn&Garden","Garden");
        tradeController.addKeyword("Lawn&Garden","GardeningTools");

        assertThat(tradeController.getKeyWords("Lawn&Garden").size()).isEqualTo(3);

    }

    /**
     * This test next levels
     *
     * HomeAppliances       1
     *      Lawn&Garden     2
     *          Test1       3
     *              Subtest 4
     *
     */
    @Test
    void testLevel(){

        create();

        tradeController.createCategory("Furniture");
        tradeController.createCategory("Electronics");
        tradeController.createCategory("HomeAppliances");

        tradeController.createSubcategory("HomeAppliances", "Major Appliances");
        tradeController.createSubcategory("HomeAppliances", "Minor Appliances");
        tradeController.createSubcategory("HomeAppliances", "Lawn&Garden");

        tradeController.createSubcategory("Lawn&Garden", "Test1");

        tradeController.addKeyword("Lawn&Garden","Lawn");
        tradeController.addKeyword("Lawn&Garden","Garden");
        tradeController.addKeyword("Lawn&Garden","GardeningTools");

        tradeController.createSubcategory("Test1", "SubTest1");

        assertThat(tradeController.displayLevel("HomeAppliances")).isEqualTo(1);
        assertThat(tradeController.displayLevel("Lawn&Garden")).isEqualTo(2);
        assertThat(tradeController.displayLevel("Test1")).isEqualTo(3);
        assertThat(tradeController.displayLevel("SubTest1")).isEqualTo(4);
    }

    /**
     * This test next keywords
     *
     * HomeAppliances       keyword ( appliances )
     *      Lawn&Garden     keyword ( Lawn, Garden, Gardening Tools
     *          Test1       keyword ()
     *
     */
    @Test
    void testGetKeywordsForCategory(){

        create();

        tradeController.createCategory("Furniture");
        tradeController.createCategory("Electronics");
        tradeController.createCategory("HomeAppliances");

        tradeController.createSubcategory("HomeAppliances", "Major Appliances");
        tradeController.createSubcategory("HomeAppliances", "Minor Appliances");
        tradeController.createSubcategory("HomeAppliances", "Lawn&Garden");

        tradeController.createSubcategory("Lawn&Garden", "Test1");

        tradeController.addKeyword("HomeAppliances","appliances");
        tradeController.addKeyword("Lawn&Garden","Lawn");
        tradeController.addKeyword("Lawn&Garden","Garden");
        tradeController.addKeyword("Lawn&Garden","GardeningTools");

        tradeController.createSubcategory("Test1", "SubTest1");

        assertThat(tradeController.getKeyWords("HomeAppliances").size()).isEqualTo(1);
        assertThat(tradeController.getKeyWords("Lawn&Garden").size()).isEqualTo(4);
        final List<String> result = tradeController.getKeyWords("Test1") ;
        assertThat(tradeController.getKeyWords("Test1").size()).isEqualTo(4);

    }


}
