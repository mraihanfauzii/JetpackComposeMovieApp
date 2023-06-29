package com.example.submission_akhir_jetpackcompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.submission_akhir_jetpackcompose.model.MovieDataSource
import com.example.submission_akhir_jetpackcompose.ui.navigation.Screen
import com.example.submission_akhir_jetpackcompose.ui.theme.Submission_Akhir_JetpackComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

 class JetMovieAppTest {
     @get:Rule
     val composeTestRule = createAndroidComposeRule<ComponentActivity>()
     private lateinit var navController: TestNavHostController

     @Before
     fun setUp() {
         composeTestRule.setContent {
             Submission_Akhir_JetpackComposeTheme {
                 navController = TestNavHostController(LocalContext.current)
                 navController.navigatorProvider.addNavigator(ComposeNavigator())
                 JetMovieApp(navController = navController)
             }
         }
     }

     @Test
     fun navHost_verifyStartDestination() {
         navController.assertCurrentRouteName(Screen.Home.route)
     }

     @Test
     fun home_clickItem_scrollToTop() {
         composeTestRule.onNodeWithTag("MovieList").performScrollToIndex(22)
         composeTestRule.onNodeWithTag("ScrollToTop").performClick()
         composeTestRule.onNodeWithText(MovieDataSource.movieData[0].title).assertIsDisplayed()
     }

     @Test
     fun test_textfield_input() {
         val textInput = "Blackpanther"
         composeTestRule.onNodeWithTag("SearchBar").performTextInput(textInput)
         composeTestRule.onNodeWithTag("SearchBar").assertTextEquals(textInput)
     }

     @Test
     fun navHost_bottomNavigation_working() {
         composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
         navController.assertCurrentRouteName(Screen.Cart.route)
         composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
         navController.assertCurrentRouteName(Screen.Profile.route)
         composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
         navController.assertCurrentRouteName(Screen.Home.route)
     }

     @Test
     fun navHost_clickItem_navigatesBack() {
         composeTestRule.onNodeWithText(MovieDataSource.movieData[8].title).performClick()
         navController.assertCurrentRouteName(Screen.DetailMovie.route)
         composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
         navController.assertCurrentRouteName(Screen.Home.route)
     }

     @Test
     fun navHost_checkout_rightBackStack() {
         composeTestRule.onNodeWithText(MovieDataSource.movieData[27].title).performClick()
         navController.assertCurrentRouteName(Screen.DetailMovie.route)
         composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
         composeTestRule.onNodeWithContentDescription("Order Button").performClick()
         navController.assertCurrentRouteName(Screen.Cart.route)
         composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
         navController.assertCurrentRouteName(Screen.Home.route)
     }
 }