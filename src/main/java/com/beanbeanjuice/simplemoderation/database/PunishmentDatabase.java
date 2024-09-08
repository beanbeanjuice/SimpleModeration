package com.beanbeanjuice.simplemoderation.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PunishmentDatabase {

    private final Connection connection;

    public PunishmentDatabase(final String path) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + path);

        this.createBansTable();
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) connection.close();
    }

    private void createBansTable() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS bans (
                ban_id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0,
                player_uuid TEXT NOT NULL,
                ban_moderator_uuid TEXT NOT NULL,
                reason TEXT NOT NULL,
                date_created DATETIME NOT NULL,
                date_expires DATETIME,
                ip TEXT NOT NULL,
                is_ip_banned INTEGER NOT NULL CHECK (is_ip_banned IN (0, 1)),
                date_removed DATETIME,
                unban_moderator_uuid TEXT,
                removed_reason TEXT
            );
        """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
