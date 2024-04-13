import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class UserTest {
    @Test
    fun testUser(){
        val user = User("user", "password", mutableListOf(), mutableListOf())
        user.addFavorite(1)
        user.addFavorite(2)
        user.addFavorite(3)
        user.addRestriction("vegan")
        user.addRestriction("gluten-free")
        user.removeFavorite(2)
        user.removeRestriction("vegan")
        assertEquals(user.favorites, mutableListOf(1, 3))
        assertEquals(user.restrictions, mutableListOf("gluten-free"))
    }
}