package com.example.atelier_api_sullivan_sextius.service;

import com.example.atelier_api_sullivan_sextius.dto.*;
import com.example.atelier_api_sullivan_sextius.exceptions.BadRequestException;

import com.example.atelier_api_sullivan_sextius.exceptions.PlayerNotFoundException;
import com.example.atelier_api_sullivan_sextius.exceptions.ResourceNotFoundException;
import com.example.atelier_api_sullivan_sextius.storage.PlayerStorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {

    private  PlayerService service;
    private ObjectMapper mapper = new ObjectMapper();
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
    void testGetAllPlayers() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        service = new PlayerService(mapper, mockPlayerStorage);
        Map<String, PlayerDTO> result = service.getAllPlayers();

        assertEquals(3, result.size());
    }


    @Test
    void testGetAllPlayersNotFound() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(null);

        service = new PlayerService(mapper, mockPlayerStorage);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getAllPlayers();
        });
        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testGetPlayerById_found() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);
        when(mockPlayerStorage.getPlayerById("1")).thenReturn(players.get("1"));

        service = new PlayerService(mapper, mockPlayerStorage);

        PlayerDTO player = service.getPlayerById(1L);
        assertNotNull(player);
        assertEquals("Roger", player.getFirstName());
    }

    @Test
    void testGetPlayerById_notFound() {
        Long invalidId = 999L;
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);
        when(mockPlayerStorage.getPlayerById(invalidId.toString())).thenReturn(null);

        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerNotFoundException ex = assertThrows(PlayerNotFoundException.class, () -> {
            service.getPlayerById(invalidId);
        });
        assertEquals("The player with id 999 does not exist.", ex.getMessage());
    }

    @Test
    void testAddPlayer_success() {
        PlayerDTO player = PlayerDTO.builder()
                .id(100L)
                .firstName("Andy")
                .lastName("Murray")
                .shortName("A. MUR")
                .sex("M")
                .picture("pic4.jpg")
                .country(CountryDTO.builder().code("GBR").picture("flag4.jpg").build())
                .data(StatsDataDTO.builder().rank(4).height(180).weight(79000).last(List.of(1, 1)).build())
                .build();

        PlayerStorage mockPlayerStorage = mock(PlayerStorage.class);

        Map<String, PlayerDTO> players = new HashMap<>();
        players.put("100", player);

        when(mockPlayerStorage.addOrUpdatePlayer(player)).thenReturn(players);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        PlayerService service = new PlayerService(mapper, mockPlayerStorage);


        Map<String, PlayerDTO> addedPlayers = service.addPlayer(player);

        assertNotNull(addedPlayers);
        assertTrue(addedPlayers.containsKey("100"));
        assertEquals("Andy", addedPlayers.get("100").getFirstName());
        assertEquals(1, service.getAllPlayers().size());
    }

    @Test
    void testAddPlayer_missingFields_shouldThrow() {
        PlayerDTO player = new PlayerDTO();
        player.setFirstName(null);
        player.setLastName("Murray");
        player.setSex("M");
        player.setCountry(CountryDTO.builder().code("GBR").build());
        Map<String, PlayerDTO> players = new HashMap<>();
        players.put("100", player);

        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        service = new PlayerService(mapper, mockPlayerStorage);
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.addPlayer(player));
        assertEquals("The first name, last name, and gender are required.", ex.getMessage());
    }

    @Test
    void testAddPlayer_missingFieldsCountry_shouldThrow() {
        PlayerDTO player = new PlayerDTO();
        player.setFirstName("firstname");
        player.setLastName("userNotFiedlCountry");
        player.setSex("F");
        Map<String, PlayerDTO> players = new HashMap<>();
        players.put("200", player);

        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        service = new PlayerService(mapper, mockPlayerStorage);
        BadRequestException ex = assertThrows(BadRequestException.class, () -> service.addPlayer(player));
        assertEquals("The country and its code are required.", ex.getMessage());
    }

    @Test
    void testPatchPlayer_shouldUpdateNamesOnly() {
        PlayerDTO player = PlayerDTO.builder()
                .id(100L)
                .lastName("Murray").build();

        PlayerStorage mockPlayerStorage = mock(PlayerStorage.class);

        Map<String, PlayerDTO> players = new HashMap<>();
        players.put("100", player);

        when(mockPlayerStorage.addOrUpdatePlayer(player)).thenReturn(players);
        PlayerService service = new PlayerService(mapper, mockPlayerStorage);
        Map<String, PlayerDTO> updatedPlayer= service.patchPlayer(player);

        assertNotNull(updatedPlayer);
        assertEquals("Murray", updatedPlayer.get("100").getLastName());
    }

    @Test
    void testGetPlayersSortedByRank() {

        PlayerStorage mockPlayerStorage = mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        PlayerService service = new PlayerService(mapper, mockPlayerStorage);

        List<PlayerDTO> sorted = service.getPlayersSortedByRank(players);

        assertEquals(ROGER_RANK, sorted.get(0).getData().getRank());
        assertEquals("Roger", sorted.get(0).getFirstName());

        assertEquals(NADAL_RANK, sorted.get(1).getData().getRank());
        assertEquals(NADAL_FIRST_NAME, sorted.get(1).getFirstName());

        assertEquals(DJOKIVIC_RANK, sorted.get(2).getData().getRank());
        assertEquals(DJOKOVIC_FIRST_NAME, sorted.get(2).getFirstName());
    }

    @Test
    void testGetPlayersSortedByRankNotData() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getPlayersSortedByRank(null);
        });
        assertEquals("No players data provided", ex.getMessage());
    }
    @Test
    void testGetTopCountryByWinRatio() {
        PlayerDTO roger = PlayerDTO.builder()
                .id(1L)
                .firstName("Roger")
                .country(CountryDTO.builder().code("SUI").build())
                .data(StatsDataDTO.builder().last(List.of(1, 1, 0, 1, 0)).build())
                .build();

        PlayerDTO nadal = PlayerDTO.builder()
                .id(2L)
                .lastName("Nadal")
                .country(CountryDTO.builder().code("ESP").build())
                .data(StatsDataDTO.builder().last(List.of(1, 0, 0)).build())
                .build();

        PlayerDTO djokovic = PlayerDTO.builder()
                .id(3L)
                .firstName("Djokovic")
                .country(CountryDTO.builder().code("SUI").build())
                .data(StatsDataDTO.builder().last(List.of(1, 1)).build())
                .build();

        Map<String, PlayerDTO> players = new HashMap<>();
        players.put("1", roger);
        players.put("2", nadal);
        players.put("3", djokovic);

        PlayerStorage mockPlayerStorage = mock(PlayerStorage.class);
        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        PlayerService service = new PlayerService(mapper, mockPlayerStorage);
        String topCountry = service.getTopCountryByWinRatio(players);
        assertEquals("SUI", topCountry);
    }

    @Test
    void testGetTopCountryByWinRatio_throwsException_whenPlayersIsNull() {
        PlayerService service = new PlayerService(mapper, mock(PlayerStorage.class));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getTopCountryByWinRatio(null);
        });

        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testGetTopCountryByWinRatio_throwsException_whenPlayersIsEmpty() {
        PlayerService service = new PlayerService(mapper, mock(PlayerStorage.class));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getTopCountryByWinRatio(Collections.emptyMap());
        });

        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testGetTopCountryByWinRatio_throwsException_whenNoValidCountryFound() {
        PlayerService service = new PlayerService(mapper, mock(PlayerStorage.class));

        Map<String, PlayerDTO> playersWithoutCountry = Map.of(
                "1", PlayerDTO.builder().country(null).build(),
                "2", PlayerDTO.builder().country(CountryDTO.builder().code(null).build()).build()
        );

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getTopCountryByWinRatio(playersWithoutCountry);
        });

        assertEquals("No country found with players data", ex.getMessage());
    }

    @Test
    void testCalculateAverageBmi_success() {
        PlayerService service = new PlayerService(mapper, mock(PlayerStorage.class));

        PlayerDTO player1 = PlayerDTO.builder()
                .data(StatsDataDTO.builder().height(180).weight(75000).build())
                .build();

        PlayerDTO player2 = PlayerDTO.builder()
                .data(StatsDataDTO.builder().height(170).weight(68000).build())
                .build();

        Map<String, PlayerDTO> players = Map.of(
                "1", player1,
                "2", player2
        );

        double result = service.calculateAverageBmi(players);

        double expectedPlayer1Bmi = 75.0 / (1.8 * 1.8); // 23.15
        double expectedPlayer2Bmi = 68.0 / (1.7 * 1.7); // 23.53
        double expectedAverage = (expectedPlayer1Bmi + expectedPlayer2Bmi) / 2;

        assertEquals(expectedAverage, result, 0.01);
    }

    @Test
    void testCalculateAverageBmi_returnsZeroIfNoValidData() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerDTO player1 = PlayerDTO.builder().data(null).build();
        PlayerDTO player2 = PlayerDTO.builder().data(null).build();

        Map<String, PlayerDTO> players = Map.of(
                "1", player1,
                "2", player2
        );

        double result = service.calculateAverageBmi(players);
        assertEquals(0.0, result);
    }

    @Test
    void testCalculateAverageBmi_throwsException_whenPlayersIsNull() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.calculateAverageBmi(null);
        });

        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testCalculateAverageBmi_throwsException_whenPlayersIsEmpty() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.calculateAverageBmi(Collections.emptyMap());
        });

        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testCalculateAverageBmi_handlesZeroHeightGracefully() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerDTO player1 = PlayerDTO.builder()
                .data(StatsDataDTO.builder().height(0).weight(75000).build())
                .build();

        Map<String, PlayerDTO> players = Map.of("1", player1);

        double result = service.calculateAverageBmi(players);
        assertEquals(0.0, result);
    }

    @Test
    void testCalculateMedianHeight_success_oddNumberOfPlayers() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerDTO p1 = PlayerDTO.builder().data(StatsDataDTO.builder().height(180).build()).build();
        PlayerDTO p2 = PlayerDTO.builder().data(StatsDataDTO.builder().height(170).build()).build();
        PlayerDTO p3 = PlayerDTO.builder().data(StatsDataDTO.builder().height(190).build()).build();

        Map<String, PlayerDTO> players = Map.of(
                "1", p1,
                "2", p2,
                "3", p3
        );

        double median = service.calculateMedianHeight(players);
        assertEquals(180.0, median);
    }

    @Test
    void testCalculateMedianHeight_success_evenNumberOfPlayers() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerDTO p1 = PlayerDTO.builder().data(StatsDataDTO.builder().height(160).build()).build();
        PlayerDTO p2 = PlayerDTO.builder().data(StatsDataDTO.builder().height(170).build()).build();
        PlayerDTO p3 = PlayerDTO.builder().data(StatsDataDTO.builder().height(180).build()).build();
        PlayerDTO p4 = PlayerDTO.builder().data(StatsDataDTO.builder().height(190).build()).build();

        Map<String, PlayerDTO> players = Map.of(
                "1", p1,
                "2", p2,
                "3", p3,
                "4", p4
        );

        double median = service.calculateMedianHeight(players);
        assertEquals((170 + 180) / 2.0, median);
    }

    @Test
    void testCalculateMedianHeight_throwsException_whenPlayersNull() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.calculateMedianHeight(null);
        });

        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testCalculateMedianHeight_throwsException_whenPlayersEmpty() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.calculateMedianHeight(Collections.emptyMap());
        });

        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testCalculateMedianHeight_returnsZero_whenNoValidData() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerDTO p1 = PlayerDTO.builder().data(null).build();
        PlayerDTO p2 = PlayerDTO.builder().data(null).build();

        Map<String, PlayerDTO> players = Map.of(
                "1", p1,
                "2", p2
        );

        double result = service.calculateMedianHeight(players);
        assertEquals(0.0, result);
    }
    @Test
    void testGetStatistics_success() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerDTO p1 = PlayerDTO.builder()
                .country(CountryDTO.builder().code("FR").picture(DJOKOVIC_COUNTRY_PICTURE).build())
                .data(StatsDataDTO.builder().height(180).weight(75000).rank(10).points(5).age(21).build())
                .build();

        PlayerDTO p2 = PlayerDTO.builder()
                .country(CountryDTO.builder().code("JPN").picture(DJOKOVIC_COUNTRY_PICTURE).build())
                .data(StatsDataDTO.builder().height(170).weight(68000).rank(20).points(15).age(31).build())
                .build();

        PlayerDTO p3 = PlayerDTO.builder()
                .country(CountryDTO.builder().code("SUI").picture(DJOKOVIC_COUNTRY_PICTURE).build())
                .data(StatsDataDTO.builder().height(160).weight(60000).rank(90).points(2).age(33).build())
                .build();

        Map<String, PlayerDTO> players = Map.of(
                "1", p1,
                "2", p2,
                "3", p3
        );

        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        PlayerStatsDTO stats = service.getStatistics();


        assertEquals("SUI", stats.getTopCountryByWinRatio());


        List<Integer> heights = List.of(160, 170, 180);
        double expectedMedian = heights.get(1); // 170
        assertEquals(expectedMedian, stats.getMedianHeight());

        double bmi1 = 75.0 / (1.8 * 1.8);
        double bmi2 = 68.0 / (1.7 * 1.7);
        double bmi3 = 60.0 / (1.6 * 1.6);
        double expectedBmi = (bmi1 + bmi2 + bmi3) / 3;
        assertEquals(expectedBmi, stats.getAverageBmi(), 0.01);
    }

    @Test
    void testGetStatistics_returnsZeroValues_whenAllDataInvalid() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        PlayerDTO p1 = PlayerDTO.builder().data(null).build();
        PlayerDTO p2 = PlayerDTO.builder().data(null).build();

        Map<String, PlayerDTO> players = Map.of(
                "1", p1,
                "2", p2
        );

        when(mockPlayerStorage.getPlayers()).thenReturn(players);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getStatistics();
        });

        assertEquals("No valid players data (missing data or country)", ex.getMessage());

;
    }

    @Test
    void testGetStatistics_throwsException_whenPlayersIsNull() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        when(mockPlayerStorage.getPlayers()).thenReturn(null);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getStatistics();
        });

        assertEquals("No players data provided", ex.getMessage());
    }

    @Test
    void testGetStatistics_throwsException_whenPlayersIsEmpty() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        service = new PlayerService(mapper, mockPlayerStorage);
        when(mockPlayerStorage.getPlayers()).thenReturn(Collections.emptyMap());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.getStatistics();
        });

        assertEquals("No players data provided", ex.getMessage());
    }


    @Test
    void testDeletePlayer_successfulDeletion_returnsTrue() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        PlayerDTO toDelete = PlayerDTO.builder().id(10L).build();
        service = new PlayerService(mapper, mockPlayerStorage);

        Map<String, PlayerDTO> after = new HashMap<>();
        after.put("20", PlayerDTO.builder().id(20L).build());

        when(mockPlayerStorage.deletePlayer(toDelete)).thenReturn(after);
        SuccessDTO result = service.deletePlayer(toDelete);
        assertTrue(result.isSuccess());
    }

    @Test
    void testDeletePlayer_cannotBeNull() {
        PlayerStorage mockPlayerStorage =  mock(PlayerStorage.class);
        PlayerDTO toDelete = PlayerDTO.builder().id(null).build();
        service = new PlayerService(mapper, mockPlayerStorage);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.deletePlayer(toDelete);
        });
        assertEquals("Player Id cannot be null or empty", ex.getMessage());
    }




}
