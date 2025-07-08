
/*
* TODO: Analisar esse teste
* */
//package br.com.mc2e.neuromind.infrastructure.local.storage
//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.MutablePreferences
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import io.mockk.*
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.runBlocking
//import org.junit.After
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//
//
//class UserPreferencesStorageImplTest {
//
//    // Mocks
//    private lateinit var mockDataStore: DataStore<Preferences>
//    private lateinit var mockPreferences: Preferences
//    private lateinit var mockMutablePreferences: MutablePreferences
//
//    private val mockContext = mockk<Context>(relaxed = true)
//
//    // Class under test
//    private lateinit var userPreferencesStorage: UserPreferencesStorageImpl
//
//    // Preference Keys (ensure these match your implementation)
//    private val userNameKey = stringPreferencesKey("user_name")
//    private val userEmailKey = stringPreferencesKey("user_email")
//
//    @Before
//    fun setUp() {
//        // Instantiate mocks
//        mockDataStore =
//            mockk(relaxUnitFun = true) // relaxUnitFun for suspend functions returning Unit
//        mockPreferences = mockk()
//        mockMutablePreferences = mockk(relaxUnitFun = true) // For set operations
//
//        // **Important**: For UserPreferencesStorageImpl to be testable this way,
//        // it should ideally accept DataStore<Preferences> as a constructor parameter.
//        // If it creates its own DataStore instance internally using a Context,
//        // you would need a more complex setup (e.g., Robolectric or instrumented tests,
//        // or refactoring to inject the DataStore).
//        // For this example, I'll assume you can inject mockDataStore.
//        // If UserPreferencesStorageImpl takes Context, you might do:
//        // val mockContext = mockk<Context>(relaxed = true)
//        // And then ensure the DataStore instance within UserPreferencesStorageImpl is the mockDataStore.
//        // This usually means refactoring UserPreferencesStorageImpl.
//
//        // Assuming UserPreferencesStorageImpl can take a DataStore instance:
//        userPreferencesStorage = UserPreferencesStorageImpl(mockContext)
//        // If your UserPreferencesStorageImpl takes a Context and you can't easily inject
//        // DataStore, you'd mock the Context and the global `preferencesDataStore` delegate if possible,
//        // which is more involved. The current file content shows you are already attempting to
//        // mock the DataStore directly, which is good.
//
//        // Common setup for coEdit
//        coEvery { mockDataStore.edit(any()) } coAnswers {
//            // This lambda simulates the behavior of the `edit` extension function.
//            // It captures the transform block passed to `edit` and invokes it with `mockMutablePreferences`.
//            val transform = arg<suspend (MutablePreferences) -> Unit>(0)
//            transform(mockMutablePreferences)
//            mockPreferences // `edit` returns the updated Preferences
//        }
//
//        // Common setup for `dataStore.data`
//        every { mockDataStore.data } returns flowOf(mockPreferences)
//    }
//
//    @After
//    fun tearDown() {
//        unmockkAll() // Clear all mocks after each test
//    }
//
//    @Test
//    fun `saveUserName should store the user name in DataStore`() = runBlocking {
//        val testName = "John Doe"
//
//        // Action
//        userPreferencesStorage.saveUserName(testName)
//
//        // Verification
//        coVerify(exactly = 1) { mockDataStore.edit(any()) } // Verify edit was called
//        verify(exactly = 1) {
//            mockMutablePreferences[userNameKey] = testName
//        } // Verify the correct key and value were set
//    }
//
//    @Test
//    fun `getUserName should retrieve the user name from DataStore`() = runBlocking {
//        val expectedName = "Jane Doe"
//        every { mockPreferences[userNameKey] } returns expectedName
//
//        // Action
//        val actualName = userPreferencesStorage.getUserName()
//
//        // Assertion
//        assertEquals(expectedName, actualName)
//        // Verification (optional, as the result implies interaction, but good for completeness)
//        verify(exactly = 1) { mockPreferences[userNameKey] }
//    }
//
//    @Test
//    fun `getUserName should return null if no user name is stored`() = runBlocking {
//        every { mockPreferences[userNameKey] } returns null
//
//        // Action
//        val actualName = userPreferencesStorage.getUserName()
//
//        // Assertion
//        assertNull(actualName)
//        verify(exactly = 1) { mockPreferences[userNameKey] }
//    }
//
//    @Test
//    fun `saveUserEmail should store the user email in DataStore`() = runBlocking {
//        val testEmail = "john.doe@example.com"
//
//        // Action
//        userPreferencesStorage.saveUserEmail(testEmail)
//
//        // Verification
//        coVerify(exactly = 1) { mockDataStore.edit(any()) }
//        verify(exactly = 1) { mockMutablePreferences[userEmailKey] = testEmail }
//    }
//
//    @Test
//    fun `getUserEmail should retrieve the user email from DataStore`() = runBlocking {
//        val expectedEmail = "jane.doe@example.com"
//        every { mockPreferences[userEmailKey] } returns expectedEmail
//
//        // Action
//        val actualEmail = userPreferencesStorage.getUserEmail()
//
//        // Assertion
//        assertEquals(expectedEmail, actualEmail)
//        verify(exactly = 1) { mockPreferences[userEmailKey] }
//    }
//
//    @Test
//    fun `getUserEmail should return null if no user email is stored`() = runBlocking {
//        every { mockPreferences[userEmailKey] } returns null
//
//        // Action
//        val actualEmail = userPreferencesStorage.getUserEmail()
//
//        // Assertion
//        assertNull(actualEmail)
//        verify(exactly = 1) { mockPreferences[userEmailKey] }
//    }
//
//    @Test
//    fun `saveUserName should overwrite existing user name`() = runBlocking {
//        val initialName = "Old Name"
//        val newName = "New Name"
//
//        // Simulate initial save if needed, or just focus on the overwrite
//        every { mockMutablePreferences[userNameKey] = initialName } just runs
//        userPreferencesStorage.saveUserName(initialName) // Call it once
//
//        // Setup for the second call (overwrite)
//        every { mockMutablePreferences[userNameKey] = newName } just runs
//        userPreferencesStorage.saveUserName(newName) // Call it again to overwrite
//
//        // Verify edit was called (potentially twice, depending on how you structure the test)
//        coVerify(atLeast = 1) { mockDataStore.edit(any()) } // Or exactly = 2 if you
//    }
//}