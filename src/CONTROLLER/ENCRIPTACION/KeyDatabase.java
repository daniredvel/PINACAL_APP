package CONTROLLER.ENCRIPTACION;

import DB.UTIL.GestorConexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KeyDatabase {

    private static final String DB_NAME = "Pinacal";
    private static final String DB_USER = "Pinacal";
    private static final String DB_PASSWORD = "pinacal";

    public static boolean crearConexion() {
        int estadoConexion = GestorConexion.crearConexion(DB_NAME, DB_USER, DB_PASSWORD);
        return estadoConexion == 0;
    }

    // Almacena la clave encriptada en la base de datos
    public static void storeKeyInDatabase(String encryptedKey) throws Exception {
        if (crearConexion()) {
            try (Connection conn = GestorConexion.getConexion()) {
                String sql = "INSERT INTO keys (encrypted_key) VALUES (?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, encryptedKey);
                    pstmt.executeUpdate();
                }
            }
        }

    }

    // Recupera la clave encriptada de la base de datos
    public static String retrieveKeyFromDatabase() throws Exception {
        if (crearConexion()) {

            try (Connection conn = GestorConexion.getConexion()) {
                String sql = "SELECT encrypted_key FROM keys LIMIT 1";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getString("encrypted_key");
                        }
                    }
                }
            }
        }
        return null;
    }
}