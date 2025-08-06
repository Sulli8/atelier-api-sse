package com.example.atelier_api_sullivan_sextius.storage;
import com.example.atelier_api_sullivan_sextius.dto.CountryDTO;
import com.example.atelier_api_sullivan_sextius.dto.PlayerDTO;
import com.example.atelier_api_sullivan_sextius.dto.StatsDataDTO;
import com.example.atelier_api_sullivan_sextius.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerStorageTest {
    public static final Long ROGER_ID = 1L;
    public static final String ROGER_FIRST_NAME = "Roger";
    public static final String ROGER_LAST_NAME = "Federer";
    public static final String ROGER_SEX = "M";
    public static final String ROGER_SHORT_NAME = "R. FED";
    public static final String ROGER_PICTURE = "pic1.jpg";
    public static final String ROGER_COUNTRY_CODE = "SUI";
    public static final String ROGER_COUNTRY_PICTURE = "http://example.fr";
    public static final int ROGER_HEIGHT = 185;
    public static final int ROGER_WEIGHT = 85000;
    public static final List<Integer> ROGER_LAST_MATCHES = List.of(1, 1, 0);
    public static final int ROGER_RANK = 10;

    public static final Long NADAL_ID = 2L;
    public static final String NADAL_FIRST_NAME = "Rafael";
    public static final String NADAL_LAST_NAME = "Nadal";
    public static final String NADAL_SEX = "M";
    public static final String NADAL_SHORT_NAME = "R. NAD";
    public static final String NADAL_PICTURE = "pic2.jpg";
    public static final String NADAL_COUNTRY_CODE = "SUI";
    public static final String NADAL_COUNTRY_PICTURE = "http://example.fr";
    public static final int NADAL_HEIGHT = 180;
    public static final int NADAL_WEIGHT = 83000;
    public static final List<Integer> NADAL_LAST_MATCHES = List.of(1, 0);
    public Map<String, PlayerDTO> players;
    public static final int NADAL_RANK = 14;

    public static final Long DJOKOVIC_ID = 3L;
    public static final String DJOKOVIC_FIRST_NAME = "Novak";
    public static final String DJOKOVIC_LAST_NAME = "Djokovic";
    public static final String DJOKOVIC_SEX = "M";
    public static final String DJOKOVIC_SHORT_NAME = "N. DJO";
    public static final String DJOKOVIC_PICTURE = "pic3.jpg";
    public static final String DJOKOVIC_COUNTRY_CODE = "SUI";
    public static final String DJOKOVIC_COUNTRY_PICTURE = "http://example.fr";
    public static final int DJOKIVIC_RANK = 20;

    public static CountryDTO createRogerCountry() {
        return CountryDTO.builder().code(ROGER_COUNTRY_CODE).picture(ROGER_COUNTRY_PICTURE).build();
    }

    public static StatsDataDTO createRogerStats() {
        return StatsDataDTO.builder().rank(ROGER_RANK).points(120000).age(20).height(ROGER_HEIGHT).weight(ROGER_WEIGHT).last(ROGER_LAST_MATCHES).build();
    }

    public static CountryDTO createNadalCountry() {
        return CountryDTO.builder().code(NADAL_COUNTRY_CODE).picture(NADAL_COUNTRY_PICTURE).build();
    }

    public static StatsDataDTO createNadalStats() {
        return StatsDataDTO.builder().rank(NADAL_RANK).points(32000).age(36).height(NADAL_HEIGHT).weight(NADAL_WEIGHT).last(NADAL_LAST_MATCHES).build();
    }

    public static CountryDTO createDjokovicCountry() {
        return CountryDTO.builder().code(DJOKOVIC_COUNTRY_CODE).picture(DJOKOVIC_COUNTRY_PICTURE).build();
    }
    public static StatsDataDTO createDjokovicStats() {
        return StatsDataDTO.builder().rank(DJOKIVIC_RANK).points(320).age(29).height(NADAL_HEIGHT).weight(NADAL_WEIGHT).last(NADAL_LAST_MATCHES).build();
    }
    @BeforeEach
    void setUp() {

        players = new HashMap<>();
        PlayerDTO player1 = PlayerDTO.builder()
                .id(ROGER_ID)
                .firstName(ROGER_FIRST_NAME)
                .lastName(ROGER_LAST_NAME)
                .shortName(ROGER_SHORT_NAME)
                .sex(ROGER_SEX)
                .picture(ROGER_PICTURE)
                .country(createRogerCountry())
                .data(createRogerStats())
                .build();

        PlayerDTO player2 = PlayerDTO.builder()
                .id(NADAL_ID)
                .firstName(NADAL_FIRST_NAME)
                .lastName(NADAL_LAST_NAME)
                .shortName(NADAL_SHORT_NAME)
                .sex(NADAL_SEX)
                .picture(NADAL_PICTURE)
                .country(createNadalCountry())
                .data(createNadalStats())
                .build();

        PlayerDTO player3 = PlayerDTO.builder()
                .id(DJOKOVIC_ID)
                .firstName(DJOKOVIC_FIRST_NAME)
                .lastName(DJOKOVIC_LAST_NAME)
                .shortName(DJOKOVIC_SHORT_NAME)
                .sex(DJOKOVIC_SEX)
                .picture(DJOKOVIC_PICTURE)
                .country(createDjokovicCountry())
                .data(createDjokovicStats())
                .build();


        players.put(player1.getId().toString(), player1);
        players.put(player2.getId().toString(), player2);
        players.put(player3.getId().toString(), player3);


    }
    @Test
    void testAddOrUpdatePlayer_success() {
        PlayerStorage storage = new PlayerStorage();
        PlayerDTO player = PlayerDTO.builder()
                .id(DJOKOVIC_ID)
                .firstName(DJOKOVIC_FIRST_NAME)
                .lastName(DJOKOVIC_LAST_NAME)
                .shortName(DJOKOVIC_SHORT_NAME)
                .sex(DJOKOVIC_SEX)
                .picture(DJOKOVIC_PICTURE)
                .country(createDjokovicCountry())
                .data(createDjokovicStats())
                .build();


        Map<String, PlayerDTO> result = storage.addOrUpdatePlayer(player);

        assertNotNull(result);
        assertTrue(result.containsKey("3"));
        assertEquals(DJOKOVIC_FIRST_NAME, result.get("3").getFirstName());
    }
    @Test
    void testAddOrUpdatePlayer_throwsException_whenPlayerIsNull() {
        PlayerStorage storage = new PlayerStorage();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            storage.addOrUpdatePlayer(null);
        });

        assertEquals("Player or its ID cannot be null", thrown.getMessage());
    }

    @Test
    void testAddOrUpdatePlayer_throwsException_whenPlayerIdIsNull() {
        PlayerStorage storage = new PlayerStorage();
        PlayerDTO playerWithoutId = PlayerDTO.builder()
                .firstName(DJOKOVIC_FIRST_NAME)
                .lastName(DJOKOVIC_LAST_NAME)
                .shortName(DJOKOVIC_SHORT_NAME)
                .sex(DJOKOVIC_SEX)
                .picture(DJOKOVIC_PICTURE)
                .country(createDjokovicCountry())
                .data(createDjokovicStats())
                .build();


        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            storage.addOrUpdatePlayer(playerWithoutId);
        });
        assertEquals("Player or its ID cannot be null", thrown.getMessage());
    }

    @Test
    void testGetPlayerById_existingId_returnsPlayer() {
        PlayerStorage storage = new PlayerStorage();

        PlayerDTO player = PlayerDTO.builder()
                .id(123L)
                .firstName(NADAL_FIRST_NAME)
                .lastName(NADAL_LAST_NAME)
                .shortName(NADAL_SHORT_NAME)
                .sex(NADAL_SEX)
                .picture(NADAL_PICTURE)
                .country(createNadalCountry())
                .data(createNadalStats())
                .build();

        storage.addOrUpdatePlayer(player);
        PlayerDTO result = storage.getPlayerById("123");

        assertNotNull(result);
        assertEquals(NADAL_FIRST_NAME,result.getFirstName());
        assertEquals(NADAL_LAST_NAME, result.getLastName());
    }


    @Test
    void testGetPlayerById_noExistingId_throwsException() {
        PlayerStorage storage = new PlayerStorage();
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            storage.getPlayerById("999");
        });
        assertEquals("Player with id 999 not found", exception.getMessage());
    }

    @Test
    void testGetPlayerById_noExistingNullId_throwsException() {
        PlayerStorage storage = new PlayerStorage();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            storage.getPlayerById(null);
        });
        assertEquals("Player id cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDeletePlayer_nullPlayer_throwsException() {
        PlayerStorage storage = new PlayerStorage();
        PlayerDTO playerWithNullId = new PlayerDTO();
        assertThrows(IllegalArgumentException.class, () -> storage.deletePlayer(playerWithNullId));
    }



}
