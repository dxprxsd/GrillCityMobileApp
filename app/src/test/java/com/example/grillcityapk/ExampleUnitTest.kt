package com.example.grillcityapk

import androidx.compose.runtime.mutableStateOf
import com.example.grillcityapk.Models.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        // Инициализация ViewModel перед каждым тестом
        viewModel = MainViewModel()
        Dispatchers.setMain(Dispatchers.Unconfined) // Установка неограниченного диспетчера для тестов
    }

    /**
     * Тест для проверки начального значения authResult.
     * Ожидается, что authResult будет иметь пустую строку в качестве начального значения.
     */
    @Test
    fun AuthResultValue() {
        val viewModel = MainViewModel()
        assertEquals("", viewModel.authResult.value)
    }

    /**
     * Тест для проверки начального значения registrationResult.
     * Ожидается, что registrationResult будет иметь пустую строку в качестве начального значения.
     */
    @Test
    fun registrationResult_initialValue_isEmpty() {
        assertEquals("", viewModel.registrationResult.value)
    }

    /**
     * Тест для проверки начального значения profileCreated.
     * Ожидается, что profileCreated будет иметь значение false по умолчанию.
     */
    @Test
    fun profileCreated_initialValue_isFalse() {
        assertFalse(viewModel.profileCreated.value)
    }

    /**
     * Тест для проверки фильтрации списка продуктов по имени.
     * Ожидается, что при поиске по имени "Shirt" в списке останется только один продукт.
     */
    @Test
    fun filteredHaircutMethod_filtersBySearchQueryCorrectly() {
        // Установка данных
        val testProducts = listOf(
            Products(
                Id = 1,
                ProductName = "Shirt",
                ProductTypeId = 1,
                ProviderId = 1,
                QuantityInStock = 10,
                Price = 100f
            ),
            Products(
                Id = 2,
                ProductName = "Pants",
                ProductTypeId = 2,
                ProviderId = 1,
                QuantityInStock = 5,
                Price = 200f
            )
        )

        viewModel.searchQuery.value = "Shirt"
        viewModel.typeOfReadyClothes = null

        // Рефлексия для установки приватного _rmhaircuts
        viewModel.apply {
            val field = MainViewModel::class.java.getDeclaredField("_rmhaircuts")
            field.isAccessible = true
            field.set(this, mutableStateOf(testProducts))
        }

        viewModel.filteredHaircutMethod()

        // Проверяем, что остался только один продукт с именем "Shirt"
        assertEquals(1, viewModel.filteredHaircuts.value.size)
        assertEquals("Shirt", viewModel.filteredHaircuts.value[0].ProductName)
    }

    /**
     * Тест для проверки добавления товара в корзину с количеством 1.
     * Ожидается, что товар будет добавлен в корзину с количеством 1.
     */
    @Test
    fun addToCart_addsProductWithQuantityOne() = runTest {
        val product = Products(
            Id = 1,
            ProductName = "Hat",
            ProductTypeId = 1,
            Price = 50f,
            ProviderId = 1,
            QuantityInStock = 10
        )
        viewModel.addToCart(product)

        val cart = viewModel.cartItems.first()
        // Проверяем, что в корзине один товар с количеством 1
        assertEquals(1, cart.size)
        assertEquals(1, cart[0].QuantityInCart)
    }

    /**
     * Тест для проверки увеличения количества товара в корзине.
     * Ожидается, что при вызове метода increaseQuantity количество товара увеличится на 1.
     */
    @Test
    fun increaseQuantity_incrementsQuantity() = runTest {
        val product = Products(
            Id = 1,
            ProductName = "Hat",
            ProductTypeId = 1,
            Price = 50f,
            ProviderId = 1,
            QuantityInStock = 10)
        viewModel.addToCart(product)
        viewModel.increaseQuantity(product)

        val cart = viewModel.cartItems.first()
        // Проверяем, что количество товара в корзине увеличилось до 2
        assertEquals(2, cart[0].QuantityInCart)
    }

    /**
     * Тест для проверки корректности подсчета общей суммы корзины.
     * Ожидается, что сумма всех товаров в корзине будет правильной (2 * 100 + 2 * 100 = 400).
     */
    @Test
    fun getCartTotal_returnsCorrectSum() = runTest {
        val product1 = Products(
            Id = 1,
            ProductName = "Shirt",
            ProductTypeId = 1,
            Price = 100f,
            QuantityInCart = 1, // Начальное количество
            ProviderId = 1,
            QuantityInStock = 5
        )

        val product2 = Products(
            Id = 2,
            ProductName = "Pants",
            ProductTypeId = 2,
            Price = 100f,
            QuantityInCart = 1, // Начальное количество
            ProviderId = 1,
            QuantityInStock = 3
        )

        // Добавляем товар в корзину
        viewModel.addToCart(product1)
        viewModel.addToCart(product2)

        // Увеличиваем количество в корзине
        viewModel.addToCart(product1.copy(QuantityInCart = 1)) // Увеличиваем количество на 1 (итого 2)
        viewModel.addToCart(product2.copy(QuantityInCart = 1)) // Увеличиваем количество на 1 (итого 2)

        // Подсчитываем итоговую сумму
        val total = viewModel.getCartTotal()
        // Проверяем, что итоговая сумма равна 400 (2 * 100 + 2 * 100)
        assertEquals(400f, total)
    }
}
