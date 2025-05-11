package com.example.grillcityapk

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
//import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnit4
import com.example.grillcityapk.Screens.LoadScreen
import com.example.grillcityapk.Screens.LoginScreen
import com.example.grillcityapk.Screens.RegistrationScreen

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.grillcityapk", appContext.packageName)
    }

    /**
     * Проверка отображения текстовых полей на странице входа в аккаунт
     * */
    @Test
    fun loginScreenElementsDisplayed() {
        composeTestRule.setContent {
            LoginScreen(rememberNavController(), MainViewModel())
        }

        // Проверяем, что текстовые поля и кнопки отображаются
        composeTestRule.onNodeWithText("Логин").assertIsDisplayed()
        composeTestRule.onNodeWithText("Пароль").assertIsDisplayed()
    }

    /**
     * Проверка отображения почты для входа в аккаунт в поле при ее введении
     * */
    @Test
    fun emailTextFieldInputTest() {
        composeTestRule.setContent {
            LoginScreen(rememberNavController(), MainViewModel())
        }

        // Вводим текст в поле "EmailField"
        composeTestRule.onNodeWithTag("EmailField").performTextInput("ivan26drag@gmail.com")

        // Проверяем, что текст введен
        composeTestRule.onNodeWithTag("EmailField").assertTextEquals("ivan26drag@gmail.com")
        //composeTestRule.onNodeWithTag("PasswordField").assertTextEquals("ivan2605")
    }

    /**
     * Проверка отображения пароля для регистрации аккаунта в поле при его введении
     * */
    @Test
    fun passwordFieldInputTest() {
        composeTestRule.setContent {
            RegistrationScreen(rememberNavController(), MainViewModel())
        }

        // Вводим текст в поле "RegPasswordField"
        composeTestRule.onNodeWithTag("RegFNameField").performTextInput("Иванов")
        composeTestRule.onNodeWithTag("RegSNameField").performTextInput("Иван")
        composeTestRule.onNodeWithTag("RegPatronumicField").performTextInput("Иванович")
        composeTestRule.onNodeWithTag("RegNumberField").performTextInput("89960130079")

        // Проверяем, что текст введен
        composeTestRule.onNodeWithTag("RegFNameField").assertTextEquals("Иванов")
        composeTestRule.onNodeWithTag("RegSNameField").assertTextEquals("Иван")
        composeTestRule.onNodeWithTag("RegPatronumicField").assertTextEquals("Иванович")
        composeTestRule.onNodeWithTag("RegNumberField").assertTextEquals("89960130079")
    }

    /**
     * Проверка отображения логотипа на странице загрузки
     * */
    @Test
    fun loadScreenLogoDisplayed() {
        composeTestRule.setContent {
            LoadScreen(rememberNavController())
        }

        composeTestRule.onNodeWithContentDescription("Logo").assertIsDisplayed()
    }

    /**
     * Проверка отображения маски пароля для входа в аккаунт в поле при его введении
     * */
    @Test
    fun passwordTextFieldInputTest() {
        composeTestRule.setContent {
            LoginScreen(rememberNavController(), MainViewModel())
        }

        // Вводим текст в поле "EmailField"
        composeTestRule.onNodeWithTag("PasswordField").performTextInput("ivan2605")

        // Проверяем, что текст введен
        composeTestRule.onNodeWithTag("PasswordField").assertTextEquals("••••••••")
    }
}