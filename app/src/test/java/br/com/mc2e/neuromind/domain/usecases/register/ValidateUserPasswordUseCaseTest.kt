package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateUserPasswordUseCaseTest {
    private val useCase = ValidateUserPasswordUseCase()

    @Test
    fun `should return Success when password and confirmation are valid and match`() {
        val password = "Password@123"
        val confirmation = "Password@123"
        val result = useCase.execute(password, confirmation)
        assertTrue(result is Result.Success)
        val value = (result as Result.Success).data
        assertEquals(password, value.getValue())
    }

    @Test
    fun `should return Error ConfirmPasswordMismatchError when password and confirmation do not match`() {
        val password = "Password@123"
        val confirmation = "Password@124"
        val result = useCase.execute(password, confirmation)
        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is ValidationFailure.ConfirmPasswordMismatchError)
    }

    @Test
    fun `should return Error InvalidPassword when password is too short`() {
        val password = "Ab1@"
        val confirmation = "Ab1@"
        val result = useCase.execute(password, confirmation)
        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is ValidationFailure.InvalidPassword)
    }

    @Test
    fun `should return Error InvalidPassword when password is blank`() {
        val password = "   "
        val confirmation = "   "
        val result = useCase.execute(password, confirmation)
        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is ValidationFailure.InvalidPassword)
    }

    @Test
    fun `should return Error InvalidPassword when password does not meet requirements`() {
        val password = "password" // no uppercase, no special char, no digit
        val confirmation = "password"
        val result = useCase.execute(password, confirmation)
        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is ValidationFailure.InvalidPassword)
    }
}