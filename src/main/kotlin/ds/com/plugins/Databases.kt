package ds.com.plugins

import ds.com.db.FavoritesTable
import ds.com.db.RecipeTable
import ds.com.db.TagTable
import ds.com.db.TagRelationTable
import ds.com.models.TagDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.slf4j.LoggerFactory
import ds.com.db.UserDAO
import ds.com.db.UserTable
import ds.com.models.PostgresTagRepository
import java.sql.Connection
import java.sql.DriverManager
import java.net.URI

fun Application.configureDatabases() {
    val databaseUrl = System.getenv("DATABASE_URL")
    if (databaseUrl == null) {
        throw IllegalArgumentException("DATABASE_URL environment variable is not set.")
    }

    try {
        val dbUri = URI(databaseUrl)
        println("dbUri: $dbUri")
        val username = dbUri.userInfo.split(":")[0]
        val password = dbUri.userInfo.split(":")[1]
        val dbUrl = "jdbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}"


        Database.connect(
            url = dbUrl,
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )

        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(RecipeTable)
            SchemaUtils.create(FavoritesTable)
            SchemaUtils.create(TagTable)
            SchemaUtils.create(TagRelationTable)
        }
    } catch (e: Exception) {
        throw RuntimeException("Failed to parse DATABASE_URL: $databaseUrl", e)
    }
}

