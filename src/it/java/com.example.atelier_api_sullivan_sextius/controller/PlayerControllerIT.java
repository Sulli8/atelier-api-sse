package controller;

import com.example.atelier_api_sullivan_sextius.AtelierApiSullivanSextiusApplication;
import com.example.atelier_api_sullivan_sextius.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = AtelierApiSullivanSextiusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerControllerIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

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

    public static final Long DJOKOVIC_ID = 52L;
    public static final String DJOKOVIC_FIRST_NAME = "Novak";
    public static final String DJOKOVIC_LAST_NAME = "Djokovic";
    public static final String DJOKOVIC_SEX = "M";
    public static final String DJOKOVIC_SHORT_NAME = "N.DJO";
    public static final String DJOKOVIC_PICTURE = "https://tenisu.latelier.co/resources/Djokovic.png";
    public static final String DJOKOVIC_COUNTRY_CODE = "SRB";
    public static final String DJOKOVIC_COUNTRY_PICTURE = "https://tenisu.latelier.co/resources/Serbie.png";
    public static final int DJOKOVIC_RANK = 2;
    public static final int DJOKOVIC_POINTS = 2542;
    public static final int DJOKOVIC_WEIGHT = 80000;
    public static final int DJOKOVIC_HEIGHT = 188;
    public static final int DJOKOVIC_AGE = 31;
    public static final List<Integer> DJOKOVIC_LAST_MATCHES = List.of(1, 1, 1, 1, 1);

    public static final Long SERENA_ID = 102L;
    public static final String SERENA_FIRST_NAME = "Serena";
    public static final String SERENA_LAST_NAME = "Williams";
    public static final String SERENA_SEX = "F";
    public static final String SERENA_SHORT_NAME = "S.WIL";
    public static final String SERENA_PICTURE = "https://tenisu.latelier.co/resources/Serena.png";

    public static final String SERENA_COUNTRY_CODE = "USA";
    public static final String SERENA_COUNTRY_PICTURE = "https://tenisu.latelier.co/resources/USA.png";

    public static final int SERENA_RANK = 10;
    public static final int SERENA_POINTS = 3521;
    public static final int SERENA_WEIGHT = 72000;
    public static final int SERENA_HEIGHT = 175;
    public static final int SERENA_AGE = 37;

    public static final List<Integer> SERENA_LAST = List.of(0, 1, 1, 1, 0);

    private static final String LOCALHOST = "http://localhost:";

    public static CountryDTO createRogerCountry() {
        return CountryDTO.builder().code(ROGER_COUNTRY_CODE).picture(ROGER_COUNTRY_PICTURE).build();
    }
    public static StatsDataDTO createRogerStats() {
        return StatsDataDTO.builder().rank(ROGER_RANK).points(120000).age(20).height(ROGER_HEIGHT).weight(ROGER_WEIGHT).last(ROGER_LAST_MATCHES).build();
    }

    @Test
    void testGetAllPlayers() {
        String url = LOCALHOST + port + "/api/players";
        ResponseEntity<Map<String, PlayerDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, PlayerDTO> players = response.getBody();
        assertThat(players).isNotNull();
        assertEquals(5,players.size());

        assertEquals(SERENA_ID,players.get(SERENA_ID.toString()).getId());
        assertEquals(SERENA_FIRST_NAME,players.get(SERENA_ID.toString()).getFirstName());
        assertEquals(SERENA_LAST_NAME,players.get(SERENA_ID.toString()).getLastName());
        assertEquals(SERENA_SEX,players.get(SERENA_ID.toString()).getSex());
        assertEquals(SERENA_SHORT_NAME,players.get(SERENA_ID.toString()).getShortName());
        assertEquals(SERENA_PICTURE,players.get(SERENA_ID.toString()).getPicture());
        assertEquals(SERENA_COUNTRY_CODE,players.get(SERENA_ID.toString()).getCountry().getCode());
        assertEquals(SERENA_COUNTRY_PICTURE,players.get(SERENA_ID.toString()).getCountry().getPicture());
        assertEquals(SERENA_RANK,players.get(SERENA_ID.toString()).getData().getRank());
        assertEquals(SERENA_POINTS,players.get(SERENA_ID.toString()).getData().getPoints());
        assertEquals(SERENA_WEIGHT,players.get(SERENA_ID.toString()).getData().getWeight());
        assertEquals(SERENA_HEIGHT,players.get(SERENA_ID.toString()).getData().getHeight());
        assertEquals(SERENA_AGE,players.get(SERENA_ID.toString()).getData().getAge());
        assertEquals(SERENA_LAST,players.get(SERENA_ID.toString()).getData().getLast());
    }

    @Test
    void testGetPlayerById() {
        String url = LOCALHOST + port + "/api/players/" + DJOKOVIC_ID;
        ResponseEntity<PlayerDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PlayerDTO player = response.getBody();
        assertThat(player).isNotNull();

        assertEquals(DJOKOVIC_ID, player.getId());
        assertEquals(DJOKOVIC_FIRST_NAME, player.getFirstName());
        assertEquals(DJOKOVIC_LAST_NAME, player.getLastName());
        assertEquals(DJOKOVIC_SEX, player.getSex());
        assertEquals(DJOKOVIC_SHORT_NAME, player.getShortName());
        assertEquals(DJOKOVIC_PICTURE, player.getPicture());
        assertEquals(DJOKOVIC_COUNTRY_CODE, player.getCountry().getCode());
        assertEquals(DJOKOVIC_COUNTRY_PICTURE, player.getCountry().getPicture());
        assertEquals(DJOKOVIC_RANK, player.getData().getRank());
        assertEquals(DJOKOVIC_POINTS, player.getData().getPoints());
        assertEquals(DJOKOVIC_WEIGHT, player.getData().getWeight());
        assertEquals(DJOKOVIC_HEIGHT, player.getData().getHeight());
        assertEquals(DJOKOVIC_AGE, player.getData().getAge());
        assertEquals(DJOKOVIC_LAST_MATCHES, player.getData().getLast());

    }

    @Test
    void testGetStats() {
        String url = LOCALHOST + port + "/api/players/stats";
        ResponseEntity<PlayerStatsDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        PlayerStatsDTO playerStatsDTO = response.getBody();
        assertThat(playerStatsDTO).isNotNull();
        assertThat(playerStatsDTO.getAverageBmi()).isGreaterThan(0);
        assertThat(playerStatsDTO.getMedianHeight()).isGreaterThan(0);
        assertEquals(playerStatsDTO.getTopCountryByWinRatio(), "SRB");
    }

    @Test
    void testCreatePlayer() {
        String url = LOCALHOST + port + "/api/players";
        PlayerDTO player = PlayerDTO.builder()
                .id(ROGER_ID)
                .firstName(ROGER_FIRST_NAME)
                .lastName(ROGER_LAST_NAME)
                .shortName(ROGER_SHORT_NAME)
                .sex(ROGER_SEX)
                .picture(ROGER_PICTURE)
                .country(createRogerCountry())
                .data(createRogerStats())
                .build();

        ResponseEntity<Map<String, PlayerDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(player),
                new ParameterizedTypeReference<Map<String, PlayerDTO>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ROGER_ID, response.getBody().get(ROGER_ID.toString()).getId());
        assertEquals(ROGER_FIRST_NAME, response.getBody().get(ROGER_ID.toString()).getFirstName());
        assertEquals(ROGER_LAST_NAME, response.getBody().get(ROGER_ID.toString()).getLastName());
        assertEquals(ROGER_SEX, response.getBody().get(ROGER_ID.toString()).getSex());
        assertEquals(ROGER_SHORT_NAME, response.getBody().get(ROGER_ID.toString()).getShortName());
        assertEquals(ROGER_PICTURE, response.getBody().get(ROGER_ID.toString()).getPicture());
        assertEquals(createRogerCountry().getCode(), response.getBody().get(ROGER_ID.toString()).getCountry().getCode());
        assertEquals(createRogerCountry().getPicture(), response.getBody().get(ROGER_ID.toString()).getCountry().getPicture());
        assertEquals(createRogerStats().getRank(), response.getBody().get(ROGER_ID.toString()).getData().getRank());
        assertEquals(createRogerStats().getPoints(), response.getBody().get(ROGER_ID.toString()).getData().getPoints());
        assertEquals(createRogerStats().getWeight(), response.getBody().get(ROGER_ID.toString()).getData().getWeight());
        assertEquals(createRogerStats().getHeight(), response.getBody().get(ROGER_ID.toString()).getData().getHeight());
        assertEquals(createRogerStats().getAge(), response.getBody().get(ROGER_ID.toString()).getData().getAge());
        assertEquals(createRogerStats().getLast(), response.getBody().get(ROGER_ID.toString()).getData().getLast());
    }


    @Test
    void testPatchPlayer() {
        String url = LOCALHOST + port + "/api/players";
        PlayerDTO player = PlayerDTO.builder()
                .id(SERENA_ID)
                .firstName(SERENA_FIRST_NAME)
                .lastName("Shakespeare")
                .shortName("sks")
                .sex("M")
                .picture("https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcRV9nII_MU0ero3JxZljuKKUL0BVJR59_dIKhqNnwQ1IIZIp5lp4-qX_r5ExEoO4Okvza_-bf4Dpy13tr40nP9aSrT3O2owWfZ5rrSSnsMsIqzik5OTbRX4Oi4SB0DWNcMU-V7ptLcrJx9p")
                .country(createRogerCountry())
                .data(createRogerStats())
                .build();

        ResponseEntity<Map<String, PlayerDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                new HttpEntity<>(player),
                new ParameterizedTypeReference<Map<String, PlayerDTO>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SERENA_ID, response.getBody().get(SERENA_ID.toString()).getId());
        assertEquals(SERENA_FIRST_NAME, response.getBody().get(SERENA_ID.toString()).getFirstName());
        assertEquals("Shakespeare", response.getBody().get(SERENA_ID.toString()).getLastName());
        assertEquals("M", response.getBody().get(SERENA_ID.toString()).getSex());
        assertEquals("sks", response.getBody().get(SERENA_ID.toString()).getShortName());
        assertEquals("https://encrypted-tbn3.gstatic.com/licensed-image?q=tbn:ANd9GcRV9nII_MU0ero3JxZljuKKUL0BVJR59_dIKhqNnwQ1IIZIp5lp4-qX_r5ExEoO4Okvza_-bf4Dpy13tr40nP9aSrT3O2owWfZ5rrSSnsMsIqzik5OTbRX4Oi4SB0DWNcMU-V7ptLcrJx9p", response.getBody().get(SERENA_ID.toString()).getPicture());
        assertEquals(createRogerCountry().getCode(), response.getBody().get(SERENA_ID.toString()).getCountry().getCode());
        assertEquals(createRogerCountry().getPicture(), response.getBody().get(SERENA_ID.toString()).getCountry().getPicture());
        assertEquals(createRogerStats().getRank(), response.getBody().get(SERENA_ID.toString()).getData().getRank());
        assertEquals(createRogerStats().getPoints(), response.getBody().get(SERENA_ID.toString()).getData().getPoints());
        assertEquals(createRogerStats().getWeight(), response.getBody().get(SERENA_ID.toString()).getData().getWeight());
        assertEquals(createRogerStats().getHeight(), response.getBody().get(SERENA_ID.toString()).getData().getHeight());
        assertEquals(createRogerStats().getAge(), response.getBody().get(SERENA_ID.toString()).getData().getAge());
        assertEquals(createRogerStats().getLast(), response.getBody().get(SERENA_ID.toString()).getData().getLast());
    }

    @Test
    void testDeletePlayer() {
        String url = LOCALHOST + port + "/api/players";
        PlayerDTO player = PlayerDTO.builder()
                .id(ROGER_ID)
                .firstName(ROGER_FIRST_NAME)
                .lastName(ROGER_LAST_NAME)
                .shortName(ROGER_SHORT_NAME)
                .sex(ROGER_SEX)
                .picture(ROGER_PICTURE)
                .country(createRogerCountry())
                .data(createRogerStats())
                .build();

        ResponseEntity<SuccessDTO> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                new HttpEntity<>(player),
                new ParameterizedTypeReference<SuccessDTO>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
    }







}
