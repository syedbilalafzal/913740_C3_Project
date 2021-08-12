import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    @Spy
    Restaurant restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    //REFACTOR ALL THE REPEATED LINES OF CODE
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE

    @BeforeEach
    public void RestaurantTest(){
        this.restaurant.addToMenu("Sweet corn soup",119);
        this.restaurant.addToMenu("Vegetable lasagne", 269);
    }
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        //Restaurant Opening time is "10:30:00" and closing time is "22:00:00"
        LocalTime timeWithinRange = LocalTime.parse("21:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(timeWithinRange);
        assertThat(restaurant.isRestaurantOpen(), equalTo(true));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //Restaurant Opening time is "10:30:00" and closing time is "22:00:00"

        LocalTime timeOutOfange = LocalTime.parse("23:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(timeOutOfange);
        assertThat(restaurant.isRestaurantOpen(), equalTo(false));

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = this.restaurant.getMenu().size();
        this.restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,this.restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_total_price_of_multiple_items_passed_to_the_function(){
        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        double totalCost = restaurant.getItemsPrice(selectedItems);
        List<Item> items = restaurant.getMenu();
        float itemOnePrice = Float.parseFloat(items.get(0).toString().split(":")[1]);
        float itemTwoPrice = Float.parseFloat(items.get(1).toString().split(":")[1]);
        float totalPrice = itemOnePrice + itemTwoPrice;
        assertEquals(totalCost,totalPrice);
    }

}